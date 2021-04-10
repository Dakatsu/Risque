package main.game;

/**
 * Superclass of entities that exist on a map and need common functionality.
 * @author Kyle
 */
public class MapEntity {

	/**
	 * The map this entity exists on.
	 */
	private Map d_map;
	
	/**
	 * Initializes the item with a map.
	 * @param p_map The map this item exists on.
	 */
	public MapEntity(Map p_map) {
		setMap(p_map);
	}
	
	/**
	 * Initializes the entity without a map.
	 */
	MapEntity() {
		this(null);
	}
	
	/**
	 * Gets the current map.
	 * @return The map.
	 */
	public Map getMap() {
		return d_map;
	}
	
	/**
	 * Sets the map to a new one.
	 * @param p_map The new map.
	 */
	public void setMap(Map p_map) {
		d_map = p_map;
	}
}
