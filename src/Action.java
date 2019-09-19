import java.util.ArrayList;

public class Action implements Comparable<Action> {
	String curr;
//	State parent;
//	ArrayList<State> children;
	String piece;
	ArrayList<String> moves; //sequence of moves. 1+
	Boolean capture;
	Integer val; //number of moves in sequence i.e moves.length()
	
	public Action(int val, ArrayList<String> moves) {
		this.moves = moves;
		this.val = val;
		
	}
	
	@Override
	public int compareTo(Action action) {
		return this.val.compareTo(action.val);
	}
	
	public Action() {
		this.moves = new ArrayList<String>();
	}

	public String toString() {
		String str = moves.get(0);
		for(int i=1; i<moves.size(); i++) {
			str += moves.get(i);
		}
		return str;
	}
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
