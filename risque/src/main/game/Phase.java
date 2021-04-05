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
	 * Implements the creation of a deploy order in this Phase.
	 * @param p_tID The ID of the territory to deploy the armies to.
	 * @param p_num The number of armies desired to be deployed.
	 */
	public void createDeployOrder(int p_tID, int p_num) {
		printInvalidCommandMessage("deploy");
	}
	
	/**
	 * Implements the creation of an advance order in this Phase.
	 * @param p_fromID The ID of the territory the armies are moving from.
	 * @param p_toID The ID of the territory the armies will go to.
	 * @param p_num The number of armies desired to be advanced.
	 */
	public void createAdvanceOrder(int p_fromID, int p_toID, int p_num) {
		printInvalidCommandMessage("advance");
	}
	
	/**
	 * Implements the finish(orders) command.
	 */
	public void finishOrders() {
		printInvalidCommandMessage("finish");
	}
	
	/**
	 *  Generic message when a command is invalid for a given state.
	 *  @param p_commandName The name of the command that was invalid.
	 */
	public void printInvalidCommandMessage(String p_commandName) {
		d_engine.broadcastMessage("Command " + p_commandName + " is not valid in state " + this.getClass().getSimpleName() + ".");
	}
}
