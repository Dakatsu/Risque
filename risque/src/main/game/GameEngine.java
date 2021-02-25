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
	 * Default constructor for the GameEngine.
	 */
	public GameEngine() {
		d_players = new LinkedList<>();
		d_map = new Map();
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
		return d_map.loadFromFile(p_mapName);
	}
	
	/**
	 * Saves a map to a file name.
	 * @param p_mapName The name for the new map.
	 * @return True if the map was successfully saved, otherwise false.
	 */
	public boolean saveMap(String p_mapName) {
		return d_map.saveToFile(p_mapName);
	}
	
	/**
	 * Starts the process for terminating the engine.
	 */
	public void finishAndQuit() {
		d_wantsExit = true;
	}
}
