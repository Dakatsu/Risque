package main.game;

/**
 * An abstract class for orders issued by players.
 * @author Kyle
 */
public abstract class Order extends GameEntity {
	/**
	 * Executes the given order.
	 * @return Whether the order was executed successfully.
	 */
	abstract public boolean execute();
}
