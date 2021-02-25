package main.controller;

import main.console.Console;
import main.game.GameEngine;

/**
 * Class to handle calling commands on different game elements.
 * 
 * @author Kyle
 *
 */
public class Controller extends Thread {
	private GameEngine d_engine;
	private Console d_console;
	private boolean d_wantsExit = false;
	
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
	public void editcontinent(String p_add_remove_continent) {
	//TODO
	}

	/**
	*Will start the process for executing "editcountry" command.
	*@param p_add_remove_country This parameter includes country which is to be added or removed.
	*/
	public void editcountry(String p_add_remove_country) {
	//TODO
	}

	/**
	*Will start the process for executing "editneighbor" command.
	*@param p_add_remove_neighbor This parameter includes neighbor which is to be added or removed.
	*/
	public void editneighbor(String p_add_remove_neighbor) {
	//TODO
	}

	/**
	*Will start the process for executing "showmap" command.
	*/
	public void showmap() {
	//TODO
	}

	/**
	*Will start the process for executing "savemap" command.
	*@param p_filename File name to which a map is to be saved.
	*/
	public void savemap(String p_filename) {
	//TODO
	}

	/**
	*Will start the process for executing "editmap" command.
	*@param p_filename File name from which a map is to be edited.
	*/
	public void editmap(String p_filename) {
	//TODO
	}
	
	/**
	*Will start the process for executing "validatemap" command.
	*/
	public void validatemap() {
	//TODO
	}

	/**
	*Will start the process for executing "loadmap" command.
	*@param p_filename File name from which a map is to be loaded.
	*/
	public void loadmap(String p_filename) {
	//TODO
	}

	/**
	*Will start the process for executing "gameplayer" command.
	*/
	public void gameplayer() {
	//TODO
	}

	/**
	*Will start the process for executing "assigncountries" command.
	*/
	public void assigncountries() {
	//TODO
	}

	/**
	*Will start the process for executing "deploy" command.
	*/
	public void deploy() {
	//TODO
	}

	/**
	 * Starts the process for terminating the whole program.
	 */
	public void quit() {
		finishAndQuit();
		d_engine.finishAndQuit();
		d_console.addMessage("...Quitting Risque...");
		d_console.finishAndQuit();
	}
	
	/**
	 * Starts the process for terminating the controller.
	 */
	public void finishAndQuit() {
		d_wantsExit = true;
	}
	
	/**
	 * Entry point for the Controller. The object is deleted once this method finishes.
	 */
	public void run() {
		// Temporary code until Controller's core behaviour is implemented.
		// Just tell the console we're running every five seconds, unless we want to quit.
		while (!d_wantsExit) {
			if (d_console != null) {
				//d_console.addMessage("Controller is running.");
			}
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
