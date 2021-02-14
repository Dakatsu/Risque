package game;

import console.Console;
import controller.Controller;

/**
 * From grading sheet:
 * "Implementation of a GameEngineclass implementing and controlling the game phasesaccording to the Warzone rules."
 * 
 * @author Kyle
 *
 */
public class GameEngine extends Thread {
	private Controller d_controller;
	private Console d_console;
	private boolean d_wantsExit = false;
	
	/**
	 * Default constructor for the GameEngine.
	 */
	public GameEngine() {
		// Do something?
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
	 * Starts the process for terminating the engine.
	 */
	public void finishAndQuit() {
		d_wantsExit = true;
	}
	
	/**
	 * Entry point for the GameEngine. The object is deleted once this method finishes.
	 */
	public void run() {
		// Temporary loop until game engine's core behaviour is implemented.
		// Just tell the console we're running every five seconds, unless we want to quit.
		while (!d_wantsExit) {
			if (d_console != null) {
				d_console.addMessage("GameEngine is running.");
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
