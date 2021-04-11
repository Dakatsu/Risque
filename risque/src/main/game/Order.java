package main.game;

/**
 * An abstract class for orders issued by players.
 * @author Kyle
 */
public abstract class Order extends GameEntity {
	
	/**
	 * The player that issues this order.
	 */
	private Player d_issuer;
	
	/**
	 * Gets the player invoking this command.
	 * @return The invoking player.
	 */
	public Player getIssuer() {
		return d_issuer;
	}
	
	/**
	 * Sets the invoking player.
	 * @param l_issuer The new invoker.
	 */
	public void setIssuer(Player l_issuer) {
		d_issuer = l_issuer;
	}
	
	/**
	 * Executes the given order.
	 * @return Whether the order was executed successfully.
	 */
	abstract public boolean execute();
}
