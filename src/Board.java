
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
				if(i%2==0 && j<this.size-1) j++;
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
	
	

	public static void main(String[] args) {
		Board b = new Board(2);
		b.print();

	}

}
