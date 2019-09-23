import java.util.ArrayList;

//Add function to check for Actions in next state from Lee
public class State {
	
	char player;
	char opponent;
	Board board;
	int utility; //heuristic for non terminal state
	Boolean terminal;
	State parent;
	ArrayList<Action> actions; // Set of possible actions in this state
	
	public State(char player, Board board) {
		if(player == 'w') this.opponent = 'b';
		else this.opponent = 'w';
		this.player = player;
		this.board = board;
		this.actions = new ArrayList<Action>();
	}
	
	public int getUtility() {
		return this.utility;
	}
	
	public Boolean isTerminal() {
		return this.terminal;
	}
	
	public ArrayList<Action> getActions() { //get set of all possible actions in this state
		if(this.player == 'b') this.board.flipArr();
		for(int i=0; i<this.board.size; i++) {
			ArrayList<Action> moves = new ArrayList<Action>();
			for(int j=0; j<this.board.size; j++) {
				int squareVal = this.board.set_up[i][j];
				if(squareVal == this.player || squareVal == Character.toUpperCase(this.player)) {
					String poss = this.board.getSquareStr(i, j);
					Boolean isKing = false;
					if(squareVal == 'W' || squareVal == 'B') isKing = true;
					ArrayList<String> myArr = this.board.positions(this.board,this.player);
					ArrayList<String> oppoArr = this.board.positions(this.board, this.opponent);
					moves = getPossActions(poss, isKing, myArr, oppoArr);

					for(Action act: moves)
						if(act.moves != null)
							actions.add(act);
					
//					if(this.player == 'b' && moves != null) {
//						for(Action act: moves)
//							if(act != null)
//								for(String move: act.moves) {
//									act.moves.add(this.board.flipPos(move));
//									act.moves.remove(move);
//								}
//					}
					
				}
				else continue;
			}
		}
		//if black player, flip all moves in actions list and flip board back
		if(this.player == 'b') {
			this.board.flipArr();
			this.board.flipActions(this.actions);
		}
		
		//Clean actions to remain with only the ones that are possible in this state
		this.actions = finalActions(this.actions);
		return this.actions;
	}
	
	public ArrayList<Action> getPossActions(String poss, Boolean isKing, ArrayList<String> myArr, ArrayList<String> oppoArr) { //get all possible moves for a piece
		ArrayList<Action> possMoves = possMoves(poss, isKing,myArr,oppoArr);
		//ArrayList<Action> possMoves = null;
		return possMoves;
	}
	
//	public static ArrayList<String> possMoves(String currPos, boolean isKing) { //arraylist of strings
//		ArrayList<String> possMoves= new ArrayList<String>();
//		int column;
//		char row;
//		
//		//separate A1 to A & 1
//				row = currPos.charAt(0);
//				char ab = (char)currPos.charAt(1);
//				column =Character.getNumericValue(ab);// Integer.toString(Character.toString ((char) currPos.charAt(1)));
//		
//		if(isKing == true) {  //checking if it's king
//			
//			char rBefore = (char)((int)row - 1);   //row before
//			char rAfter = (char)((int)row + 1);   //row after
//			
//			int cBefore = column - 1; //column before
//			int cAfter = column + 1; //column after
//			
//			//checking boundaries
//			if(rBefore == 'A' || rBefore == 'B' || rBefore == 'C' || rBefore == 'D') {
//				if( cBefore > 0 && cBefore < 5 ) {
//					possMoves.add(Character.toString(rBefore) + Integer.toString(cBefore));
//				}
//				if(cAfter == 1 || cAfter == 2 || cAfter == 3 || cAfter == 4) {
//					possMoves.add(Character.toString(rBefore) + Integer.toString(cAfter));
//				}
//			}
//			if(rAfter == 'A' || rAfter == 'B' || rAfter == 'C' || rAfter == 'D') {
//				if( cBefore > 0 && cBefore < 5 ) {
//					possMoves.add(Character.toString(rAfter) + Integer.toString(cBefore));
//				}
//				if(cAfter == 1 || cAfter == 2 || cAfter == 3 || cAfter == 4) {
//					possMoves.add(Character.toString(rAfter) + Integer.toString(cAfter));
//				}
//			}
//			
//		}else { //not king - can only move forward
//
//			char rBefore = (char)((int)row - 1);
//			
//			int cBefore = column - 1; //column before
//			int cAfter = column + 1;  //column after
//			
//			if(rBefore == 'A' || rBefore == 'B' || rBefore == 'C' || rBefore == 'D') {
//				if( cBefore > 0 && cBefore < 5 ) {
//					possMoves.add(Character.toString(rBefore) + Integer.toString(cBefore));
//				}
//				if(cAfter == 1 || cAfter == 2 || cAfter == 3 || cAfter == 4) {
//					possMoves.add(Character.toString(rBefore) + Integer.toString(cAfter));
//				}
//			}
//		}
//		return possMoves;
//	}
	
	
	
	
	
	
	
	public static ArrayList<Action> possMoves(String currPos, boolean isKing, ArrayList<String> myArr, ArrayList<String> oppoArr) { //arraylist of strings
		//closest four diagonals
		ArrayList<String> possibleDiag= new ArrayList<String>();
		//final out of ArrayList of Actions
		ArrayList<Action> actionList = new ArrayList<Action>();
		
		int column;
		char row;
		
		//separate A1 to A & 1
		row = currPos.charAt(0);
		column = Character.getNumericValue((char)currPos.charAt(1));
	
		//do {
		
			if(isKing == true) {  //checking if it's king
				
				char rBefore = (char)((int)row - 1);   //row before
				char rAfter = (char)((int)row + 1);   //row after
				
				int cBefore = column - 1; //column before
				int cAfter = column + 1; //column after
				
				//checking boundaries v2
				if(checkBound(rBefore,cBefore)) {
					possibleDiag.add(Character.toString(rBefore) + Integer.toString(cBefore));
				}
				if(checkBound(rBefore,cAfter)) {
					possibleDiag.add(Character.toString(rBefore) + Integer.toString(cAfter));
				}
				if(checkBound(rAfter,cBefore)) {
					possibleDiag.add(Character.toString(rAfter) + Integer.toString(cBefore));
				}
				if(checkBound(rAfter,cAfter)) {
					possibleDiag.add(Character.toString(rAfter) + Integer.toString(cAfter));
				}
				
				
			}else { //not king - can only move forward
	
				char rBefore = (char)((int)row - 1);
				
				int cBefore = column - 1; //column before
				int cAfter = column + 1;  //column after
				
				//checking boundaries v2
				if(checkBound(rBefore,cBefore)) {
					possibleDiag.add(Character.toString(rBefore) + Integer.toString(cBefore));
				}
				if(checkBound(rBefore,cAfter)) {
					possibleDiag.add(Character.toString(rBefore) + Integer.toString(cAfter));
				}
							
			}
			
			//for each diagonal position
			for (String diag : possibleDiag) {
		
				char diagRow; //= diag.charAt(0);
				int diagCol; //= Character.getNumericValue((char)currPos.charAt(1));
				
	
				//check if there are my pieces
				if(myArr.contains(diag) ) {
					
					//do not add to the actionList
				}else if ( oppoArr.contains(diag) ) {
					
					//jump over opponent
					//check empty position over the opponent and boundary
					
					boolean canMove = false;
					ArrayList<String> jumpList = new ArrayList<String>();
					int capture = 0;
					
					//update diag each time jumping
					diagRow = diag.charAt(0);
					diagCol = Character.getNumericValue((char)diag.charAt(1));
					
					if(isKing) {
						//for king - all directions
						if(diagRow == (char)((int)row - 1) && diagCol == column - 1 ) { //front left diag
							//empty position
							char emptyRow = (char) ((int)diagRow - 1);
							int emptyCol = diagCol - 1;
							String emptyPos = Character.toString(emptyRow) + Integer.toString(emptyCol);
							
							//if it is within boundary and not in oppoArr and myArr then capture
							if( checkBound(emptyRow, emptyCol) && !myArr.contains(emptyPos) && !oppoArr.contains(emptyPos)) {
								jumpList.add(currPos + "-" + emptyPos);
								actionList.add(new Action(1,jumpList));
	//							canMove = true;
	//							jumpList.add(emptyPos);
	//							capture++;
	//							row = emptyRow; //update to empty position
	//							column = emptyCol;
								//continue;
							}else {
								canMove = false;
								
							}
						}else if(diagRow == (char)((int)row - 1) && diagCol == column + 1 ) { //front right diag
							
							//empty position
							char emptyRow = (char) ((int)diagRow - 1);
							int emptyCol = diagCol + 1;
							String emptyPos = Character.toString(emptyRow) + Integer.toString(emptyCol);
							
							//if it is within boundary and not in oppoArr and myArr then add to output arr
							if( checkBound(emptyRow, emptyCol) && !myArr.contains(emptyPos) && !oppoArr.contains(emptyPos)) {
								canMove = true;
								jumpList.add(currPos + "-" + emptyPos);
								actionList.add(new Action(1,jumpList));
								
							}else {
								canMove = false;
							}
						}else if(diagRow == (char)((int)row + 1) && diagCol == column - 1 ) { //back left diag
							//empty position
							char emptyRow = (char) ((int)diagRow + 1);
							int emptyCol = diagCol - 1;
							String emptyPos = Character.toString(emptyRow) + Integer.toString(emptyCol);
							
							//if it is within boundary and not in oppoArr and myArr then add to output arr
							if( checkBound(emptyRow, emptyCol) && !myArr.contains(emptyPos) && !oppoArr.contains(emptyPos)) {
								canMove = true;
								jumpList.add(currPos + "-" + emptyPos);
								actionList.add(new Action(1,jumpList));
								
							}else {
								canMove = false;
							}
						}else if(diagRow == (char)((int)row + 1) && diagCol == column + 1 ) { //back right diag
							//empty position
							char emptyRow = (char) ((int)diagRow + 1);
							int emptyCol = diagCol + 1;
							String emptyPos = Character.toString(emptyRow) + Integer.toString(emptyCol);
							
							//if it is within boundary and not in oppoArr and myArr then add to output arr
							if( checkBound(emptyRow, emptyCol) && !myArr.contains(emptyPos) && !oppoArr.contains(emptyPos)) {
								canMove = true;
								jumpList.add(currPos + "-" + emptyPos);
								actionList.add(new Action(1,jumpList));
								
								
							}else {
								canMove = false;
							}
						}
					}else {
						//non-king pieces
						if(diagRow == (char)((int)row - 1) && diagCol == column - 1 ) { //front left diag
							//empty position
							char emptyRow = (char) ((int)diagRow - 1);
							int emptyCol = diagCol - 1;
							String emptyPos = Character.toString(emptyRow) + Integer.toString(emptyCol);
							
							//if it is within boundary and not in oppoArr and myArr then capture
							if( checkBound(emptyRow, emptyCol) && !myArr.contains(emptyPos) && !oppoArr.contains(emptyPos)) {
								jumpList.add(currPos + "-" + emptyPos);
								actionList.add(new Action(1,jumpList));
	//							canMove = true;
	//							jumpList.add(emptyPos);
	//							capture++;
	//							row = emptyRow; //update to empty position
	//							column = emptyCol;
								//continue;
							}else {
								canMove = false;
								
							}
						}else if(diagRow == (char)((int)row - 1) && diagCol == column + 1 ) { //front right diag
							
							//empty position
							char emptyRow = (char) ((int)diagRow - 1);
							int emptyCol = diagCol + 1;
							String emptyPos = Character.toString(emptyRow) + Integer.toString(emptyCol);
							
							//if it is within boundary and not in oppoArr and myArr then add to output arr
							if( checkBound(emptyRow, emptyCol) && !myArr.contains(emptyPos) && !oppoArr.contains(emptyPos)) {
								canMove = true;
								jumpList.add(currPos + "-" + emptyPos);
								actionList.add(new Action(1,jumpList));
								
							}else {
								canMove = false;
							}
						}
					}
					
//					if(jumpList.isEmpty()) {
//						//dont add actionList
//					}else {
//						//add to actionList
//						actionList.add(new Action(capture, jumpList)); //add new action with jump # and jumpList
//					}
	
					
				}else {
					//empty position add to actionList with int val = 0
					ArrayList<String> arr = new ArrayList<String>();
					arr.add(currPos + "-" + diag);
					actionList.add( new Action(0, arr) );
					//arr.removeAll(arr);
				}
			}
			
		//}while(canMove);
			
		return actionList; 
	}
	
	public static boolean checkBound(char row, int column) {  //A=row, 1=column
		if(row == 'A' || row == 'B' || row == 'C' || row == 'D') {
			if( column > 0 && column < 5 ) {
				return true;
			}else {
				return false;
			}
			
		}else {
			return false;
		}
	}
	
	
	
	public ArrayList<Action> finalActions(ArrayList<Action> actions){ //returns the actions of highest value only
		int maxVal = 0;
		ArrayList<Action> actionsN = new ArrayList<Action>();
		for(Action action: actions) {
			if(action.val > maxVal) maxVal = action.val;	
		}
		for(Action action: actions) {
			if(action.val == maxVal) actionsN.add(action);
		}
		return actionsN;
	}
	
	
	
	public static void main(String[] args) {
	
		Board b = new Board(1);
		b.print();
		State state = new State('b',b);
		state.getActions();
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("dsgfcs");
		Action a1 = new Action(0, list);
		Action a2 = new Action(1, list);
		Action a3 = new Action(1, list);
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(a1);
		actions.add(a2);
		actions.add(a3);
		System.out.println("Before ");
		for(Action a: actions) System.out.println(a.toString());
		actions = state.finalActions(actions);
		System.out.println("After ");
		for(Action a: actions) System.out.println(a.toString());
		
	}

}
