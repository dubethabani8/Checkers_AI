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
	
	Boolean contains(String move) {
		Boolean contains = false;
		for(String str: moves) {
			if(this.moves.get(0).equals(move))
				contains = true;
		}
		return contains;
	}
	
	Boolean equals(String str) { //checks if a move like A1-B2-C3 is equal to A1-B2,B2-C3
		ArrayList<String> temp = new ArrayList<String>();
		Boolean equals = true;
		for(int i=0; i <= str.length()-5; i += 3) {
			String m = str.substring(i,i+5);
			temp.add(m);
		}
		for(String s: temp) {
			if(!this.moves.contains(s)) {
				equals = false;
			}
		}
		return equals;
	}
	

	public static void main(String[] args) {
		Action a = new Action();
		ArrayList<String> moves = new ArrayList<String>();
		moves.add("A1-B2");
		moves.add("B2-C2");
		a.moves = moves;
		System.out.println(a.equals("A1-B2-C3"));;
	}

}
