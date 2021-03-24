package main.game;

/**
 * An interface for orders issued by players.
 * @author Kyle
 */
public interface Order {
	/**
	 * Executes the given order.
	 * @return Whether the order was executed successfully.
	 */
	public boolean execute();
}
