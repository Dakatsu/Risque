package main.game;

/**
 * An order that prevents attacks between two players.
 * @author Kyle
 */
public class NegotiateOrder extends Order {
	
	/**
	 * The player whom we cannot attack for one turn.
	 */
	private Player d_tempAlly;
	
	/**
	 * Creates the negotiation order with the temporary ally.
	 * @param p_newAlly
	 */
	public NegotiateOrder(Player p_tempAlly) {
		this.d_tempAlly = p_tempAlly;
	}

	/**
	 * Executes the negotiation order.
	 * @return True if players exist.
	 */
	@Override
	public boolean execute() {
		// Add us as allies if we exist and are different from each other.
		if (getIssuer() != null && d_tempAlly != null && getIssuer() != d_tempAlly) {
			getIssuer().addAlly(d_tempAlly);
			d_tempAlly.addAlly(getIssuer());
			return true;
		}
		// If we failed, return the card.
		getIssuer().addCard("diplomacy");
		return false;
	}
}
