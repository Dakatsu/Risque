package main.game;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The phase for when players are issuing their orders.
 * @author Kyle
 */
public class IssueOrderPhase extends Phase {
	
	/**
	 * The number of armies every player receives no matter their continent bonuses.
	 */
	private int d_minArmies;
	
	/**
	 * The current player that needs to issue commands.
	 */
	private Player d_currentPlayer;
	
	/**
	 * The IDs of players that take turns in this phase.
	 */
	private LinkedList<Player> d_playerRotation;

	/**
	 * Auto-generated constructor stub.
	 * @param p_engine The game engine context.
	 */
	IssueOrderPhase(GameEngine p_engine) {
		super(p_engine);
		d_minArmies = 3;
		d_currentPlayer = null;
		d_playerRotation = new LinkedList<>();
	}
	
	@Override
	public void onPhaseStart(Phase p_prevPhase) {
		calculateAndSetArmies();
		for (Player l_player : d_engine.getPlayers()) {
			if (l_player.getNumUndeployedArmies() > 0) {
				if (d_currentPlayer == null) {
					d_currentPlayer = l_player;
				}
				else {
					d_playerRotation.add(l_player);
				}
			}
		}
		d_engine.broadcastMessage(d_currentPlayer.getName() + " goes first.");
	}
	
	/**
	 * Called when a player's turn has ended.
	 */
	public void onEndTurn() {
		// Calc next player.
		Player l_prevPlayer = d_currentPlayer;
		// Add this player back to the list if they still have armies to deploy.
		if (l_prevPlayer.getNumUndeployedArmies() > 0) {
			d_playerRotation.add(l_prevPlayer);
		}
		d_currentPlayer = d_playerRotation.isEmpty() ? null : d_playerRotation.pop();
		if (d_currentPlayer != null) {
			d_engine.broadcastMessage("It is now " + d_currentPlayer.getName() + "\'s turn.");
		}
		else {
			d_engine.setPhase(new ExecuteOrderPhase(d_engine));
		}
	}
	
	/**
	 * Calculates and sets the number of armies that each player can deploy.
	 */
	private void calculateAndSetArmies() {
		for (int l_idx = 0; l_idx < d_engine.getNumPlayers(); l_idx++) {
			Player l_player = d_engine.getPlayerByID(l_idx);
			int l_numTerritories = l_player.getNumTerritoriesOwned();
			// Only add armies if we are still in the game.
			if (l_numTerritories > 0) {
				l_player.setNumUndeployedArmies(Math.max(d_minArmies, l_numTerritories / 3));
				for (Continent l_continent : l_player.getOwnedContinents()) {
					l_player.addUndeployedArmies(l_continent.getBonusArmies());
				}
				d_engine.broadcastMessage(l_player.getName() + "\'s Armies to Deploy: " + l_player.getNumUndeployedArmies());
			}
		}
	}
	
	/**
	 * Creates an order to deploy armies for the next player.
	 * @param p_tID The ID of the territory to deploy the armies to.
	 * @param p_num The number of armies desired to be deployed.
	 */
	@Override
	public void createDeployOrder(int p_tID, int p_num) {
		// TODO: Next player should be player that actually has armies to deploy.
		if (p_tID > 0 && p_tID <= d_engine.getMap().getNumTerritories()) {
			Territory l_territory = d_engine.getMap().getTerritory(p_tID);
			// Cannot deploy to a territory we do not control.
			if (!d_currentPlayer.ownsTerritory(l_territory)) {
				d_engine.broadcastMessage("Cannot deploy to a territory " + d_currentPlayer.getName() + " does not control.");
			}
			else {
				// Cannot deploy more armies than we have.
				int l_numArmies = d_currentPlayer.removeUndeployedArmies(p_num);
				d_currentPlayer.issueOrder(d_engine.onCreateEntity(new DeployOrder(l_territory, l_numArmies)));
				d_engine.broadcastMessage(d_currentPlayer.getName() + " will deploy " + l_numArmies + " to " + l_territory.getDisplayName() + ".");
				onEndTurn();
			}
		}
		else {
			d_engine.broadcastMessage("Invalid territory. Please try again.");
		}
	}
	
	/**
	 * Creates an order to advance armies for the next player.
	 * @param p_fromID The ID of the territory the armies are moving from.
	 * @param p_toID The ID of the territory the armies will go to.
	 * @param p_num The number of armies desired to be advanced.
	 */
	@Override
	public void createAdvanceOrder(int p_fromID, int p_toID, int p_num) {
		if (!d_engine.getMap().doesBorderExist(p_toID, p_fromID)) {
			d_engine.broadcastMessage("Error: territories do not border each other.");
		}
		else if (p_fromID == p_toID) {
			d_engine.broadcastMessage("Error: the territories cannot be the same.");
		}
		else if (p_fromID < 1 || p_fromID > d_engine.getMap().getNumTerritories()) {
			d_engine.broadcastMessage("Error: invalid origin territory.");
		}
		else if (p_toID < 1 || p_toID > d_engine.getMap().getNumTerritories()) {
			d_engine.broadcastMessage("Error: invalid destination territory.");
		}
		else if (p_num < 1) {
			d_engine.broadcastMessage("Error: must advance at least one army.");
		}
		else {
			Territory l_fromTerritory = d_engine.getMap().getTerritory(p_fromID);
			// Cannot advance from a territory we do not control
			// However, it doesn't matter if we own the destination; it could be lost or gained before this order is executed).
			if (!d_currentPlayer.ownsTerritory(l_fromTerritory)) {
				d_engine.broadcastMessage("Error: cannot advance from a territory " + d_currentPlayer.getName() + " does not control.");
			}
			else {
				Territory l_toTerritory = d_engine.getMap().getTerritory(p_toID);
				// Calc next player.
				// Cannot advance more armies than currently exist in the territory.
				int l_numArmies = Math.min(p_num, l_fromTerritory.getNumArmies());
				d_currentPlayer.issueOrder(d_engine.onCreateEntity(new AdvanceOrder(l_fromTerritory, l_toTerritory, l_numArmies)));
				d_engine.broadcastMessage(d_currentPlayer.getName() + " will advance " + l_numArmies + " from " + l_fromTerritory.getDisplayName() + " to " + l_toTerritory.getDisplayName() + ".");
				onEndTurn();
			}
		}
	}

	/**
	 * Prints a player-friendly view of the map to the screen.
	 */
	@Override
	public void showMap() {
		// Lists each player's name, then a display of their territories and the armies they control.
		// TODO: Show number of fully-controlled continents?
		if (d_engine.getMap() != null) {
			String l_mapView = "Risque Overview:\n";
			for (int l_idx = 0; l_idx < d_engine.getNumPlayers(); l_idx++) {
				Player l_player = d_engine.getPlayerByID(l_idx);
				LinkedList<Territory> l_ownedTerritories = l_player.getOwnedTerritories();
				if (l_ownedTerritories.size() > 0) {
					l_mapView += "  " + l_player.getName() + "\'s Empire and Garrisons:\n";
					for (Territory l_territory : l_ownedTerritories) {
						l_mapView += "    #" + d_engine.getMap().getTerritoryID(l_territory) + ", " + l_territory.getDisplayName() + ": " + l_territory.getNumArmies() + "\n";
					}
				}
			}
			d_engine.broadcastMessage(l_mapView);
		}
	}
}
