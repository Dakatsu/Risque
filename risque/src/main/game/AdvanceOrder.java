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
	
	AdvanceOrder(Territory p_fromTerritory, Territory p_toTerritory, int p_numArmiesAdvancing) {
		d_numArmiesAdvancing = p_numArmiesAdvancing;
		d_fromTerritory = p_fromTerritory;
		d_toTerritory = p_toTerritory;
	}
	
	@Override
	public boolean execute() {
		Map l_map = getEngine().getMap();
		// Check that we actually have a valid path.
		if (l_map != null && l_map.doesBorderExist(l_map.getTerritoryID(d_fromTerritory), l_map.getTerritoryID(d_toTerritory))) {
			// TODO: The rest of the logic!
			return true;
		}
		return false;
	}
}
