package main.game;

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
		// We can execute this order if there is at least one army on the from territory.
		if (d_fromTerritory.getNumArmies() > 0) {
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
			// Case 1: we own both territories. Just transfer armies.
			if (l_originOwner == l_destinationOwner) {
				int l_armiesToTransfer = Math.min(d_numArmiesAdvancing, d_fromTerritory.getNumArmies());
				d_fromTerritory.setNumArmies(d_fromTerritory.getNumArmies() - l_armiesToTransfer);
				d_toTerritory.setNumArmies(d_toTerritory.getNumArmies() + l_armiesToTransfer);
			}
			// Case 2: war were declared. Fight!
			else {
				// TODO: Do proper logic from the project requirements.
			}
			return true;
		}
		return false;
	}
}
