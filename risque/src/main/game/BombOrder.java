package main.game;

/**
 * An order that destroys half of the armies on a specific territory.
 * @author Kyle
 *
 */
public class BombOrder extends Order {
	/**
	 * The territory to bomb.
	 */
	private Territory d_target;
	
	/**
	 * Constructs the bomb order.
	 * @param p_target The territory to bomb.
	 */
	public BombOrder(Territory p_target) {
		d_target = p_target;
	}
	
	/**
	 * Bomb the territory!
	 * @return True if the territory exists (and was therefore bombed).
	 */
	@Override
	public boolean execute() {
		if (d_target != null && d_engine.getPhase().canAttackTerritory(d_target, getIssuer())) {
			int l_prevNumArmies = d_target.getNumArmies();
			d_target.setNumArmies(l_prevNumArmies / 2);
			getEngine().broadcastMessage(getIssuer().getName() + " bombed " + d_target.getDisplayName() + "(" + getEngine().getTerritoryOwner(d_target).getName() + "). The garrison of " + l_prevNumArmies + " was reduced to " + d_target.getNumArmies() + ".");
			return true;
		}
		// If somehow this bombing fails, give the player their card back.
		getEngine().broadcastMessage(getIssuer().getName() + " managed to break the game by trying to bomb a nonexistent territory.");
		getIssuer().addCard("bomb");
		return false;
	}
}
