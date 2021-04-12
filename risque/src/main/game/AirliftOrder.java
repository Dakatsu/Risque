package main.game;

public class AirliftOrder extends Order {

	/**
	 * The source territory of the armies to airlift.
	 */
	private Territory d_source;
	
	/**
	 * The destination of the armies to airlift.
	 */
	private Territory d_destination;
	
	/**
	 * The number of armies to airlift.
	 */
	private int d_numArmies;
	
	/**
	 * Creates the airlift order.
	 * @param p_source The source territory.
	 * @param p_destination The destination territory.
	 * @param p_numArmies The number of armies to transfer.
	 */
	public AirliftOrder(Territory p_source, Territory p_destination, int p_numArmies) {
		d_source = p_source;
		d_destination = p_destination;
		d_numArmies = p_numArmies;
	}
	
	/**
	 * Execute the airlift order.
	 * @return Whether the airlift occurs.
	 */
	@Override
	public boolean execute() {
		if (d_source == null || d_destination == null) {
			// If this somehow happened, I guess return the airlift card?
			getEngine().broadcastMessage(getIssuer().getName() + " managed to airlift to/from a null territory.");
			getIssuer().addCard("airlift");
			return false;
		}
		if (getIssuer() == getEngine().getTerritoryOwner(d_source) && getIssuer() == getEngine().getTerritoryOwner(d_destination)) {
			int l_numArmiesToAirlift = Math.min(d_numArmies, d_source.getNumArmies());
			// Do the airlift if we control both territories and have armies to transfer.
			if (l_numArmiesToAirlift > 0) {
				d_source.setNumArmies(d_source.getNumArmies() - l_numArmiesToAirlift);
				d_destination.setNumArmies(d_source.getNumArmies() + l_numArmiesToAirlift);
				getEngine().broadcastMessage(getIssuer().getName() + " airlifted " + l_numArmiesToAirlift + " from " + d_source.getDisplayName() + " to " + d_destination.getDisplayName() + ".");
				return true;
			}
			// Return the card if there are no armies left to airlift.
			getIssuer().addCard("airlift");
			getEngine().broadcastMessage(getIssuer().getName() + "\'s territory " + d_source.getDisplayName() + " has no armies to airlift to " + d_destination.getDisplayName() + ".");
			return false;
		}
		// If we don't control both territories, return the airlift card.
		getIssuer().addCard("airlift");
		getEngine().broadcastMessage(getIssuer().getName() + "\'s airlift from " + d_source.getDisplayName() + " to " + d_destination.getDisplayName() + " cancelled since they no longer control both territories.");
		return false;
	}
}
