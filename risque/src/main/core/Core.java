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
	 * The console will create an InputHandler as a separate thread.
	 * The program terminates when that thread finishes, which happens after the "quit" command
	 * is typed into the console.
	 * @param args Unused program arguments.
	 */
	public static void main(String[] args) {
		System.out.println("...Starting Risque...");
		GameEngine l_gameEngine = new GameEngine();
		Controller l_controller = new Controller(l_gameEngine);
		Console l_console = new Console(l_gameEngine, l_controller);
	}
}
