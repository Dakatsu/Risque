package game;

import java.util.LinkedList;

/**
 * A territory on the map, also known as a country.
 * @author Kyle
 */
public class Territory {
	/**
	 * The human-readable name for this territory.
	 */
	private String d_name;
	
	/**
	 * The continent this territory belongs to.
	 */
	Continent d_continent;
	
	/**
	 * Constructs a new territory with a human name and no continent.
	 * @param p_name The human-readable name of the territory.
	 */
	public Territory(String p_name, Continent p_continent) {
		setName(p_name);
		setContinent(p_continent);
	}
	
	/**
	 * Constructs a new territory with a human name and no continent.
	 * @param p_name The human-readable name of the territory.
	 */
	public Territory(String p_name) {
		this(p_name, null);
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
	 * Gets the continent this territory resides on.
	 * @return Our continent.
	 */
	public Continent getContinent() {
		return d_continent;
	}
	
	/**
	 * Sets our continent to a different one.
	 * Note: this can be assigned null, but a map will not be considered valid if it is null.
	 * @param p_continent The new continent to belong to.
	 */
	public void setContinent(Continent p_continent) {
		d_continent = p_continent;
	}
}
