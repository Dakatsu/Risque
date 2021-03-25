package main.game;

import java.util.LinkedList;

import main.console.Console;
import main.controller.Controller;

/**
 * From grading sheet:
 * "Implementation of a GameEngineclass implementing and controlling the game phases according to the Warzone rules."
 * 
 * @author Kyle
 *
 */
public class GameEngine {
	/**
	 * The controller we're linked to.
	 */
	private Controller d_controller;
	
	/**
	 * The console we're linked to.
	 */
	private Console d_console;
	
	/**
	 * The current map we have loaded for gameplay or editing.
	 */
	private Map d_map;
	
	/**
	 * All players in the game.
	 */
	private LinkedList<Player> d_players;
	
	/**
	 * The minimum number of armies assigned on each round.
	 */
	private int d_minArmies;
	
	/**
	 * The index of the next player that gets to issue orders.
	 */
	private int d_nextPlayer;
	
	/**
	 * The current phase the game is in.
	 */
	private Phase d_currentPhase;
	
	/**
	 * Default constructor for the GameEngine.
	 */
	public GameEngine() {
		d_players = new LinkedList<>();
		d_map = onCreateEntity(new Map());
		d_minArmies = 3;
		d_currentPhase = new StartupPhase(this);
	}
	
	/**
	 * Gets the controller currently connected to this.
	 * @return the current controller
	 */
	public Controller getController() {
		return d_controller;
	}
	
	/**
	 * Sets the controller.
	 * @param p_controller the new controller
	 */
	public void setController(Controller p_controller) {
		d_controller = p_controller;
	}
	
	/**
	 * Gets the console currently connected to this.
	 * @return the current console
	 */
	public Console getConsole() {
		return d_console;
	}
	
	/**
	 * Called whenever the game engine creates an entity. Just ensures it has a connection to the game engine.
	 * @param p_entity The new entity.
	 */
	public <T extends GameEntity> T onCreateEntity(T p_entity) {
		p_entity.setEngine(this);
		return p_entity;
	}
	
	/**
	 * Sets the console.
	 * @param p_console the new console
	 */
	public void setConsole(Console p_console) {
		d_console = p_console;
	}
	
	/**
	 * Gets the current map.
	 * @return The current map.
	 */
	public Map getMap() {
		return d_map;
	}
	
	/**
	 * Loads a map, replacing the current one.
	 * @param p_mapName The name of the map to be loaded.
	 * @return True if the map was successfully loaded, otherwise false.
	 */
	public boolean loadMap(String p_mapName) {
		if (!isGameInProgress()) {
			return d_map.loadFromFile(p_mapName);
		}
		return false;
	}
	
	/**
	 * Saves a map to a file name.
	 * @param p_mapName The name for the new map.
	 * @return True if the map was successfully saved, otherwise false.
	 */
	public boolean saveMap(String p_mapName) {
		if (!isGameInProgress()) {
			return d_map.saveToFile(p_mapName);
		}
		return false;
	}
	
	/**
	 * Prints the map to the console.
	 * If the game has begun, it prints a strategic gameplay view.
	 */
	public void printMap() {
		d_currentPhase.showMap();
	}
	
	/**
	 * Gets the number of players.
	 * @return The number of players.
	 */
	public int getNumPlayers() {
		return d_players.size();
	}
	
	/**
	 * Adds a new player.
	 * @param p_name The new player's name.
	 * @return True if a player was added. False otherwise.
	 */
	public boolean addPlayer(String p_name) {
		if (!isGameInProgress()) {
			Player l_newPlayer = onCreateEntity(new Player(p_name));
			if (l_newPlayer != null) {
				broadcastMessage("Player \"" + p_name + "\" added. Total Players: " + getNumPlayers());
				return d_players.add(l_newPlayer);
			}
			else {
				broadcastMessage("Error: could not add player.");
				return false;
			}
		}
		broadcastMessage("Error: players cannot be added during gameplay");
		return false;
	}
	
	/**
	 * Removes a player from the game with the given name.
	 * @param p_name The name of the player to remove. Not case sensitive.
	 * @return True if a player was removed, false otherwise.
	 */
	public boolean removePlayer(String p_name) {
		if (!isGameInProgress()) {
			for (int l_idx = 0; l_idx < getNumPlayers(); l_idx++) {
				String l_playerName = d_players.get(l_idx).getName();
				if (l_playerName.equalsIgnoreCase(p_name)) {
					d_players.remove(l_idx);
					broadcastMessage("Player \"" + l_playerName + "\" removed. Total Players: " + getNumPlayers());
					return true;
				}
			}
			broadcastMessage("Error: no players by that name exist in this game.");
			return false;
		}
		broadcastMessage("Error: players cannot be removed during gameplay");
		return false;
	}
	
	/**
	 * Returns whether the game is in progress.
	 * @return True if the game is in progress. False if we are in the pre-game and/or editing the map.
	 */
	public boolean isGameInProgress() {
		return d_currentPhase.getClass() != StartupPhase.class;
	}
	
	/** 
	 * Assigns territories and begins the game. 
	 * @return True if the territories were assigned and the game has started, otherwise false.
	 */
	public boolean assignTerritories() {
		// Print out an error message if we cannot start the game.
		if (!d_map.validateMap()) {
			broadcastMessage("Error: a valid map must be loaded.");
			return false;
		}
		else if (getNumPlayers() < 2) {
			broadcastMessage("Error: cannot start a game with just one player.");
			return false;
		}
		else if (d_map.getNumTerritories() < getNumPlayers()) {
			broadcastMessage("Error: there must be at least one territory per player.");
			return false;
		}
		// Otherwise, divvy up the territories between the players.
		// TODO: Make this actually random and have some balance calculations.
		for (int l_idx = 1; l_idx <= d_map.getNumTerritories(); l_idx++) {
			d_players.get(l_idx % getNumPlayers()).addOwnedTerritory(d_map.getTerritory(l_idx));
		}
		calculateArmiesPerPlayer();
		d_nextPlayer = 0;
		d_currentPhase = new IssueOrderPhase(this);
		broadcastMessage("Territories have been assigned and the game has started. Good luck!");
		return true;
	}
	
	/**
	 * Calculates the number of armies to give to each player and sets them.
	 */
	public void calculateArmiesPerPlayer() {
		// Start every player off with a minimum number of armies.
		for (Player l_player : d_players) {
			// Start every player off with a minimum number of armies.
			l_player.setNumUndeployedArmies(0);
			// Then each player gets a number according to the continents they control.
			for (Continent l_continent : l_player.getOwnedContinents()) {
				l_player.addUndeployedArmies(l_continent.getBonusArmies());
			}
		}
	}
	
	/**
	 * Creates an order to deploy armies for the next player.
	 * @param p_tID The ID of the territory to deploy the armies to.
	 * @param p_num The number of armies desired to be deployed.
	 * @return The number of armies actually deployed.
	 */
	public int deployArmies(int p_tID, int p_num) {
		// TODO: Next player should be player that actually has armies to deploy.
		// TODO: Should only be able to deploy to your own territory.
		if (p_tID > 0 && p_tID <= d_map.getNumTerritories()) {
			Player l_player = d_players.get(d_nextPlayer);
			Territory l_territory = d_map.getTerritory(p_tID);
			// Cannot deploy to a territory we do not control.
			if (!l_player.ownsTerritory(l_territory)) {
				broadcastMessage("Cannot deploy to a territory " + l_player.getName() + " does not control.");
				return 0;
			}
			// Calc next player.
			d_nextPlayer = (d_nextPlayer + 1) % getNumPlayers();
			while (d_players.get(d_nextPlayer).getNumUndeployedArmies() == 0) {
				d_nextPlayer = (d_nextPlayer + 1) % getNumPlayers();
			}
			// Cannot deploy more armies than we have.
			int l_numArmies = l_player.removeUndeployedArmies(p_num);
			l_player.issueOrder(onCreateEntity(new DeployOrder(l_territory, l_numArmies)));
			broadcastMessage(l_player.getName() + " will deploy " + l_numArmies + " to " + l_territory.getName() + ".");
			return l_numArmies;
		}
		broadcastMessage("Invalid territory. Please try again.");
		return 0;
	}
	
	/**
	 * Executes every player's order. The orders are executed in round-robin order,
	 * e.g. player 1's first order is executed, then player 2, etc. until every player has
	 * issued their first order. Then it begins with player 1 again.
	 * Players are skipped if they have no orders to issue.
	 * After all orders are issued, the number of armies is calculated again.
	 */
	public void executeOrders() {
		boolean l_areThereUnexecutedOrders = true;
		while (l_areThereUnexecutedOrders) {
			l_areThereUnexecutedOrders = false;
			for (Player l_player : d_players) {
				if (l_player.hasOrdersLeftToExecute()) {
					l_player.nextOrder().execute();
					if (l_player.hasOrdersLeftToExecute()) {
						l_areThereUnexecutedOrders = true;
					}
				}
			}
		}
	}
	
	/**
	 * Broadcasts a message to any connected observers, like the console.
	 * @param p_message The string to output.
	 */
	public void broadcastMessage(String p_message) {
		if (d_console != null) {
			d_console.addMessage(p_message);
		}
	}
}
