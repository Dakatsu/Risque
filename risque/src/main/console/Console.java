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
	 *Controller to execute "editmap" command.
	 *@param p_file File name from which the map is to be edited.
	 */
	public void execEditMap(String p_file) {
		d_controller.editMap(p_file);
	}
}
