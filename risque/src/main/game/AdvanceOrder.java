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
	 * The chance that a defender will kill an attacker.
	 */
	private float d_defenderKillChance;
	
	/**
	 * The chance that an attacker will kill a defender.
	 */
	private float d_attackerKillChance;
	
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
		d_defenderKillChance = 0.7f;
		d_attackerKillChance = 0.6f;
	}
	
	/**
	 * Calculates the number of casualties during combat.
	 * @param p_numRounds The number of times we "roll the dice" on a kill.
	 * @param p_killChance The chance of a kill for each round, as a percent.
	 * @param p_maxCasualties The maximum number of kills we can achieve.
	 * @return The number of kills.
	 */
	public int calcNumCasualties(int p_numRounds, float p_killChance, int p_maxCasualties) {
		Random l_rand = new Random();
		int l_kills = 0;
		for (int l_idx = 0; l_idx < p_numRounds; l_idx++) {
			if (p_killChance >= l_rand.nextFloat()) {
				l_kills++;
				if (l_kills == p_maxCasualties) {
					return p_maxCasualties;
				}
			}
		}
		return l_kills;
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
				int l_numSurvivingDefenders = d_toTerritory.getNumArmies() - calcNumCasualties(l_numToAdvance, d_attackerKillChance, d_toTerritory.getNumArmies());
				int l_numSurvivingAttackers = l_numToAdvance - calcNumCasualties(d_toTerritory.getNumArmies(), d_defenderKillChance, l_numToAdvance);
				// If defenders have all been killed, take the territory.
				if (l_numSurvivingDefenders <= 0) {
					d_engine.broadcastMessage("The territory " + d_toTerritory.getDisplayName() + " (" + d_engine.getTerritoryOwner(d_toTerritory).getName() + ") has been siezed by armies from " + d_fromTerritory.getDisplayName() + " (" + getIssuer().getName() + ").\n"
							+ "  Surviving Attackers: " + l_numSurvivingAttackers);
					d_fromTerritory.setNumArmies(d_fromTerritory.getNumArmies() - l_numToAdvance);
					d_toTerritory.setNumArmies(l_numSurvivingAttackers);
					d_engine.changeTerritoryOwner(d_toTerritory, getIssuer());
				}
				else {
					d_engine.broadcastMessage("The attack on " + d_toTerritory.getDisplayName() + " (" + d_engine.getTerritoryOwner(d_toTerritory).getName() + ") by " + d_fromTerritory.getDisplayName() + " (" + getIssuer().getName() + ") did not succeed.\n"
							+ "  Surviving Attackers: " + l_numSurvivingAttackers + "/" + l_numToAdvance + "\n"
							+ "  Surviving Defenders: " + l_numSurvivingDefenders + "/" + d_toTerritory.getNumArmies());
					// The from territory will keep its initial army count, minus the number sent away, plus the attackers returning home.
					d_fromTerritory.setNumArmies(d_fromTerritory.getNumArmies() - l_numToAdvance + l_numSurvivingAttackers);
					// All armies in this territory were defending, so the number of defenders after combat is the number left in the territory.
					d_toTerritory.setNumArmies(l_numSurvivingDefenders);
				}
			}
			return true;
		}
		return false;
	}
}
