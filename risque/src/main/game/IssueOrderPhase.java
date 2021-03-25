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
	 * Auto-generated constructor stub.
	 * @param p_engine The game engine context.
	 */
	IssueOrderPhase(GameEngine p_engine) {
		super(p_engine);
		d_minArmies = 3;
	}
	
	@Override
	public void onPhaseStart(Phase p_prevPhase) {
		// Calculate the number of armies per player.
		for (int l_idx = 0; l_idx < d_engine.getNumPlayers(); l_idx++) {
			Player l_player = d_engine.getPlayerByID(l_idx);
			l_player.setNumUndeployedArmies(Math.max(d_minArmies, l_player.getNumTerritoriesOwned() / 3));
			for (Continent l_continent : l_player.getOwnedContinents()) {
				l_player.addUndeployedArmies(l_continent.getBonusArmies());
			}
			d_engine.broadcastMessage(l_player.getName() + "\'s Armies to Deploy: " + l_player.getNumUndeployedArmies());
		}
		//d_engine.d_nextPlayer = 0;
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
