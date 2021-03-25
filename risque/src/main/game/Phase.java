package main.game;

/**
 *	Phases represent the current state of the game engine, such as map editing and gameplay phases.
 *  The command in this abstract class will print that they are not implemented for that phase.
 *  This is so subclasses will not have to manually print that for every command.
 *  Subclasses can then simply override the functions they actually need.
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
	 * Does actions at the start of a phase.
	 * @param p_prevPhase The previous phase.
	 */
	public void onPhaseStart(Phase p_prevPhase) {
		// Do nothing by default.
	}
	
	/**
	 * Does actions upon ending a phase.
	 * @param p_nextPhase The next phase.
	 */
	public void onPhaseEnd(Phase p_nextPhase) {
		// Do nothing by default.
	}

	/**
	 * Implements the showMap command.
	 */
	public void showMap() {
		printInvalidCommandMessage("showMap");
	}
	
	/**
	 * Implements the assignTerritories command.
	 */
	public void assignTerritories() {
		printInvalidCommandMessage("assignTerritories");
	}
	
	/**
	 * Implements the finish(orders) command.
	 */
	public void finishOrders() {
		printInvalidCommandMessage("finishOrders");
	}
	
	/**
	 *  Generic message when a command is invalid for a given state.
	 *  @param p_commandName The name of the command that was invalid.
	 */
	public void printInvalidCommandMessage(String p_commandName) {
		d_engine.getConsole().addMessage("Command " + p_commandName + " is not valid in state " + this.getClass().getSimpleName() + ".");
	}
}
