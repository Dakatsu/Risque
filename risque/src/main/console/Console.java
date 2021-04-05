package main.console;

import main.controller.Controller;
import main.game.GameEngine;
import main.game.GameObserver;

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
public class Console implements GameObserver {
	/**
	 * The game engine we are observing.
	 */
	private GameEngine d_engine;
	
	/**
	 * The controller we send commands to.
	 */
	private Controller d_controller;
	
	/**
	 * Constructor for the console that connects it to the engine and controller
	 * @param p_engine the game engine to connect to
	 * @param p_controller the controller to connect to
	 */
	public Console(GameEngine p_engine, Controller p_controller) {
		setEngine(p_engine);
		if (d_engine != null) {
			d_engine.addObserver(this);
		}
		setController(p_controller);
		if (d_controller != null) {
			d_controller.setConsole(this);
		}
		InputHandler l_inputHandler = new InputHandler(this);
		l_inputHandler.start();
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
	 * Prints a message to the console.
	 * @param p_message the message to print.
	 */
	@Override
	public void onAddMessage(String p_message) {
		System.out.println(p_message);
	}
	
	/**
	 * Nothing happens 
	 */
	@Override
	public void onQuit() {
		// Do nothing by default.
	}
	
	/**
	*Controller to execute "editcontinent" command.
	*@param p_add_remove_cont This parameter includes continent which is to be added or removed.
	*/
	public void execEditcontinent(String p_add_remove_cont) {
		d_controller.editContinent(p_add_remove_cont);
	}

	/**
	*Controller to execute "editcountry" command.
	*@param p_add_remove_coun This parameter includes country which is to be added or removed.
	*/
	public void execEditcountry(String p_add_remove_coun) {
		d_controller.editCountry(p_add_remove_coun);
	}

	/**
	*Controller to execute "editneighbor" command.
	*@param p_add_remove_nei This parameter includes neighbor country which is to be placed next to other country.
	*/
	public void execEditneighbor(String p_add_remove_nei) {
		d_controller.editNeighbour(p_add_remove_nei);
	}

	/**
	 *Controller to execute "editmap" command.
	 *@param p_file File name from which the map is to be edited.
	 */
	public void execEditMap(String p_file) {
		d_controller.editMap(p_file);
	}
	
	/**
	 * Adds a continent to the map.
	 * @param p_cID The desired ID for the new continent. 
	 * @param p_cValue The number of bonus armies controlling this continent awards.
	 */
	public void execAddContinent(int p_cID, int p_cValue) {
		if (d_controller.addContinent(p_cID, p_cValue)) {
			onAddMessage("New continent created. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			onAddMessage("The continent could not be created.");
		}
	}
	
	/**
	 * Removes a continent from the map.
	 * @param p_cID The ID of the continent to remove.
	 */
	public void execRemoveContinent(int p_cID) {
		if (d_controller.removeContinent(p_cID)) {
			onAddMessage("Continent removed. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			onAddMessage("No continents were removed. Please ensure a valid ID was entered.");
		}
	}
	
	/**
	 * Adds a territory to the map.
	 * @param p_tID The desired ID for the new territory.
	 * @param p_cID The ID of the continent the territory will belong to.
	 */
	public void execAddTerritory(int p_tID, int p_cID) {
		if (d_controller.addTerritory(p_tID, p_cID)) {
			onAddMessage("New territory created. The number of territories is now " + d_engine.getMap().getNumTerritories());
		}
		else {
			onAddMessage("Territory was not added. Please check your parameters.");
		}
	}
	
	/**
	 * Removes a territory from the map.
	 * @param p_tID The ID of the territory to remove.
	 */
	public void execRemoveTerritory(int p_tID) {
		if (d_controller.removeTerritory(p_tID)) {
			onAddMessage("Territory removed. The number of territories is now " + d_engine.getMap().getNumTerritories());
		}
		else {
			onAddMessage("Territory was not added. Please check that there is a territory with the input ID.");
		}
	}
	
	/**
	 * Creates a link between two territories.
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void execAddNeighbour(int p_firstID, int p_secondID) {
		if (d_controller.addNeighbours(p_firstID, p_secondID)) {
			onAddMessage("Neighbours successfully added.");
		}
		else {
			onAddMessage("Invalid IDs or the territories were already neighbours.");
		}
	}
	
	/**
	 * Removes a link between two territories.
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void execRemoveNeighbour(int p_firstID, int p_secondID) {
		if (d_controller.removeNeighbours(p_firstID, p_secondID)) {
			onAddMessage("Territories are no longer neighbours.");
		}
		else {
			onAddMessage("Invalid IDs or the territories were not neighbours.");
		}
	}
}
