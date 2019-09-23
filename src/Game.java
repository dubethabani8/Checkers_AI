import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Thabani Dube
 *
 */
public class Game {
	Agent agent;
	State currState;
	char agentCol;
	char userCol;
	char currPlayer;
	Boolean gameOver = false;
	
	public Game(Agent agent, State initialState, char userCol) {
		this.agent = agent;
		this.currState = initialState;
		this.userCol = userCol;
		if(this.userCol == 'b') this.agentCol = 'w';
		else this.agentCol = 'b';
	}
	
	public static void main(String[] args) {	
		//check how it is printing
		Scanner sc = new Scanner(System.in);  // Create a Scanner object
		System.out.println("Checkers by Thabani Dube, Arun Ramesh, Changhyun Lee");
		
	    System.out.println("Choose your game:");
	    System.out.println("1. Small 4x4 Checkers");
	    System.out.println("Standard 8x8 Checkers");
	    System.out.print("Your Choice? ");
	    int gameSize = sc.nextInt();
	    Board board = new Board(gameSize);
	    
	    System.out.println("Choose your opponent:");
	    System.out.println("1. An agent that plays randomly\n2. An agent that uses MINIMAX\n3. An agent that uses "
	    		+ "MINIMAX with alpha-beta pruning\n4. An agent that uses H-MINIMAX with a fixed depth cutoff");
	    System.out.print("Your choice? ");  
	    int opponent = sc.nextInt();
	    
	    Agent agent = new Agent(opponent);
	    
	    int depthLim = 0;
		if (opponent == 4) {
			System.out.print("Depth limit? ");
			depthLim = sc.nextInt();
		}
		
		agent.depthLim = depthLim;
		
		System.out.print("Do you want to play BLACK (b) or WHITE (w)? ");
		String blackOrWhite = sc.next();
		
		State currState = new State('b',board);
		Game game = new Game(agent, currState, blackOrWhite.charAt(0));
		game.currPlayer = 'b';
		
		//START GAME PLAY
		game.currState.board.print();
		
		while(!game.gameOver) {
			game.currState.actions = new ArrayList<Action>();
			
			if(game.currPlayer == 'b') System.out.println("Next to move: BLACK");
			else System.out.println("Next to move: WHITE");
			
			//Exchange of moves
			if(game.currPlayer == game.userCol) { //USER PLAY
				ArrayList<Action> actions = game.currState.getActions(); //Get all actions
				if(actions == null) {
					game.gameOver = true; //end game if no actions are available
					System.out.println("\n\nGAME OVER!!!\n" +  "You lose");
					break;
				}
				else {
					Action best = actions.get(0);
					if(best.val == 0) {
						System.out.println("Your move: [src-dst]: ");
						String str = sc.next();
						Boolean valid = false;
						//check if valid action
						for(Action action: actions) {
							if(action.contains(str))
								valid = true;
						}
						if(valid) {
							game.currState.board.makeSingleMove(str, game.currPlayer);
						}
						else{
							System.out.println("Invalid move!!!");
							continue;
						}
					}
					else { //Force player to perform best capture move
						System.out.println("You can only make one of the following");
						for(Action action: actions) {
							System.out.println(action.moves.toString());
						}
						System.out.println("Your move: [src-step-step...-dst]: ");
						String str = sc.next();
						Boolean valid = false;
						for(Action action: actions) {
							if(action.equals(str)) {
								valid = true;
								game.currState.board.makeMove(action, game.currPlayer);
								break;
								//Action best = GET SELECTED SEQUENCE FROM ACTIONS
							}
						}
						if(!valid) {
							System.out.println("Invalid move!!!");
							continue;
						}
						
					} 
				}
				

			}
			else { //AGENT PLAY
				System.out.println("\nI'm thinking...");
				Action action = game.agent.getNextAction(game.currState);
				if(action == null) {
					game.gameOver = true;
					System.out.println("\n\nGAME OVER!!!\n" +  "You win!!!");
					break;
				}
				game.currState.board.makeMove(action, game.agentCol);
				
			}
			game.currState.board.print();
			game.switchPlayer();
		}
		
	} 
	
	
	
	public void switchPlayer() {
		if(this.currPlayer == 'w') {
			this.currPlayer = 'b';
			this.currState.player = 'b';
			this.currState.opponent = 'w';
		}
		else{
			this.currPlayer = 'w';
			this.currState.player = 'w';
			this.currState.opponent = 'b';
			
		}
	}

}
