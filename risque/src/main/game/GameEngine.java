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
	 * Gets the current phase.
	 * @return The current phase.
	 */
	public Phase getPhase() {
		return d_currentPhase;
	}
	
	/**
	 * Sets the phase to a new phase.
	 * @param p_nextPhase The phase to switch to.
	 */
	public void setPhase(Phase p_nextPhase) {
		d_currentPhase.onPhaseEnd(p_nextPhase);
		Phase l_prevPhase = d_currentPhase;
		d_currentPhase = p_nextPhase;
		p_nextPhase.onPhaseStart(l_prevPhase);
	}
	
	/**
	 * Called whenever the game engine creates an entity. Just ensures it has a connection to the game engine.
	 * @param <T> the type of GameEntity that was created.
	 * @param p_entity The new entity.
	 * @return The entity.
	 */
	public <T extends GameEntity> T onCreateEntity(T p_entity) {
		p_entity.setEngine(this);
		return p_entity;
	}
	
	/**
	 * Sets the console.
	 * @param p_console the new console.
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
	public void showMap() {
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
			if (l_newPlayer != null && d_players.add(l_newPlayer)) {
				broadcastMessage("Player \"" + p_name + "\" added. Total Players: " + getNumPlayers());
				return true;
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
	 * Gets a player by their ID (zero-based indexing) if a player exists at that ID.
	 * @param p_playerID The ID of the player.
	 * @return The player if there is one at that ID.
	 */
	public Player getPlayerByID(int p_playerID) {
		if (getNumPlayers() > 0) {
			return d_players.get(p_playerID);
		}
		return null;
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
	 */
	public void assignTerritories() {
		d_currentPhase.assignTerritories();
	}
	
	/**
	 * Creates an order to deploy armies for the next player.
	 * @param p_tID The ID of the territory to deploy the armies to.
	 * @param p_num The number of armies desired to be deployed.
	 * @return The number of armies actually deployed.
	 */
	public int deployArmies(int p_tID, int p_num) {
		// TODO: Next player should be player that actually has armies to deploy.
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
			broadcastMessage(l_player.getName() + " will deploy " + l_numArmies + " to " + l_territory.getDisplayName() + ".");
			return l_numArmies;
		}
		broadcastMessage("Invalid territory. Please try again.");
		return 0;
	}
	
	/**
	 * Creates an order to advance armies for the next player.
	 * @param p_fromID The ID of the territory the armies are moving from.
	 * @param p_toID The ID of the territory the armies will go to.
	 * @param p_numToAdvance The number of armies desired to be advanced.
	 * @return The number of armies actually advanced.
	 */
	public int advanceArmies(int p_fromID, int p_toID, int p_num) {
		// TODO: Next player should be player that actually has armies to deploy.
		// TODO: Should only be able to deploy to your own territory.
		if (!getMap().doesBorderExist(p_toID, p_fromID)) {
			broadcastMessage("Error: territories do not border each other.");
			return 0;
		}
		if (p_fromID == p_toID) {
			broadcastMessage("Error: the territories cannot be the same.");
		}
		if (p_fromID < 1 || p_fromID > d_map.getNumTerritories()) {
			broadcastMessage("Error: invalid origin territory.");
			return 0;
		}
		if (p_toID < 1 || p_toID > d_map.getNumTerritories()) {
			broadcastMessage("Error: invalid destination territory.");
			return 0;
		}
		if (p_num < 1) {
			broadcastMessage("Error: must advance at least one army.");
			return 0;
		}
		Player l_player = d_players.get(d_nextPlayer);
		Territory l_fromTerritory = d_map.getTerritory(p_fromID);
		// Cannot advance from a territory we do not control
		// However, it doesn't matter if we own the destination; it could be lost or gained before this order is executed).
		if (!l_player.ownsTerritory(l_fromTerritory)) {
			broadcastMessage("Error: cannot advance from a territory " + l_player.getName() + " does not control.");
			return 0;
		}
		Territory l_toTerritory = d_map.getTerritory(p_toID);
		// Calc next player.
		d_nextPlayer = (d_nextPlayer + 1) % getNumPlayers();
		// Cannot advance more armies than currently exist in the territory.
		int l_numArmies = Math.min(p_num, l_fromTerritory.getNumArmies());
		l_player.issueOrder(onCreateEntity(new AdvanceOrder(l_fromTerritory, l_toTerritory, l_numArmies)));
		broadcastMessage(l_player.getName() + " will advance " + l_numArmies + " from " + l_fromTerritory.getDisplayName() + " to " + l_toTerritory.getDisplayName() + ".");
		return l_numArmies;
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
