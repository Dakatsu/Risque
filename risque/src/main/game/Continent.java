package main.game;

/**
 * A collection of territories/countries.
 * @author Kyle
 *
 */
public class Continent {
	/**
	 * The human-readable name for this continent.
	 */
	private String d_name;
	
	/**
	 * The number of bonus armies given when a continent is fully controlled.
	 */
	private int d_bonusArmies;
	
	/**
	 * The player that controls this entire continent, if one exists.
	 */
	private Player d_playerOwner;
	
	/**
	 * Constructs a continent with a name and specific number of bonus armies.
	 * @param p_name The name for this continent.
	 * @param p_bonusArmies The number of bonus armies controlling this continent provides.
	 */
	public Continent(String p_name, int p_bonusArmies) {
		setName(p_name);
		setBonusArmies(p_bonusArmies);
		determinePlayerOwner();
	}
	
	/**
	 * Constructs a continent with a name and no bonus armies.
	 * @param p_name The name for this continent.
	 */
	public Continent(String p_name) {
		this(p_name, 0);
	}
	
	/**
	 * Gets the human-readable name of this territory.
	 * @return The name of this territory.
	 */
	public String getName() {
		return d_name;
	}
	
	/**
	 * Sets the human-readable name of this territory to a new value.
	 * @param p_name The new name for this territory.
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
	
	/**
	 * Returns the player who owns this continent.
	 * @return The player who controls every territory in this continent (if one exists), otherwise null.
	 */
	public Player getPlayerOwner() {
		return d_playerOwner;
	}
	
	/**
	 * Determines which player owns this continent, if one exists.
	 * TODO: Actual logic to detect the owner.
	 * @return The owning player (whether it changed or not), or null if nobody controls it.
	 */
	public Player determinePlayerOwner() {
		d_playerOwner = null;
		return getPlayerOwner();
	}
}
