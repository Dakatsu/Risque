package main.game;

import java.io.File;
import java.io.IOException;

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
	 * Loads a map, replacing the current one (if valid in this game phase).
	 * @param p_mapName The name of the map to be loaded.
	 */
	public void loadMap(String p_mapName) {
		printInvalidCommandMessage("loadMap");
	}
	
	/**
	 * Saves a map to a file name (if valid in this game phase).
	 * @param p_mapName The name for the new map.
	 */
	public void saveMap(String p_mapName) {
		printInvalidCommandMessage("saveMap");
	}

	/**
	 * Implements the showMap command.
	 */
	public void showMap() {
		printInvalidCommandMessage("showMap");
	}
	
	/**
	 * Edits the map (if the phase allows it).
	 * @param p_mapName The name of the map to edit. It is created if it does not exist.
	 */
	public void editMap(String p_mapName) {
		printInvalidCommandMessage("editMap");
	}
	
	/**
	 * Adds a new continent to the map (if valid in this game phase).
	 * @param p_cID The desired ID for this continent.
	 * @param p_cValue The number of bonus armies for this new continent.
	 */
	public void addContinent(int p_cID, int p_cValue) {
		printInvalidCommandMessage("addContinent");
	}
	
	/**
	 * Removes a continent from the map (if valid in this game phase).
	 * @param p_cID The ID of the continent to remove.
	 */
	public void removeContinent(int p_cID) {
		printInvalidCommandMessage("removeContinent");
	}
	
	/**
	 * Adds a territory to the map (if valid in this game phase).
	 * @param p_tID The desired ID for this territory.
	 * @param p_cID The continent for this ID.
	 */
	public void addTerritory(int p_tID, int p_cID) {
		printInvalidCommandMessage("addTerritory");
	}
	
	/**
	 * Removes the territory at the given index (if valid in this game phase).
	 * @param p_tID The ID of the territory to remove.
	 */
	public void removeTerritory(int p_tID) {
		printInvalidCommandMessage("removeTerritory");
	}
	
	/**
	 * Makes two territories neighbours (if the were not already and if valid in this game phase).
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void addNeighbours(int p_firstID, int p_secondID) {
		printInvalidCommandMessage("addNeighbours");
	}
	
	/**
	 * Removes the link between two territories (if valid in this game phase).
	 * @param p_firstID The ID of the first territory.
	 * @param p_secondID The ID of the second territory.
	 */
	public void removeNeighbours(int p_firstID, int p_secondID) {
		printInvalidCommandMessage("removeNeighbours");
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
	 * Implements the creation of a bomb order in this phase.
	 * @param p_targetID The ID of the territory to bomb.
	 */
	public void createBombOrder(int p_targetID) {
		printInvalidCommandMessage("bomb");
	}
	
	/**
	 * Uses the airlift card to transfer armies from one territory to another.
	 * @param p_sourceID The ID of the source territory of the armies.
	 * @param p_destinationID The ID of the destination territory for the armies.
	 * @param p_numArmies The number of armies to transfer.
	 */
	public void createAirliftOrder(int p_sourceID, int p_destinationID, int p_numArmies) {
		printInvalidCommandMessage("airlift");
	}
	
	/**
	 * Use the negotiate card to prevent two players from fighting.
	 * @param p_player The target player.
	 */
	public void createNegotiateOrder(Player p_player) {
		printInvalidCommandMessage("negotiate");
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
	
	/**
	 * Can an attack on a territory proceed?
	 * If not, broadcasts the reason why it cannot.
	 * @param p_territory The territory to attack.
	 * @param p_attacker The player attacking the territory.
	 * @return True if the attack can proceed.
	 */
	public boolean canAttackTerritory(Territory p_territory, Player p_attacker) {
		return true;
	}
}
