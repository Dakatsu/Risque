package main.core;
import main.console.Console;
import main.controller.Controller;
import main.game.GameEngine;

/**
 * The entry point for the program that starts the other modules.
 * 
 * @author Kyle
 *
 */
public class Core {

	/**
	 * The main function that will start the key modules for the program.
	 * The program terminates once all three modules have finished.
	 * @param args unused program arguments
	 */
	public static void main(String[] args) {
		System.out.println("...Starting Risque...");
		GameEngine l_gameEngine = new GameEngine();
		l_gameEngine.start();
		Controller l_controller = new Controller(l_gameEngine);
		l_controller.start();
		Console l_console = new Console(l_gameEngine, l_controller);
		l_console.start();
	}
}
