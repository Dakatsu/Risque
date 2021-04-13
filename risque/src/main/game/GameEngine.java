package main.game;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import main.console.Console;
import main.console.LogEntryBuffer;
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
	 * The current phase the game is in.
	 */
	private Phase d_currentPhase;
	
	/**
	 * The list of cards the game can give players.
	 */
	private LinkedList<String> d_cardOptions;
	
	/**
	 * Default constructor for the GameEngine.
	 */
	public GameEngine() {
		d_observers = new LinkedList<>();
		d_players = new LinkedList<>();
		d_map = null;
		d_currentPhase = new StartupPhase(this);
		d_cardOptions = new LinkedList<>();
		d_cardOptions.add("bomb");
		d_cardOptions.add("airlift");
		d_cardOptions.add("negotiate");
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
		/**
		 * Allow null returns in case this is used with an object that may not have been successfully created.
		 */
		if (p_entity != null) {
			p_entity.setEngine(this);
			return p_entity;
		}
		return null;
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
	 * Changes the map to a new one.
	 * @param p_map The new map.
	 */
	public void setMap(Map p_map) {
		d_map = p_map;
	}
	
	/** 
	 * Loads a map, replacing the current one.
	 * @param p_mapName The name of the map to be loaded. 
	 */
	public void loadMap(String p_mapName) throws IOException {
		d_currentPhase.loadMap(p_mapName);
	}
	
	/**
	 * Saves a map to a file name.
	 * @param p_mapName The name for the new map.
	 */
	public void saveMap(String p_mapName) {
		d_currentPhase.saveMap(p_mapName);
	}
	
	/**
	 * Prints the map to the console.
	 * If the game has begun, it prints a strategic gameplay view.
	 */
	public void showMap() {
		d_currentPhase.showMap();
	}
	
	/**
	 * Edits the map (if the phase allows it).
	 * @param p_mapName The name of the map to edit. It is created if it does not exist.
	 */
	public void editMap(String p_mapName) {
		d_currentPhase.editMap(p_mapName);
	}
	
	/**
	 * Adds a new continent to the map (if valid in this game phase).
	 * @param p_cID The desired ID for this continent.
	 * @param p_cValue The number of bonus armies for this new continent.
	 */
	public void addContinent(int p_cID, int p_cValue) {
		d_currentPhase.addContinent(p_cID, p_cValue);
	}
	
	/**
	 * Removes a continent from the map (if valid in this game phase).
	 * @param p_cID The ID of the continent to remove.
	 */
	public void removeContinent(int p_cID) {
		d_currentPhase.removeContinent(p_cID);
	}
	
	/**
	 * Adds a territory to the map (if valid in this game phase).
	 * @param p_tID The desired ID for this territory.
	 * @param p_cID The continent for this ID.
	 */
	public void addTerritory(int p_tID, int p_cID) {
		d_currentPhase.addTerritory(p_tID, p_cID);
	}
	
	/**
	 * Removes the territory at the given index (if valid in this game phase).
	 * @param p_tID The ID of the territory to remove.
	 */
	public void removeTerritory(int p_tID) {
		d_currentPhase.removeTerritory(p_tID);
	}
	
	/**
	 * Makes two territories neighbours (if the were not already and if valid in this game phase).
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void addNeighbours(int p_firstID, int p_secondID) {
		d_currentPhase.addNeighbours(p_firstID, p_secondID);
	}
	
	/**
	 * Removes the link between two territories (if valid in this game phase).
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void removeNeighbours(int p_firstID, int p_secondID) {
		d_currentPhase.removeNeighbours(p_firstID, p_secondID);
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
	 * Gets a player by their name if a player exists with that name.
	 * @param p_playerName The ID of the player.
	 * @return The first player with that name, if there is one.
	 */
	public Player getPlayerByName(String p_playerName) {
		for (Player l_player : d_players) {
			if (l_player.getName().equalsIgnoreCase(p_playerName)) {
				return l_player;
			}
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
	 * Uses the bomb card on a territory.
	 * @param p_targetID The territory to bomb.
	 */
	public void bomb(int p_targetID) {
		d_currentPhase.createBombOrder(p_targetID);
	}
	
	/**
	 * Uses the airlift card to transfer armies from one territory to another.
	 * @param p_sourceID The ID of the source territory of the armies.
	 * @param p_destinationID The ID of the destination territory for the armies.
	 * @param p_numArmies The number of armies to transfer.
	 */
	public void airlift(int p_sourceID, int p_destinationID, int p_numArmies) {
		d_currentPhase.createAirliftOrder(p_sourceID, p_destinationID, p_numArmies);
	}
	
	/**
	 * Uses the negotiate card to prevent the current player and another player from fighting.
	 * @param p_player The the player to negotiate cease-fire with.
	 */
	public void negotiate(Player p_player) {
		if (p_player != null) {
			d_currentPhase.createNegotiateOrder(p_player);
		}
		else {
			broadcastMessage("Invalid parameters for the negotiate command.~");
		}
	}
	
	/**
	 * Signals that the current player does not want to issue any more orders.
	 */
	public void finishOrders() {
		d_currentPhase.finishOrders();
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
	 * Starts the process of quitting the game.
	 */
	public void startQuit() {
		for (GameObserver d_observer : d_observers) {
			d_observer.onAddMessage("...Quitting Risque...");
			d_observer.onQuit();
		}
	}
	
	/**
	 * Gets the owner of a specific territory.
	 * @param p_territory The territory in question.
	 * @return The owner of p_territory (or null if unowned).
	 */
	public Player getTerritoryOwner(Territory p_territory) {
		for (Player l_player : d_players) {
			if (l_player.ownsTerritory(p_territory)) {
				return l_player;
			}
		}
		return null;
	}
	
	/**
	 * Gets the owner of a specific continent.
	 * @param p_continent The continent in question.
	 * @return The owner of p_continent (or null if unowned).
	 */
	public Player getContinentOwner(Continent p_continent) {
		for (Player l_player : d_players) {
			if (l_player.ownsContinent(p_continent)) {
				return l_player;
			}
		}
		return null;
	}
	
	/**
	 * Returns a shallow copy of the list of card options.
	 * @return The card options list.
	 */
	public LinkedList<String> getCardOptions() {
		return new LinkedList<String>(d_cardOptions);
	}
	
	/**
	 * Handles the transfer of a territory to a new player.
	 * @param p_territory The territory to transfer ownership of.
	 * @param p_conqueror The new owner of the territory.
	 */
	public void changeTerritoryOwner(Territory p_territory, Player p_conqueror) {
		if (p_territory != null) {
			Player l_prevOwner = getTerritoryOwner(p_territory);
			if (l_prevOwner != null) {
				l_prevOwner.removeOwnedTerritory(p_territory);
				l_prevOwner.removeOwnedContinent(p_territory.getContinent());
			}
			if (p_conqueror != null) {
				p_conqueror.addOwnedTerritory(p_territory);
				// Determine whether to mark the continent as owned by this player.
				Continent l_continent = p_territory.getContinent();
				boolean l_doesOwnContinent = true;
				LinkedList<Territory> l_cTerritories = l_continent.getTerritories();
				for (Territory l_cTerritory : l_cTerritories) {
					if (!p_conqueror.ownsTerritory(l_cTerritory)) {
						l_doesOwnContinent = false;
						break;
					}
				}
				if (l_doesOwnContinent) {
					p_conqueror.addOwnedContinent(l_continent);
				}
			}
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
		LogEntryBuffer l_log = new LogEntryBuffer(l_gameEngine);
		l_gameEngine.broadcastMessage("Welcome to Risque!");
	}
}
