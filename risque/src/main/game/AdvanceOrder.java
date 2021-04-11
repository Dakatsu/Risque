package main.game;

import java.util.Random;

/**
 * An order that moves armies from one territory to another, handling an attack if need be.
 * @author Kyle
 */
public class AdvanceOrder extends Order {

	/**
	 * The number of armies to move from one territory to another.
	 */
	private int d_numArmiesAdvancing;
	
	/**
	 * The territory the armies will come from.
	 */
	private Territory d_fromTerritory;
	
	/**
	 * The territory armies will go to or invade.
	 */
	private Territory d_toTerritory;
	
	/**
	 * Init the advance order.
	 * @param p_fromTerritory The origin territory for the armies.
	 * @param p_toTerritory The destination territory. May or may not be owned by the same player.
	 * @param p_numArmiesAdvancing The maximum armies to advance between the two territories.
	 */
	public AdvanceOrder(Territory p_fromTerritory, Territory p_toTerritory, int p_numArmiesAdvancing) {
		d_numArmiesAdvancing = p_numArmiesAdvancing;
		d_fromTerritory = p_fromTerritory;
		d_toTerritory = p_toTerritory;
	}
	
	@Override
	public boolean execute() {
		/*
		 *  We can execute this order if there is at least one army on the from territory.
		 *  Note that the player may have lost this territory before this order could be executed.
		 *  If so, do not do anything.
		 */
		if (d_fromTerritory.getNumArmies() > 0 && d_engine.getTerritoryOwner(d_fromTerritory) == getIssuer()) {
			Player l_originOwner = null;
			Player l_destinationOwner = null;
			for (int l_idx = 0; l_idx < getEngine().getNumPlayers(); l_idx++) {
				Player l_player = getEngine().getPlayerByID(l_idx);
				if (l_player.ownsTerritory(d_fromTerritory)) {
					l_originOwner = l_player;
				}
				if (l_player.ownsTerritory(d_toTerritory)) {
					l_destinationOwner = l_player;
				}
				if (l_originOwner != null && l_destinationOwner != null) {
					break;
				}
			}
			int l_numToAdvance = Math.min(d_numArmiesAdvancing, d_fromTerritory.getNumArmies());
			// Case 1: we own both territories. Just transfer armies.
			if (l_originOwner == l_destinationOwner) {
				d_fromTerritory.setNumArmies(d_fromTerritory.getNumArmies() - l_numToAdvance);
				d_toTerritory.setNumArmies(d_toTerritory.getNumArmies() + l_numToAdvance);
				d_engine.broadcastMessage(getIssuer().getName() + " advanced " + l_numToAdvance + " from " + d_fromTerritory.getDisplayName() + " to " + d_toTerritory.getDisplayName() + ".");
			}
			// Case 2: war were declared. Fight!
			else {
				/**
				 * Casualty calculations are done per the project guidelines:
				 * Each attacking unit has a 60% chance of killing a defender.
				 * Each defending unit has a 70% chance of killing an attacker.
				 */
				Random l_rand = new Random();
				// Calculate defender casualties first.
				int l_numDefenders = d_toTerritory.getNumArmies();
				for (int l_idx = 0; l_idx < l_numToAdvance; l_idx++) {
					if (l_rand.nextFloat() >= 0.4f) {
						l_numDefenders--;
						if (l_numDefenders == 0) {
							break;
						}
					}
				}
				// Then calculate number of attacker casualties.
				int l_numAttackers = l_numToAdvance;
				for (int l_idx = 0; l_idx < l_numToAdvance; l_idx++) {
					if (l_rand.nextFloat() >= 0.3f) {
						l_numAttackers--;
						if (l_numAttackers == 0) {
							break;
						}
					}
				}
				// If defenders have all been killed, take the territory.
				if (l_numDefenders == 0) {
					d_engine.broadcastMessage("The territory " + d_toTerritory.getDisplayName() + " (" + d_engine.getTerritoryOwner(d_toTerritory).getName() + ") has been siezed by armies from " + d_fromTerritory.getDisplayName() + " (" + getIssuer().getName() + ").\n"
							+ "  Surviving Attackers: " + l_numAttackers);
					d_fromTerritory.setNumArmies(d_fromTerritory.getNumArmies() - l_numToAdvance);
					d_toTerritory.setNumArmies(l_numAttackers);
					d_engine.changeTerritoryOwner(d_toTerritory, getIssuer());
				}
				else {
					d_engine.broadcastMessage("The attack on " + d_toTerritory.getDisplayName() + " (" + d_engine.getTerritoryOwner(d_toTerritory).getName() + ") by " + d_fromTerritory.getDisplayName() + " (" + getIssuer().getName() + ") did not succeed.\n"
							+ "  Surviving Attackers: " + l_numAttackers + "/" + l_numToAdvance + "\n"
							+ "  Surviving Defenders: " + l_numDefenders + "/" + d_toTerritory.getNumArmies());
					// The from territory will keep its initial army count, minus the number sent away, plus the attackers returning home.
					d_fromTerritory.setNumArmies(d_fromTerritory.getNumArmies() - l_numToAdvance + l_numAttackers);
					// All armies in this territory were defending, so the number of defenders after combat is the number left in the territory.
					d_toTerritory.setNumArmies(l_numDefenders);
				}
			}
			return true;
		}
		return false;
	}
}
