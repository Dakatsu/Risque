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
	 * Do we want to quit the game?
	 */
	private boolean d_wantsExit = false;
	
	/**
	 * The current map we have loaded for gameplay or editing.
	 */
	private Map d_map;
	
	/**
	 * All players in the game.
	 */
	private LinkedList<Player> d_players;
	
	/**
	 * Is the game in progress? False if we have not started playing yet.
	 */
	private boolean d_isGameInProgress;
	
	/**
	 * The minimum number of armies assigned on each round.
	 */
	private int d_minArmies;
	
	/**
	 * Default constructor for the GameEngine.
	 */
	public GameEngine() {
		d_players = new LinkedList<>();
		d_map = new Map();
		d_minArmies = 3;
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
			Player l_newPlayer = new Player(p_name);
			if (l_newPlayer != null) {
				return d_players.add(l_newPlayer);
			}
		}
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
				if (d_players.get(l_idx).getName().equalsIgnoreCase(p_name)) {
					d_players.remove(l_idx);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns whether the game is in progress.
	 * @return True if the game is in progress. False if we are in the pre-game and/or editing the map.
	 */
	public boolean isGameInProgress() {
		return d_isGameInProgress;
	}
	
	/** 
	 * Assigns territories and begins the game. 
	 * @return True if the territories were assigned and the game has started, otherwise false.
	 */
	public boolean assignTerritories() {
		if (d_map.validateMap() && getNumPlayers() > 1 && d_map.getNumTerritories() >= getNumPlayers()) {
			// Divvy up the territories between the players.
			// TODO: Make this actually random and have some balance calculations.
			for (int l_idx = 1; l_idx <= d_map.getNumTerritories(); l_idx++) {
				d_players.get(l_idx % getNumPlayers()).addOwnedTerritory(d_map.getTerritory(l_idx));
			}
			d_isGameInProgress = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Starts the process for terminating the engine.
	 */
	public void finishAndQuit() {
		d_wantsExit = true;
	}
}
