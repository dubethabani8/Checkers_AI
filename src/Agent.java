
public class Agent {
	//1-random, 2-minimax, 3-minimax+alpha-beta pruning, 4-H-minimax+fixed depth cutoff+alpha-beta pruning
	int type; 
	
	public Agent(int type) {
		this.type = type;
	}
	
	public String getNextAction(State state) {
		String action = null;
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
	
	public String random(State state) {
		return null;
	}
	
	public String minimax(State state) {
		return null;
	}
	
	public String minimax_AB(State state) {
		return null;
	}
	
	public String H_minimax_AB(State state) {
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
