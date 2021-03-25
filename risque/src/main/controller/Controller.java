package main.controller;

import main.console.Console;
import main.game.GameEngine;

/**
 * Class to handle calling commands on different game elements.
 * 
 * @author Kyle
 *
 */
public class Controller {
	private GameEngine d_engine;
	private Console d_console;
	
	/**
	 * Constructor for the controller.
	 * @param p_engine the game engine.
	 */
	public Controller(GameEngine p_engine) {
		setEngine(p_engine);
		if (d_engine != null) {
			d_engine.setController(this);
		}
	}
	
	/**
	 * Gets the game engine currently connected to this.
	 * @return the current game engine
	 */
	public GameEngine getEngine() {
		return d_engine;
	}
	
	/**
	 * Sets the game engine.
	 * @param p_engine the new game engine
	 */
	public void setEngine(GameEngine p_engine) {
		d_engine = p_engine;
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
	*Will start the process for executing "editcontinent" command.
	*@param p_add_remove_continent This parameter includes continent which is to be added or removed.
	*/
	public void editContinent(String p_add_remove_continent) {
	//TODO
	}

	/**
	*Will start the process for executing "editcountry" command.
	*@param p_add_remove_country This parameter includes country which is to be added or removed.
	*/
	public void editCountry(String p_add_remove_country) {
	//TODO
	}

	/**
	*Will start the process for executing "editneighbor" command.
	*@param p_add_remove_neighbor This parameter includes neighbor which is to be added or removed.
	*/
	public void editNeighbour(String p_add_remove_neighbor) {
	//TODO
	}

	/**
	 * Asks the game engine to print the map to the console.
	 */
	public void printMap() {
		d_engine.showMap();
	}

	/**
	* Will start the process for executing "savemap" command.
	* @param p_fileName File name to which a map is to be saved.
	*/
	public void saveMap(String p_fileName) {
		d_engine.saveMap(p_fileName);
	}

	/**
	 * Will start the process for executing "editmap" command.
	 * @param p_fileName File name from which a map is to be edited.
	 * @return TODO: This comman does nothing right now!
	 */
	public boolean editMap(String p_fileName) {
		return false;
	}
	
	/**
	 * Will start the process for executing "validatemap" command.
	 */
	public void validateMap() {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("There is no map.");
		}
		else if (d_engine.getMap().validateMap()) {
			d_engine.broadcastMessage("The map is valid.");
		}
		else {
			d_engine.broadcastMessage("The map is invalid.");
		}
	}

	/**
	 * Will start the process for executing "loadmap" command.
	 * @param p_fileName File name from which a map is to be loaded.
	 * @return True if the map was successfully loaded, otherwise false.
	 */
	public boolean loadMap(String p_fileName) {
		return d_engine.loadMap(p_fileName);
	}
	
	/**
	 * Adds a player to the game.
	 * @param p_name The new player's name.
	 * @return True if the player was added, false otherwise.
	 */
	public boolean addPlayer(String p_name) {
		return d_engine.addPlayer(p_name);
	}
	
	/**
	 * Removes a player from the game.
	 * @param p_name The name of the player to remove.
	 * @return True if a player was removed, false if none were removed (e.g. name did not exist).
	 */
	public boolean removePlayer(String p_name) {
		return d_engine.removePlayer(p_name);
	}
	
	/**
	 * Adds a new continent to the map.
	 * @param p_cID The desired ID for this continent.
	 * @param p_cValue The number of bonus armies for this new continent.
	 * @return True if the continent was created, otherwise false.
	 */
	public boolean addContinent(int p_cID, int p_cValue) {
		// TODO: Do gameplay check elsewhere.
		if (d_engine.isGameInProgress()) {
			return false;
		}
		return d_engine.getMap().createContinent(p_cID, p_cValue);
	}
	
	/**
	 * Removes a continent from the map.
	 * @param p_cID The ID of the continent to remove.
	 * @return True if a continent was removed, otherwise false.
	 */
	public boolean removeContinent(int p_cID) {
		// TODO: Do gameplay check elsewhere.
		if (d_engine.isGameInProgress()) {
			return false;
		}
		return d_engine.getMap().deleteContinent(p_cID);
	}
	
	/**
	 * Adds a territory to the map.
	 * @param p_tID The desired ID for this territory.
	 * @param p_cID The continent for this ID.
	 * @return True if the territory was created, otherwise false.
	 */
	public boolean addTerritory(int p_tID, int p_cID) {
		// TODO: Do gameplay check elsewhere.
		if (d_engine.isGameInProgress()) {
			return false;
		}
		return d_engine.getMap().createTerritory(p_tID, p_cID);
	}
	
	/**
	 * Removes the territory at the given index.
	 * @param p_tID The ID of the territory to remove.
	 * @return True if a territory was removed, false otherwise.
	 */
	public boolean removeTerritory(int p_tID) {
		// TODO: Do gameplay check elsewhere.
		if (d_engine.isGameInProgress()) {
			return false;
		}
		return d_engine.getMap().deleteTerritory(p_tID);
	}
	
	/**
	 * Makes two territories neighbours (if the were not already).
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 * @return True if the territories are newly neighbours, false otherwise.
	 */
	public boolean addNeighbours(int p_firstID, int p_secondID) {
		// TODO: Do gameplay check elsewhere.
		if (d_engine.isGameInProgress()) {
			return false;
		}
		return d_engine.getMap().addBorder(p_firstID, p_secondID);
	}
	
	/**
	 * Removes the link between two territories.
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 * @return True if a link between them was removed, otherwise false.
	 */
	public boolean removeNeighbours(int p_firstID, int p_secondID) {
		// TODO: Do gameplay check elsewhere.
		if (d_engine.isGameInProgress()) {
			return false;
		}
		return d_engine.getMap().deleteBorder(p_firstID, p_secondID);
	}

	/**
	 *Will start the process for executing "assigncountries" command.
	 */
	public void assignTerritories() {
		d_engine.assignTerritories();
	}

	/**
	 * Issues a deploy order.
	 * @param p_tID The ID of the territory to deploy to.
	 * @param p_numArmies The number of armies to deploy.
	 */
	public void deploy(int p_tID, int p_numArmies) {
		d_engine.deployArmies(p_tID, p_numArmies);
	}
	
	/**
	 * Issues an advance order.
	 * @param p_fromID The ID of the origin territory.
	 * @param p_toID The ID of the destination territory.
	 * @param p_numArmies The number of armies to advance.
	 */
	public void advance(int p_fromID, int p_toID, int p_numArmies) {
		d_engine.advanceArmies(p_fromID, p_toID, p_numArmies);
	}
	
	/**
	 * Ends this current player's turn without issuing any orders, and signals that they have no more orders to issue.
	 */
	public void finishOrders() {
		d_engine.finishOrders();
	}
}
