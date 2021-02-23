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
public class Console extends Thread {
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
	*/
	public void execEditcontinent() {
		d_controller.editcontinent();
	}

	/**
	*Controller to execute "editcountry" command.
	*/
	public void execEditcountry() {
		d_controller.editcountry();
	}

	/**
	*Controller to execute "editneighbor" command.
	*/
	public void execEditneighbor() {
		d_controller.editneighbor();
	}

	/**
	*Controller to execute "showmap" command.
	*/
	public void execShowmap() {
		d_controller.showmap();
	}

	/**
	*Controller to execute "savemap" command.
	*/
	public void execSavemap() {
		d_controller.savemap();
	}

	/**
	*Controller to execute "editmap" command.
	*/
	public void execEditmap() {
		d_controller.editmap();
	}

	/**
	*Controller to execute "validate" command.
	*/
	public void execValidatemap() {
		d_controller.validatemap();
	}

	/**
	*Controller to execute "loadmap" command.
	*/
	public void execLoadmap() {
		d_controller.loadmap();
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
	
	/**
	 * Entry point for the Console. The object is deleted once this method finishes.
	 */
	public void run() {
		// Create and start our separate input handling thread.
		InputHandler l_inputHandler = new InputHandler(this);
		l_inputHandler.start();
		// Temporary default behaviour: print that we're running every five seconds, unless we want to quit.
		while (!d_wantsExit) {
			addMessage("Console is running!");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
