package main.game;

import java.util.LinkedList;

/**
 * A collection of territories/countries.
 * @author Kyle
 *
 */
public class Continent extends MapEntity {
	/**
	 * The human-readable name for this continent.
	 */
	private String d_name;
	
	/**
	 * The number of bonus armies given when a continent is fully controlled.
	 */
	private int d_bonusArmies;
	
	/**
	 * Constructs a continent with a name and specific number of bonus armies.
	 * @param p_name The name for this continent.
	 * @param p_bonusArmies The number of bonus armies controlling this continent provides.
	 */
	public Continent(String p_name, int p_bonusArmies) {
		setName(p_name);
		setBonusArmies(p_bonusArmies);
	}
	
	/**
	 * Constructs a continent with a name and no bonus armies.
	 * @param p_name The name for this continent.
	 */
	public Continent(String p_name) {
		this(p_name, 0);
	}
	
	/**
	 * Gets the name of this continent.
	 * @return The name of this continent.
	 */
	public String getName() {
		return d_name;
	}
	
	/**
	 * Gets the human-readable name of this continent (no underscores).
	 * @return The human-readable name of this continent.
	 */
	public String getDisplayName() {
		return d_name.replace('_', ' ');
	}
	
	/**
	 * Sets the name of this territory to a new value.
	 * @param p_name The new name for this continent.
	 */
	public void setName(String p_name) {
		d_name = p_name;
	}
	
	/**
	 * Gets the number of bonus armies given when a continent is fully controlled.
	 * @return The number of bonus armies.
	 */
	public int getBonusArmies() {
		return d_bonusArmies;
	}
	
	/**
	 * Sets the number of bonus armies given for complete continental control to a new number.
	 * @param p_bonusArmies The new number of bonus armies to award.
	 * @return True if the bonus army number was changed, false if a number less than zero was entered.
	 */
	public boolean setBonusArmies(int p_bonusArmies) {
		if (p_bonusArmies >= 0) {
			d_bonusArmies = p_bonusArmies;
			return true;
		}
		return false;
	}

	public LinkedList<Territory> getTerritories() {
		// TODO Auto-generated method stub
		return null;
	}
}
