package main.game;

/**
 * Order that implements the blockade card used on a territory.
 * @author Kyle
 *
 */
public class BlockadeOrder extends Order {
	/**
	 * The territory to "blockade".
	 */
	private Territory d_territory;
	
	/**
	 * Init the blockade order with a target.
	 * @param p_territory The territory to blockade.
	 */
	public BlockadeOrder(Territory p_territory) {
		this.d_territory = p_territory;
	}

	@Override
	public boolean execute() {
		if (d_territory != null) {
			// Triple the number of armies.
			d_territory.setNumArmies(d_territory.getNumArmies() * 3);
			// Remove it from anyone's territory list.
			for (Player l_player : d_engine.getPlayers()) {
				l_player.removeOwnedTerritory(d_territory);
			}
			d_engine.broadcastMessage(d_territory.getDisplayName() + " was blockaded; it is now a neutral territory.");
			return true;
		}
		// If we failed, return the card.
		getIssuer().addCard("blockade");
		return false;
	}
}
