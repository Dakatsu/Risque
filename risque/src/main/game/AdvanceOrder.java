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
	private int d_fromTerritoryID;
	
	/**
	 * The territory armies will go to or invade.
	 */
	private int d_toTerritoryID;
	
	AdvanceOrder(int p_numArmiesAdvancing, int p_fromTerritoryID, int p_toTerritoryID) {
		d_numArmiesAdvancing = p_numArmiesAdvancing;
		d_fromTerritoryID = p_fromTerritoryID;
		d_toTerritoryID = p_toTerritoryID;
	}
	
	@Override
	public boolean execute() {
		Map l_map = getEngine().getMap();
		// Check that we actually have a valid path.
		if (l_map != null && l_map.doesBorderExist(d_fromTerritoryID, d_toTerritoryID)) {
			// TODO: The rest of the logic!
			return true;
		}
		return false;
	}
}
