package main.game;

import java.util.LinkedList;

import main.console.Console;
import main.controller.Controller;

/**
 * This is the root of the game's "Model" in the MVC architecture.
 * It manages most game functions and communicates with the view classes (e.g. the console).
 * The main function also provides the entry-point for this program.
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
	 * The list of observers we're linked to.
	 */
	private LinkedList<GameObserver> d_observers;
	
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
		d_observers = new LinkedList<>();
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
	 * Adds a new observer.
	 * @param p_observer The new observer.
	 */
	public void addObserver(GameObserver p_observer) {
		d_observers.add(p_observer);
	}
	
	/**
	 * Removes an observer (if they were observing us).
	 * @param p_observer The observer to remove.
	 * @return Whether p_observer was registered as an observer.
	 */
	public boolean removeObserver(GameObserver p_observer) {
		return d_observers.remove(p_observer);
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
	 * Gets a shallow copy of the list of players.
	 * @return The list of players.
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Player> getPlayers() {
		return (LinkedList<Player>)d_players.clone();
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
	 */
	public void deployArmies(int p_tID, int p_num) {
		d_currentPhase.createDeployOrder(p_tID, p_num);
	}
	
	/**
	 * Creates an order to advance armies for the next player.
	 * @param p_fromID The ID of the territory the armies are moving from.
	 * @param p_toID The ID of the territory the armies will go to.
	 * @param p_num The number of armies desired to be advanced.
	 */
	public void advanceArmies(int p_fromID, int p_toID, int p_num) {
		d_currentPhase.createAdvanceOrder(p_fromID, p_toID, p_num);
	}
	
	/**
	 * Signals that the current player does not want to issue any more orders.
	 */
	public void finishOrders() {
		
	}
	
	/**
	 * Broadcasts a message to any connected observers, like the console.
	 * @param p_message The string to output.
	 */
	public void broadcastMessage(String p_message) {
		for (GameObserver d_observer : d_observers) {
			d_observer.onAddMessage(p_message);
		}
	}
	
	/**
	 * The main function that will start the key modules for the program.
	 * The console will create an InputHandler as a separate thread.
	 * The program terminates when that thread finishes, which happens after the "quit" command
	 * is typed into the console.
	 * @param args Unused program arguments.
	 */
	public static void main(String[] args) {
		System.out.println("...Starting Risque...");
		GameEngine l_gameEngine = new GameEngine();
		Controller l_controller = new Controller(l_gameEngine);
		Console l_console = new Console(l_gameEngine, l_controller);
	}
}
