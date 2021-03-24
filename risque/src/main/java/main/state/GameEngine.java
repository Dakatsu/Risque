package main.state;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import main.game.Map;

/**
 *  Context of the State pattern. 
 *  It contains a State object (in this example the State class is the class Phase). 
 */

public class GameEngine {
	/**
	 * State object of the GameEngine 
	 */
	List<Territory> mapNew;
	List<Player> players;
	private Phase gamePhase;
	public Map map = new Map();
	int mystart;
	int mycommand;
	

	/**
	 * Method that allows the GameEngine object to change its state.  
	 * @param p_phase new state to be set for the GameEngine object.
	 */
	public void setPhase(Phase p_phase) {
		gamePhase = p_phase;
		System.out.println("new phase: " + p_phase.getClass().getSimpleName());
	}
	
	GameEngine() {
		mapNew = new ArrayList<Territory>();
		players = new ArrayList<Player>();
	}
	
	public void startNew() {
		int numTurns = 5;

		// create the players
		players.add(new Player("player1"));
		players.add(new Player("player2"));

		// create the map
		mapNew.add(new Territory("territory1", players.get(0), 10));
		mapNew.add(new Territory("territory2", players.get(0), 10));
		mapNew.add(new Territory("territory3", players.get(1), 10));
		mapNew.add(new Territory("territory4", players.get(1), 10));
		printMap();

		// run the game turns
		for (int turn = 1; turn <= numTurns; turn++) {
			boolean an_order = true;
			do {
				for (Player p : players) {
					an_order = p.createOrder(mapNew, players);
					if (!an_order)
						break;
				}
			} while (an_order);
			executeAllOrders();
			printMap();
		}
	}

	public void printMap() {
		System.out.println("===========================MAP============================");
		for (Territory t : mapNew) {
			t.print();
		}
		System.out.println("===========================MAP============================");
	}

	public void executeAllOrders() {
		System.out.println("===============BEGIN EXECUTING ALL ORDERS=================");
		Order order;
		boolean still_more_orders = false;
		do {
			still_more_orders = false;
			for (Player p : players) {
				order = p.getNextOrder();
				if (order != null) {
					still_more_orders = true;
					order.printOrder();
					order.execute();
				}
			}
		} while (still_more_orders);
		System.out.println("===============END EXECUTING ALL ORDERS===================");
	}

	
	/**
	 * This method will ask the user: 
	 * 1. What part of the game they want to start with (edit map or play game). 
	 *      Depending on the choice, the state will be set to a different object, 
	 *      which will set different behavior. 
	 * 2. What command they want to execute from the game. 
	 *      Depending on the state of the GameEngine, each command will potentially 
	 *      have a different behavior. 
	 */
	public void start() {
		Scanner keyboard = new Scanner(System.in);
		do {
			System.out.println("1. Edit Map");
			System.out.println("2. Play Game");
			System.out.println("3. Quit");
			System.out.println("Where do you want to start?: ");
			mystart = keyboard.nextInt();
			switch (mystart) {
			case 1:
				// Set the state to Preload
				setPhase(new Preload(this));
				break;
			case 2:
				// Set the state to PlaySetup
				setPhase(new PlaySetup(this));
				break;
			case 3:
				System.out.println("Bye!");
				return;
			}
			do {
				System.out.println(" =================================================");
				System.out.println("| #   PHASE                   : command           |"); 
				System.out.println(" =================================================");
				System.out.println("| 1.  Any except MainPlay     : load map          |");
				System.out.println("| 2.  Any                     : show map          |");
				System.out.println("| 3.  Edit:PostLoad           : edit country      |");
				System.out.println("| 4.  Edit:Postload           : save map and play |");
				System.out.println("| 5.  Play:PlaySetup          : set players       |");
				System.out.println("| 6.  Play:PlaySetup          : assign countries  |");
				System.out.println("| 7.  Play:MainPlay:Reinforce : reinforce         |");
				System.out.println("| 8.  Play:MainPlay:Attack    : attack            |");
				System.out.println("| 9.  Play:MainPlay:Fortify   : fortify           |");
				System.out.println("| 10. Play                    : end game          |");
				System.out.println("| 0.  Any                     : next phase        |");
				System.out.println(" =================================================");
				System.out.println("enter a " + gamePhase.getClass().getSimpleName() + " phase command: ");
				mycommand = keyboard.nextInt();
				System.out.println(" =================================================");
				//
				// Calls the method corresponding to the action that the user has selected. 
				// Depending on what it the current state object, these method calls will  
				// have a different implementation. 
				//
				switch (mycommand) {
				case 1:
					gamePhase.loadMap();
					break;
				case 2:
					gamePhase.showMap();
					break;
				case 3:
					gamePhase.editCountry();
					break;
				case 4:
					gamePhase.saveMap();
					break;
				case 5:
					gamePhase.setPlayers();
					break;
				case 6:
					gamePhase.assignCountries();
					break;
				case 7:
					gamePhase.reinforce();
					break;
				case 8:
					gamePhase.attack();
					break;
				case 9:
					gamePhase.fortify();
					break;
				case 10:
					gamePhase.endGame();
					break;
				case 0:
					gamePhase.next();
					break;
				default: 
					System.out.println("this command does not exist");
				}
			} while (!(gamePhase instanceof End));
		} while (mycommand != 3);
		keyboard.close();
	}
}
