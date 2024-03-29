import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

public class Agent {
	//1-random, 2-minimax, 3-minimax+alpha-beta pruning, 4-H-minimax+fixed depth cutoff+alpha-beta pruning
	int type;
	int depthLim;
	
	public Agent(int type) {
		this.type = type;
	}
	
	public Action getBestAction(ArrayList<Action> actions) { //returns null if no actions possible
		Action best = new Action();
		PriorityQueue<Action> sorted = new PriorityQueue<Action>(Collections.reverseOrder());
		if(actions != null )for(Action act: actions) {
			sorted.add(act);
		}
		if(!sorted.isEmpty()) best = sorted.peek();
		else return null;
		return best;
 	}
	

	
	public Action getNextAction(State state) {
		Action action = null;
		switch(this.type) {
		case 1:
			action = random(state);
			break;
		case 2:
			action = minimax(state);
			break;
		case 3:
			action = minimax_AB(state);
			break;
		case 4:
			action = H_minimax_AB(state);
			break;
		}
		return action;
		
	}
	
	public Action random(State state) {
		ArrayList<Action> actions = state.getActions(); //Get all actions
		Action best = new Action();
		if(actions == null) return null;
		else { //chose random action from the possible actions
			Random rn = new Random();
			int x = rn.nextInt(actions.size());
			best = actions.get(x); //FIX HERR
		}
		return best;
	}
	
	
	public Action minimax(State state) {


		return null;
	}


	
	public Action minimax_AB(State state) {
		return null;
	}
	
	public Action H_minimax_AB(State state) {
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
