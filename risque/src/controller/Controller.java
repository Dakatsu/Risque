package controller;

import console.Console;
import game.GameEngine;

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
	
	public void editcontinent() {
	//TODO
	}

	public void editcountry() {
	//TODO
	}

	public void editneighbor() {
	//TODO
	}

	public void showmap() {
	//TODO
	}

	public void savemap() {
	//TODO
	}

	public void editmap() {
	//TODO
	}

	public void validatemap() {
	//TODO
	}

	public void loadmap() {
	//TODO
	}

	public void gameplayer() {
	//TODO
	}

	public void assigncountries() {
	//TODO
	}

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
				d_console.addMessage("Controller is running.");
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
