package main.game;

/**
 * An order that moves armies from one territory to another, handling an attack if need be.
 * @author Kyle
 *
 */
public class AdvanceOrder implements Order {

	/**
	 * The number of armies to move from one territory to another.
	 */
	private int d_numArmiesAdvancing;
	
	/**
	 * The territory the armies will come from.
	 */
	private Territory d_originTerritory;
	
	/**
	 * The territory armies will go to or invade.
	 */
	private Territory d_destinationTerritory;
	
	AdvanceOrder(int p_numArmiesAdvancing, Territory p_originTerritory, Territory p_destinationTerritory) {
		d_numArmiesAdvancing = p_numArmiesAdvancing;
		d_originTerritory = p_originTerritory;
		d_destinationTerritory = p_destinationTerritory;
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}
}
