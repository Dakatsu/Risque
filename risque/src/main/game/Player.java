package main.game;

import main.controller.PlayerStrategy;

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
public class Player extends GameEntity {
	
	/**
	 * The current player's strategy, if one exists.
	 */
	private PlayerStrategy d_strategy;
	
	/**
	 * The player's name.
	 */
	private String d_name;
	
	/**
	 * The territories this player owns.
	 */
	private LinkedList<Territory> d_ownedTerritories;
	
	/**
	 * The continents this player owns/controls.
	 */
	private LinkedList<Continent> d_ownedContinents;
	
	/**
	 * All the player's issued but unexecuted orders.
	 * TODO: Change to queue?
	 */
	private LinkedList<Order> d_orders;
	
	/**
	 * Number of armies left to deploy for this player.
	 * TODO: Add getter/setter and make private!
	 */
	private int d_numUndeployedArmies;
	
	/**
	 * A list of cards the player has available.
	 * A card is represented as a string, e.g. "bomb" or "airlift".
	 */
	private LinkedList<String> d_cards;
	
	/**
	 * The allies that this player has (i.e. they cannot attack each other).
	 */
	private LinkedList<Player> d_allies;
	
	/**
	 * Default constructor for player.
	 * @param p_name The player's starting name.
	 */
	public Player(String p_name) {
		setName(p_name);
		setNumUndeployedArmies(0);
		d_ownedTerritories = new LinkedList<>();
		d_ownedContinents = new LinkedList<>();
		d_orders = new LinkedList<>();
		d_cards = new LinkedList<>();
		d_allies = new LinkedList<>();
		d_strategy = null;
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
	 * Does this player own a specific territory?
	 * @param p_territory The territory to check ownership of.
	 * @return True if the player owns the territory, otherwise false.
	 */
	public boolean ownsTerritory(Territory p_territory) {
		return d_ownedTerritories.contains(p_territory);
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
	 * Returns a shallow copy of the territories this player owns.
	 * @return The list of owned territories.
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Territory> getOwnedTerritories() {
		return (LinkedList<Territory>)d_ownedTerritories.clone();
	}
	
	/**
	 * Does this player control a specific continent?
	 * @param p_continent The continent to check ownership of.
	 * @return True if the player owns the continent, otherwise false.
	 */
	public boolean ownsContinent(Continent p_continent) {
		return d_ownedContinents.contains(p_continent);
	}
	
	/**
	 * Adds the continent to the list of this player's owned continents.
	 * @param p_continent The continent to add.
	 * @return True if the player did not own this continent before, otherwise false.
	 */
	public boolean addOwnedContinent(Continent p_continent) {
		if (p_continent != null && !d_ownedContinents.contains(p_continent)) {
			return d_ownedContinents.add(p_continent);
		}
		return false;
	}
	
	/**
	 * Removes the continent to the list of this player's owned continents.
	 * @param p_continent The continent to remove.
	 * @return True if the player owned this continent and it was removed, otherwise false.
	 */
	public boolean removeOwnedContinent(Continent p_continent) {
		if (p_continent != null) {
			return d_ownedContinents.remove(p_continent);
		}
		return false;
	}
	
	/**
	 * Returns a shallow copy of the continents this player owns.
	 * @return The list of owned continents.
	 */
	@SuppressWarnings("unchecked")
	public LinkedList<Continent> getOwnedContinents() {
		return (LinkedList<Continent>)d_ownedContinents.clone();
	}
	
	/**
	 * Gets the number of undeployed armies.
	 * @return The number of undeployed armies.
	 */
	public int getNumUndeployedArmies() {
		return d_numUndeployedArmies;
	}
	
	/**
	 * Sets the number of undeployed armies to a new value. Will not go below zero.
	 * @param p_newNum The desired number of armies.
	 * @return The number of armies after ensuring it is zero or greater.
	 */
	public int setNumUndeployedArmies(int p_newNum) {
		d_numUndeployedArmies = Math.max(0, p_newNum);
		return d_numUndeployedArmies;
	}
	
	/**
	 * Adds armies to this player's undeployed army count.
	 * @param p_armiesToAdd The number of armies to add.
	 */
	public void addUndeployedArmies(int p_armiesToAdd) {
		d_numUndeployedArmies += p_armiesToAdd;
	}
	
	/**
	 * Subtracts armies from the player's undeployed army count.
	 * @param p_armiesToRemove The number of armies to remove.
	 * @return The number of armies actually subtracted.
	 */
	public int removeUndeployedArmies(int p_armiesToRemove) {
		int l_armiesRemoved = Math.min(d_numUndeployedArmies, p_armiesToRemove);
		d_numUndeployedArmies -= l_armiesRemoved;
		return l_armiesRemoved;
	}
	
	/**
	 * Adds an order to the list of orders.
	 * This is issue_order from the project requirements, but in camelCase to match the rest of the code's style.
	 * @param p_order The order to add.
	 * @return True if the order was added successfully, otherwise false.
	 */
	public boolean issueOrder(Order p_order) {
		if (p_order != null) {
			p_order.setIssuer(this);
		}
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
	
	/**
	 * Does the player have a specific card?
	 * @param p_card The card (string) to check.
	 * @return True if the player has the card.
	 */
	public boolean hasCard(String p_card) {
		return d_cards.contains(p_card.toLowerCase());
	}
	
	/**
	 * Adds a card to the player's card collection.
	 * @param p_card The card to add (as a string).
	 * @return True if the card was added.
	 */
	public boolean addCard(String p_card) {
		return d_cards.add(p_card.toLowerCase());
	}
	
	/**
	 * Removes a card from the list of cards.
	 * @param p_card The card (string) to remove.
	 * @return True if the card existed and was removed, false otherwise.
	 */
	public boolean removeCard(String p_card) {
		return d_cards.remove(p_card.toLowerCase());
	}
	
	/**
	 * Is this player allied with this player?
	 * @param p_player The player to check.
	 * @return True if we are allies.
	 */
	public boolean isAllyWith(Player p_player) {
		if (p_player != null) {
			return d_allies.contains(p_player);
		}
		return false;
	}
	
	/**
	 * Adds an ally to this player's ally list.
	 * @param p_player The player to ally with.
	 * @return True if the player was added.
	 */
	public boolean addAlly(Player p_player) {
		if (p_player != null) {
			return d_allies.add(p_player);
		}
		return false;
	}
	
	/**
	 * Removes a player from the allies list.
	 * @param p_player The player to unally with.
	 * @return True if the player was an ally and was removed.
	 */
	public boolean removeAlly(Player p_player) {
		if (p_player != null) {
			return d_allies.remove(p_player);
		}
		return false;
	}
	
	/**
	 * Gets rid of all this player's allies.
	 */
	public void clearAllies() {
		d_allies.clear();
	}
	
	/**
	 * This method returns the strategy
	 *
	 * @return Strategy
	 */
	public PlayerStrategy getStrategy() {
		return d_strategy;
	}
	
	/**
	 * This method sets the strategy
	 * 
	 * @param strategy Strategy to be set
	 */
	public void setStrategy(PlayerStrategy strategy) {
		this.d_strategy = strategy;
	}
	
	/**
	 * Is this player controlled by an AI? True if they have a strategy.
	 * @return Whether player is controlled by an AI.
	 */
	public boolean isAIPlayer() {
		return d_strategy != null;
	}
	
	/**
	 * Called by the engine to notify the player that their turn has started.
	 */
	public void notifyTurnStart() {
		if (isAIPlayer()) {
			d_strategy.onNotifyTurn();
		}
		else {
			d_engine.broadcastMessage("It is now " + getName() + "\'s turn.");
		}
	}
}
