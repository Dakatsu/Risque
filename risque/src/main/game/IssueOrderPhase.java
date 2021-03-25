package main.game;

import java.util.LinkedList;

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
	 * The next player that needs to issue commands.
	 */
	private int d_nextPlayer;
	
	/**
	 * True if we are only allowing deploy orders right now.
	 */
	private boolean d_onlyAllowDeployments;

	/**
	 * Auto-generated constructor stub.
	 * @param p_engine The game engine context.
	 */
	IssueOrderPhase(GameEngine p_engine) {
		super(p_engine);
		d_minArmies = 3;
		d_nextPlayer = 0;
		d_onlyAllowDeployments = true;
	}
	
	@Override
	public void onPhaseStart(Phase p_prevPhase) {
		calculateAndSetArmies();
		d_nextPlayer = 0;
	}
	
	/**
	 * Calculates and sets the number of armies that each player can deploy.
	 */
	private void calculateAndSetArmies() {
		for (int l_idx = 0; l_idx < d_engine.getNumPlayers(); l_idx++) {
			Player l_player = d_engine.getPlayerByID(l_idx);
			l_player.setNumUndeployedArmies(Math.max(d_minArmies, l_player.getNumTerritoriesOwned() / 3));
			for (Continent l_continent : l_player.getOwnedContinents()) {
				l_player.addUndeployedArmies(l_continent.getBonusArmies());
			}
			d_engine.broadcastMessage(l_player.getName() + "\'s Armies to Deploy: " + l_player.getNumUndeployedArmies());
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
					l_mapView += "  " + l_player.getName() + "\'s Empire and Garrison:\n";
					for (Territory l_territory : l_ownedTerritories) {
						l_mapView += "    #" + d_engine.getMap().getTerritoryID(l_territory) + ", " + l_territory.getDisplayName() + ": " + l_territory.getNumArmies() + "\n";
					}
				}
			}
			d_engine.broadcastMessage(l_mapView);
		}
	}
}
