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
	*Controller to execute "validate" command.
	*/
	public void execValidatemap() {
		d_controller.validatemap();
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
	*Controller to execute "gameplayer" command.
	*/
	public void execGameplayer() {
		d_controller.gameplayer();
	}

	/**
	*Controller to execute "assigncountries" command.
	*/
	public void execAssigncountries() {
		d_controller.assigncountries();
	}

	/**
	*Controller to execute "deploy" command.
	*/
	public void execDeploy() {
		d_controller.deploy();
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
