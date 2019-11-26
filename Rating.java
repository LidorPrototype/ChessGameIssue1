package Game;

public class Rating {

	static int pawnBoard[][]={
			{ 0,  0,  0,  0,  0,  0,  0,  0},
			{50, 50, 50, 50, 50, 50, 50, 50},
			{10, 10, 20, 30, 30, 20, 10, 10},
			{ 5,  5, 10, 25, 25, 10,  5,  5},
			{ 0,  0,  0, 20, 20,  0,  0,  0},
			{ 5, -5,-10,  0,  0,-10, -5,  5},
			{ 5, 10, 10,-20,-20, 10, 10,  5},
			{ 0,  0,  0,  0,  0,  0,  0,  0}};
	static int rookBoard[][]={
			{ 0,  0,  0,  0,  0,  0,  0,  0},
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 0,  0,  0,  5,  5,  0,  0,  0}};
	static int knightBoard[][]={
			{-50,-40,-30,-30,-30,-30,-40,-50},
			{-40,-20,  0,  0,  0,  0,-20,-40},
			{-30,  0, 10, 15, 15, 10,  0,-30},
			{-30,  5, 15, 20, 20, 15,  5,-30},
			{-30,  0, 15, 20, 20, 15,  0,-30},
			{-30,  5, 10, 15, 15, 10,  5,-30},
			{-40,-20,  0,  5,  5,  0,-20,-40},
			{-50,-40,-30,-30,-30,-30,-40,-50}};
	static int bishopBoard[][]={
			{-20,-10,-10,-10,-10,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5, 10, 10,  5,  0,-10},
			{-10,  5,  5, 10, 10,  5,  5,-10},
			{-10,  0, 10, 10, 10, 10,  0,-10},
			{-10, 10, 10, 10, 10, 10, 10,-10},
			{-10,  5,  0,  0,  0,  0,  5,-10},
			{-20,-10,-10,-10,-10,-10,-10,-20}};
	static int queenBoard[][]={
			{-20,-10,-10, -5, -5,-10,-10,-20},
			{-10,  0,  0,  0,  0,  0,  0,-10},
			{-10,  0,  5,  5,  5,  5,  0,-10},
			{ -5,  0,  5,  5,  5,  5,  0, -5},
			{  0,  0,  5,  5,  5,  5,  0, -5},
			{-10,  5,  5,  5,  5,  5,  0,-10},
			{-10,  0,  5,  0,  0,  0,  0,-10},
			{-20,-10,-10, -5, -5,-10,-10,-20}};
	static int kingMidBoard[][]={
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-30,-40,-40,-50,-50,-40,-40,-30},
			{-20,-30,-30,-40,-40,-30,-30,-20},
			{-10,-20,-20,-20,-20,-20,-20,-10},
			{ 20, 20,  0,  0,  0,  0, 20, 20},
			{ 20, 30, 10,  0,  0, 10, 30, 20}};
	static int kingEndBoard[][]={
			{-50,-40,-30,-20,-20,-30,-40,-50},
			{-30,-20,-10,  0,  0,-10,-20,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 30, 40, 40, 30,-10,-30},
			{-30,-10, 20, 30, 30, 20,-10,-30},
			{-30,-30,  0,  0,  0,  0,-30,-30},
			{-50,-30,-30,-30,-30,-30,-30,-50}};

	public static int rating(int list, int depth) {
		int counter=0, material=rateMaterial();
		counter+=rateAttack();
		counter+=material;
		counter+=rateMoveability(list, depth, material);
		counter+=ratePositional(material);
		MainChessClass.flipBoard();
		material=rateMaterial();
		counter-=rateAttack();
		counter-=material;
		counter-=rateMoveability(list, depth, material);
		counter-=ratePositional(material);
		MainChessClass.flipBoard();
		return -(counter+depth*50);
	}
	
	public static int rateAttack() {
		int counter = 0, tempPositionC = MainChessClass.kingPositionC;
		for(int i = 0; i < 64; i++) {
			switch(MainChessClass.chessBoard[i/8][i%8]) {
			case "P":{
					MainChessClass.kingPositionC = i;
					if(!(MainChessClass.kingSafe())) {
						counter -= 64;
					}
				}
				break;
			case "R":{
					MainChessClass.kingPositionC = i;
					if(!(MainChessClass.kingSafe())) {
						counter -= 500;
					}
				}
				break;
			case "K":{
					MainChessClass.kingPositionC = i;
					if(!(MainChessClass.kingSafe())) {
						counter -= 300;
					}
				}
				break;
			case "B":{
					MainChessClass.kingPositionC = i;
					if(!(MainChessClass.kingSafe())) {
						counter -= 300;
					}
				}
				break;
			case "Q":{
					MainChessClass.kingPositionC = i;
					if(!(MainChessClass.kingSafe())) {
						counter -= 900;
					}
				}
				break;
			}
		}
		MainChessClass.kingPositionC = tempPositionC;
		if(!(MainChessClass.kingSafe())) {
			counter -= 200;
		}
		return (counter / 2);
	}
	
	public static int rateMaterial() {
		int counter = 0, bishopCounter = 0;
		for(int i = 0; i < 64; i++) {
			switch(MainChessClass.chessBoard[i/8][i%8]) {
			case "P":
				counter += 100;
				break;
			case "R":
				counter += 500;
				break;
			case "K":
				counter += 300;
				break;
			case "B":
				bishopCounter += 1;
				break;
			case "Q":
				counter += 900;
				break;
			}
		}
		if(bishopCounter >= 2) {
			counter += (300*bishopCounter);
		}else {
			if(bishopCounter == 1) {
				counter += 250;
			}
		}
		return counter;
	}

	public static int rateMoveability(int listLength, int depth, int material) {
		int counter = 0;
		counter += listLength;//5 points for valid move
		if(listLength == 0) {//current side is in checkmate or a stalemate
			if(!(MainChessClass.kingSafe())) {//if checkmate
				counter += (-200000*depth);
			}else {//if stalemate
				counter += (-150000*depth);
			}
		}
		return counter;
	}
	
	public static int ratePositional(int material) {
		int counter=0;
		for (int i=0;i<64;i++) {
			switch (MainChessClass.chessBoard[i/8][i%8]) {
			case "P": 
				counter+=pawnBoard[i/8][i%8];
				break;
			case "R": 
				counter+=rookBoard[i/8][i%8];
				break;
			case "K": 
				counter+=knightBoard[i/8][i%8];
				break;
			case "B": 
				counter+=bishopBoard[i/8][i%8];
				break;
			case "Q": 
				counter+=queenBoard[i/8][i%8];
				break;
			case "A": 
				if (material>=1750) {
					counter+=kingMidBoard[i/8][i%8];
					counter+=MainChessClass.posibleA(MainChessClass.kingPositionC).length()*10;
				}else {
					counter+=kingEndBoard[i/8][i%8]; 
					counter+=MainChessClass.posibleA(MainChessClass.kingPositionC).length()*30;
				}
				break;
			}
		}
		return counter;
	}
}

