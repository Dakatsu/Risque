package game;

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
	 */
	public void setBonusArmies(int p_bonusArmies) {
		d_bonusArmies = p_bonusArmies;
	}
}
