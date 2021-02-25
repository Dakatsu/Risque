package main.console;

import main.controller.Controller;
import main.game.GameEngine;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to handle input and output to/from the console.
 * 
 * Non-exhaustive list of commands that need to work:
 *   editcontinent -add continentID continentvalue -remove continentID
 *   editcountry -add countryID   continentID -remove countryID
 *   editneighbor-add countryIDneighborcountryID-remove countryIDneighborcountryID
 *   showmap (in map editor and in gameplay)
 *   savemap filename
 *   editmap filename
 *   validatemap
 *   loadmap filename
 *   gameplayer -add playername -remove playername
 *   assigncountries
 *   deploy countryID num
 * 
 * @author Kyle
 *
 */
public class Console {
	private GameEngine d_engine;
	private Controller d_controller;
	private Queue<String> d_unprintedMessages;
	private boolean d_wantsExit = false;
	
	/**
	 * Constructor for the console that connects it to the engine and controller
	 * @param p_engine the game engine to connect to
	 * @param p_controller the controller to connect to
	 */
	public Console(GameEngine p_engine, Controller p_controller) {
		setEngine(p_engine);
		if (d_engine != null) {
			d_engine.setConsole(this);
		}
		setController(p_controller);
		if (d_controller != null) {
			d_controller.setConsole(this);
		}
		InputHandler l_inputHandler = new InputHandler(this);
		l_inputHandler.start();
		d_unprintedMessages = new LinkedList<String>();
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
	 * Adds a message to the print messages queue.
	 * @param p_message the message to add (if not blank).
	 * @return whether the message was successfully added.
	 */
	public boolean addMessage(String p_message) {
		// TODO: cannot get message queue (d_unprintedMessages) to properly work, so this immediately prints messages for time being.
		System.out.println(p_message);
		return true;
	}
	
	/**
	*Controller to execute "editcontinent" command.
	*@param p_add_remove_cont This parameter includes continent which is to be added or removed.
	*/
	public void execEditcontinent(String p_add_remove_cont) {
		d_controller.editcontinent(p_add_remove_cont);
	}

	/**
	*Controller to execute "editcountry" command.
	*@param p_add_remove_coun This parameter includes country which is to be added or removed.
	*/
	public void execEditcountry(String p_add_remove_coun) {
		d_controller.editcountry(p_add_remove_coun);
	}

	/**
	*Controller to execute "editneighbor" command.
	*@param p_add_remove_nei This parameter includes neighbor country which is to be placed next to other country.
	*/
	public void execEditneighbor(String p_add_remove_nei) {
		d_controller.editneighbor(p_add_remove_nei);
	}

	/**
	 *Controller to execute "showmap" command.
	 */
	public void execShowMap() {
		addMessage("Printing map to console:\n\n" + d_controller.showMap());
	}

	/**
	 * Controller to execute "savemap" command.
	 * @param p_fileName File name to which a map is to be saved.
	 */
	public void execSaveMap(String p_fileName) {
		boolean l_wasMapSaved = d_controller.saveMap(p_fileName);
		if (l_wasMapSaved) {
			addMessage("Map successfully saved as: " + p_fileName);
		}
		else {
			addMessage("Map could not be saved. Please check that the map is valid.");
		}
	}

	/**
	 *Controller to execute "editmap" command.
	 *@param p_file File name from which the map is to be edited.
	 */
	public void execEditMap(String p_file) {
		d_controller.editMap(p_file);
	}

	/**
	 * Controller to execute "validate" command.
	 */
	public void execValidateMap() {
		if (d_controller.validateMap()) {
			addMessage("Map is valid.");
		}
		else {
			addMessage("Map is invalid.");
		}
	}

	/**
	 * Controller to execute "loadmap" command.
	 * @param p_fileName File name from which the map is to be loaded.
	 */
	public void execLoadMap(String p_fileName) {
		boolean l_wasMapLoaded = d_controller.loadMap(p_fileName);
		if (l_wasMapLoaded) {
			addMessage("Map " + p_fileName + " loaded successfully.");
		}
		else {
			addMessage("Map " + p_fileName + " could not be loaded. Please check that the map exists and is valid.");
		}
	}
	
	/**
	 * Adds a a player to the game.
	 * @param p_name The new player's name.
	 */
	public void execAddPlayer(String p_name) {
		if (d_controller.addPlayer(p_name)) {
			addMessage("Player added. Total players: " + d_engine.getNumPlayers());
		}
		else {
			addMessage("Player " + p_name + " could not be added.");
		}
	}
	
	/**
	 * Removes a player from the game.
	 * @param p_name The name of the player to remove.
	 */
	public void execRemovePlayer(String p_name) {
		if (d_controller.removePlayer(p_name)) {
			addMessage("Player " + p_name + " removed. Total players: " + d_engine.getNumPlayers());
		}
		else {
			addMessage("No players were removed. Check that you entered the name of a player.");
		}
	}
	
	/**
	 * Adds a continent to the map.
	 * @param p_cID The desired ID for the new continent. 
	 * @param p_cValue The number of bonus armies controlling this continent awards.
	 */
	public void execAddContinent(int p_cID, int p_cValue) {
		if (d_controller.addContinent(p_cID, p_cValue)) {
			addMessage("New continent created. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			addMessage("The continent could not be created.");
		}
	}
	
	/**
	 * Removes a continent from the map.
	 * @param p_cID The ID of the continent to remove.
	 */
	public void execRemoveContinent(int p_cID) {
		if (d_controller.removeContinent(p_cID)) {
			addMessage("Continent removed. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			addMessage("No continents were removed. Please ensure a valid ID was entered.");
		}
	}
	
	/**
	 * Adds a territory to the map.
	 * @param p_tID The desired ID for the new territory.
	 * @param p_cID The ID of the continent the territory will belong to.
	 */
	public void execAddTerritory(int p_tID, int p_cID) {
		if (d_controller.addTerritory(p_tID, p_cID)) {
			addMessage("New territory created. The number of territories is now " + d_engine.getMap().getNumTerritories());
		}
		else {
			addMessage("Territory was not added. Please check your parameters.");
		}
	}
	
	/**
	 * Removes a territory from the map.
	 * @param p_tID The ID of the territory to remove.
	 */
	public void execRemoveTerritory(int p_tID) {
		if (d_controller.removeTerritory(p_tID)) {
			addMessage("Territory removed. The number of territories is now " + d_engine.getMap().getNumTerritories());
		}
		else {
			addMessage("Territory was not added. Please check that there is a territory with the input ID.");
		}
	}
	
	/**
	 * Creates a link between two territories.
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void execAddNeighbour(int p_firstID, int p_secondID) {
		if (d_controller.addNeighbours(p_firstID, p_secondID)) {
			addMessage("Neighbours successfully added.");
		}
		else {
			addMessage("Invalid IDs or the territories were already neighbours.");
		}
	}
	
	/**
	 * Removes a link between two territories.
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void execRemoveNeighbour(int p_firstID, int p_secondID) {
		if (d_controller.removeNeighbours(p_firstID, p_secondID)) {
			addMessage("Territories are no longer neighbours.");
		}
		else {
			addMessage("Invalid IDs or the territories were not neighbours.");
		}
	}

	/**
	 *Controller to execute "assigncountries" or "assignterritories" command.
	 */
	public void execAssignTerritories() {
		if (d_controller.assignTerritories()) {
			addMessage("Territories have been assigned and the game has started. Good luck!");
		}
		else {
			addMessage("Unable to assign territories and start the game. Please ensure the map is valid and there are at least two players.");
		}
	}

	/**
	*Controller to execute "deploy" command.
	*@param p_depcount Includes country for deploy command.
	*/
	public void execDeploy(String p_depcount) {
		d_controller.deploy(p_depcount);
	}

	/**
	 * Has the controller execute the "quit" command.
	 */
	public void execQuit() {
		d_controller.quit();
	}
	
	/**
	 * Starts the process for terminating the console.
	 */
	public void finishAndQuit() {
		d_wantsExit = true;
	}
}
