import java.util.ArrayList;

//Add function to check for Actions in next state from Lee
public class State {
	
	char player;
	Board board;
	int utility; //heuristic for non terminal state
	Boolean terminal;
	State parent;
	ArrayList<Action> actions; // Set of possible actions in this state
	
	public State(char player, Board board) {
		this.player = player;
		this.board = board;
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
					moves = getPossActions(poss, isKing);
					
					if(moves != null) actions.addAll(moves);
					if(this.player == 'b' && moves != null) {
						for(Action act: moves)
							if(act != null)
								for(String move: act.moves) {
									act.moves.add(this.board.flipPos(move));
									act.moves.remove(move);
								}
					}
					
				}
				else continue;
			}
		}
		this.board.print();
		this.board.flipArr();
		return this.actions;
	}
	
	public ArrayList<Action> getPossActions(String poss, Boolean isKing) { //get all possible moves for a piece
		ArrayList<String> possDiags = possDiagonals(poss, isKing); 
		System.out.printf("Piece at %s and %b:\n",poss,isKing);
		for(String diag: possDiags) {
			System.out.println(this.board.flipPos(diag));
		}
		System.out.println();
		ArrayList<Action> possMoves = null;
		return possMoves;
	}
	
	public static ArrayList<String> possDiagonals(String currPos, boolean isKing) { //arraylist of strings
		ArrayList<String> possDiagonals= new ArrayList<String>();
		int column;
		char row;
		
		//separate A1 to A & 1
				row = currPos.charAt(0);
				char ab = (char)currPos.charAt(1);
				column =Character.getNumericValue(ab);// Integer.toString(Character.toString ((char) currPos.charAt(1)));
		
		if(isKing == true) {  //checking if it's king
			
			char rBefore = (char)((int)row - 1);   //row before
			char rAfter = (char)((int)row + 1);   //row after
			
			int cBefore = column - 1; //column before
			int cAfter = column + 1; //column after
			
			//checking boundaries
			if(rBefore == 'A' || rBefore == 'B' || rBefore == 'C' || rBefore == 'D') {
				if( cBefore > 0 && cBefore < 5 ) {
					possDiagonals.add(Character.toString(rBefore) + Integer.toString(cBefore));
				}
				if(cAfter == 1 || cAfter == 2 || cAfter == 3 || cAfter == 4) {
					possDiagonals.add(Character.toString(rBefore) + Integer.toString(cAfter));
				}
			}
			if(rAfter == 'A' || rAfter == 'B' || rAfter == 'C' || rAfter == 'D') {
				if( cBefore > 0 && cBefore < 5 ) {
					possDiagonals.add(Character.toString(rAfter) + Integer.toString(cBefore));
				}
				if(cAfter == 1 || cAfter == 2 || cAfter == 3 || cAfter == 4) {
					possDiagonals.add(Character.toString(rAfter) + Integer.toString(cAfter));
				}
			}
			
		}else { //not king - can only move forward

			char rBefore = (char)((int)row - 1);
			
			int cBefore = column - 1; //column before
			int cAfter = column + 1;  //column after
			
			if(rBefore == 'A' || rBefore == 'B' || rBefore == 'C' || rBefore == 'D') {
				if( cBefore > 0 && cBefore < 5 ) {
					possDiagonals.add(Character.toString(rBefore) + Integer.toString(cBefore));
				}
				if(cAfter == 1 || cAfter == 2 || cAfter == 3 || cAfter == 4) {
					possDiagonals.add(Character.toString(rBefore) + Integer.toString(cAfter));
				}
			}
		}
		return possDiagonals;
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(1);
		b.print();
		State state = new State('b',b);
		state.getActions();
		
	}

}
