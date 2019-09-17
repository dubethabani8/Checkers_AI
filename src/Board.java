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
		PriorityQueue<Action> pQueue = new PriorityQueue<Action>();
		//IMplement Comparator prot class to sort by Action.val
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
		int j = l-1-y;
		System.out.println("fipped"+ x + " " + y + " to " + i + " " + j);
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
	
	public static void main(String[] args) {
		Board b = new Board(2);
		System.out.println(b.getSquareStr(7, 7));
		//b.print();

	}

}
