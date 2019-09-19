import java.util.ArrayList;
import java.util.PriorityQueue;

public class Board {
	int size;
	int [][] set_up;
	
	public Board(int n) { //returns initial board set up
		if(n==1) this.size = 4;
		else if(n==2) this.size = 8;
		
		//create a initial board - 'b','w', 0 black white and empty squares
		this.set_up = new int[size][size];
		for(int i=0; i < this.set_up.length; i++) {
			for(int j=0; j<this.set_up.length; j++) {
				if(i%2==0 && j == 0 && j<this.size-1) j++;
				if(size==4 && i<=0) this.set_up[i][j] = 'b';
				else if(size==4 && i>=3) this.set_up[i][j] = 'w';
				else if(size==8 && i<=2) this.set_up[i][j] = 'b';
				else if(size==8 && i>=5) this.set_up[i][j] = 'w';
				else this.set_up[i][j] = ' ';
				j++;
			}
		}
	}
	
	public void print(){ //prints current board and all pieces on it
		int row = 'A', col = 1;
		for(int i=0; i<=this.size; i++) {
			
			for(int j=0; j<=this.size; j++) {
				if(i==0 && j==0) System.out.print("  ");
				else if(i==0) System.out.printf("%d ",col++);
				else if(j==0) System.out.printf("%c|",row++);
				else System.out.printf("%c|",this.set_up[i-1][j-1]);

			}
			
			System.out.println();
			if(size == 4) System.out.println(" +-+-+-+-+");
			else if(size == 8) System.out.println(" +-+-+-+-+-+-+-+-+");
		}
	}
	
	
	
	public void makeMove(Action action) {
		for(String move:action.moves) {
			if(move!= null) makeSingleMove(move);
		}
	}
	
	public void makeSingleMove(String move) { //takes in example 'D1-C2'
		int j1 = Integer.parseInt(move.substring(1,2))-1;
		int j2 = Integer.parseInt(move.substring(4,5))-1;
		int i1 = getRow(move.charAt(0));
		int i2 = getRow(move.charAt(3));
		
		
		
		if(Math.abs(j2-j1) > 1) {
			int jCaptured = (j1+j2)/2;
			int iCaptured = (i1+i2)/2;
			this.set_up[iCaptured][jCaptured] = ' ';
			this.set_up[i2][j2] = this.set_up[i1][j1];
		}
		else {
			this.set_up[i2][j2] = this.set_up[i1][j1];
		}
		this.set_up[i1][j1] = ' ';
	}
	
	public String getSquareStr(int i, int j) { //returns the string identifier for a certain index
		String row = null;
		int col = j+1;
		switch(i) {
		case 0:
			row = "A";
			break;
		case 1:
			row = "B";
			break;
		case 2:
			row = "C";
			break;
		case 3:
			row = "D";
			break;
		case 4:
			row = "E";
			break;
		case 5:
			row = "F";
			break;
		case 6:
			row = "G";
			break;
		case 7:
			row = "H";
			break;
		}
		String str = row+col;
		return str;
	}
	
	public void flipArr() { //flips array for current player
		int l = this.size;
		int[][] nArr = new int[l][l];
		for(int i=0; i<l; i++) {
			for(int j=0; j<l; j++)
				nArr[l-1-i][l-1-j] = set_up[i][j];
		}
		this.set_up = nArr; 
	}
	
	public String flipPos(String pos) {
		String flipped = null;
		int x = getRow(pos.charAt(0));
		int y = Integer.parseInt(""+pos.charAt(1));
		int l = this.size;
		int i = l-1-x;
		int j = l-y;
		flipped = getSquareStr(i,j);
		return flipped;
	}
	
	public int getRow(char c) {
		int row = 0;
		switch(c) {
		case 'A':
			row = 0;
			break;
		case 'B':
			row = 1;
			break;
		case 'C':
			row = 2;
			break;
		case 'D':
			row = 3;
			break;
		case 'E':
			row = 4;
			break;
		case 'F':
			row = 5;
			break;
		case 'G':
			row = 6;
			break;
		case 'H':
			row = 7;
			break;
		}
		return row;
	}
	
	public ArrayList<String> flipMove(ArrayList<String> moves){
		ArrayList<String> newM = new ArrayList<String>();
		if(moves == null) return null;
		else for(String str: moves) {
			String _1 = str.substring(0, 2);
			String _2 = str.substring(3, 5);
			_1 = flipPos(_1);
			
			_2 = flipPos(_2);
			str = _1 + "-" + _2;
			newM.add(str);
		}
		return newM;
	}
	
//	if(actions == null) return null;
//	else for(Action act: actions) {
//		if(act.moves == null) return null;
//		else for(String move: act.moves) {
//			act.moves.remove(move);
//			move = flipPos(move);
//			act.moves.add(move);
//		}
//	}
	
	public Action flipAction(Action act){
		ArrayList<String> moves = new ArrayList<String>();
		if(act != null) act.moves = flipMove(act.moves);
		return act;
	}
	
	public ArrayList<Action> flipActions(ArrayList<Action> actions){
		ArrayList<Action> nA = new ArrayList<Action>();
		if(actions == null) return null;
		else for(Action act: actions){
			act = flipAction(act);
			nA.add(act);
		}
		return nA;
	}
	
	public ArrayList<String> positions(char player){ //Returns all positions occupied by player
		ArrayList<String> positions = new ArrayList<String>();
		for(int i=0; i<this.size; i++)
			for(int j=0; j<this.size; j++)
				if(this.set_up[i][j] == player) positions.add(getSquareStr(i,j));
		return positions;
	}
	
	
	public static void main(String[] args) {
		Board b = new Board(1);
//		System.out.println(b.getSquareStr(7, 7));
		//b.print();
		ArrayList<String> list = new ArrayList<String>();
		list.add("A4-C2");
		list.add("D1-B3");
		list.add("D1-B3");
		Action act = new Action();
		act.moves = list;
		ArrayList<Action> actions = new ArrayList<Action>();
		actions.add(act);
		System.out.println("BEFORE: " + actions.get(0).moves.toString());
		b.flipActions(actions);
		System.out.println("AFTER: " + actions.get(0).moves.toString());
		
		String x = "D1-B3";
		b.set_up[2][1] = 'b';
		b.print();
		b.makeSingleMove(x);
		b.print();
	}

}
