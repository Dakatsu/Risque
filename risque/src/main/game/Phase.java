package main.game;

/**
 *	Phases represent the current state of the game engine, such as map editing and gameplay phases.
 */
public abstract class Phase {
	/**
	 * The game engine this phase provides context for.
	 */
	GameEngine d_engine;

	/**
	 * Constructs the phase with a link to its game engine.
	 * @param p_engine The game engine this phase provides context for.
	 */
	Phase(GameEngine p_engine) {
		d_engine = p_engine;
	}

	/**
	 * Implements the showMap command.
	 */
	public void showMap() {
		printInvalidCommandMessage("showMap");
	}
	
	/**
	 *  Generic message when a command is invalid for a given state.
	 */
	public void printInvalidCommandMessage(String p_commandName) {
		d_engine.getConsole().addMessage("Command " + p_commandName + " is not valid in state " + this.getClass().getSimpleName() + ".");
	}
}
