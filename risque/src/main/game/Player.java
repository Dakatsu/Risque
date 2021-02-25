package main.game;

import java.util.LinkedList;

/**
 * There must be a Player class that must hold (among other things):
 * a list of Country objects that are owned by the Player 
 * a list of Order objects that have been created by the Player during the current turn, and 
 *     has a method "issue_order()" (no parameters, no return value) whose function is to add 
 *     an order to the list of orders held by the player when the game engine calls it during 
 *     the issue orders phase. The player class must also have a "next_order()" (no parameters) 
 *     method that is called by the GameEngine during the execute orders phase and returns the 
 *     first order in the player’s list of orders, then removes it from the list.
 * 
 * @author Kyle
 *
 */
public class Player {	
	/**
	 * The player's name.
	 */
	private String d_name;
	
	/**
	 * The territories this player owns.
	 */
	private LinkedList<Territory> d_ownedTerritories;
	
	/**
	 * All the player's issued but unexecuted orders.
	 * TODO: Change to queue?
	 */
	private LinkedList<Order> d_orders;
	
	/**
	 * Number of armies left to deploy for this player.
	 * TODO: Add getter/setter and make private!
	 */
	public int d_numArmiesLeftToDeploy;
	
	/**
	 * Default constructor for player.
	 * @param p_name The player's starting name.
	 */
	public Player(String p_name) {
		setName(p_name);
		d_ownedTerritories = new LinkedList<>();
		d_orders = new LinkedList<>();
		d_numArmiesLeftToDeploy = 0;
	}
	
	/**
	 * Gets the player's name.
	 * @return The player's name.
	 */
	public String getName() {
		return d_name;
	}
	
	/**
	 * Changes the player's name to a new value if not blank.
	 * @param p_name The new player name.
	 * @return True if the name was changed, false if the new name was blank.
	 */
	public boolean setName(String p_name) {
		if (!p_name.isBlank()) {
			d_name = p_name.trim();
			return true;
		}
		d_name = "Player";
		return false;
	}
	
	/**
	 * Gets the number of territories currently owned by this player.
	 * @return The number of this player's territories.
	 */
	public int getNumTerritoriesOwned() {
		return d_ownedTerritories.size();
	}
	
	/**
	 * Adds the territory to the list of this player's owned territories.
	 * @param p_territory The territory to add.
	 * @return True if the player did not own this territory before, otherwise false.
	 */
	public boolean addOwnedTerritory(Territory p_territory) {
		if (p_territory != null && !d_ownedTerritories.contains(p_territory)) {
			return d_ownedTerritories.add(p_territory);
		}
		return false;
	}
	
	/**
	 * Removes the territory to the list of this player's owned territories.
	 * @param p_territory The territory to remove.
	 * @return True if the player owned this territory and it was removed, otherwise false.
	 */
	public boolean removeOwnedTerritory(Territory p_territory) {
		if (p_territory != null) {
			return d_ownedTerritories.remove(p_territory);
		}
		return false;
	}
	
	/**
	 * Adds an order to the list of orders.
	 * This is issue_order from the project requirements, but in camelCase to match the rest of the code's style.
	 * @param p_order The order to add.
	 * @return True if the order was added successfully, otherwise false.
	 */
	public boolean issueOrder(Order p_order) {
		return d_orders.add(p_order);
	}
	
	/**
	 * Pops the next order from the list.
	 * This is next_order from the project requirements, but in camelCase to match the rest of the code's style.
	 * @return The next order to execute, or null if there are no orders to execute.
	 */
	public Order nextOrder() {
		if (!d_orders.isEmpty()) {
			return d_orders.pop();
		}
		return null;
	}
	
	/**
	 * Does the player have any more orders to execute?
	 * @return True if there is at least one order to execute, otherwise false.
	 */
	public boolean hasOrdersLeftToExecute() {
		return !d_orders.isEmpty();
	}
}
