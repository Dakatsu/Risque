package main.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Represents a map file. It may be valid and playable, or in the midst of being created.
 * @author Kyle
 */
public class Map {
	/** 
	 * The list of continents in this map.
	 * Their ID is equivalent to their index in this list + 1.
	 */
	LinkedList<Continent> d_continents;
	
	/**
	 * The list of territories in this map.
	 * Their ID is equivalent to their index in this list + 1.
	 */
	LinkedList<Territory> d_territories;
	
	/**
	 * The lists of borders that connect two territories.
	 */
	HashMap<Territory, LinkedList<Territory>> d_borders;
	
	/**
	 * Default constructor that creates an empty map.
	 */
	public Map() {
		d_continents = new LinkedList<>();
		d_territories = new LinkedList<>();
		d_borders = new HashMap<>();
	}
	
	/**
	 * Returns the number of continents in this map.
	 * @return The number of continents.
	 */
	public int getNumContinents() {
		return d_continents.size();
	}
	
	/**
	 * Returns the continent by its ID if it exists.
	 * @param l_continentID The continent's ID.
	 * @return The continent at the ID, otherwise null.
	 */
	public Continent getContinent(int l_continentID) {
		if (l_continentID > 0 && l_continentID <= getNumContinents()) {
			return d_continents.get(l_continentID - 1);
		}
		return null;
	}
	
	/**
	 * Creates a continent at the given ID or at the last ID, whichever is less.
	 * @param p_continentID The desired ID of the new continent.
	 * @return True if a continent was created, otherwise false.
	 */
	public boolean createContinent(int p_continentID) {
		if (p_continentID > 0) {
			int l_newIdx = Math.min(p_continentID - 1, getNumContinents());
			// TODO: What are we doing about the bonus armies? Defaulting to five.
			int l_numBonusArmies = 5;
			Continent l_newContinent = new Continent("Continentia".concat(String.valueOf(p_continentID)), l_numBonusArmies);
			d_continents.add(l_newIdx, l_newContinent);
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes the continent at the given ID.
	 * All territories connected to this continent will also be removed.
	 * @param p_continentID The ID of the continent to remove.
	 * @return True if a continent was deleted, otherwise false.
	 */
	public boolean deleteContinent(int p_continentID) {
		Continent l_continentToDelete = getContinent(p_continentID);
		if (l_continentToDelete != null) {
			for (int l_territoryID = 1; l_territoryID <= getNumTerritories(); l_territoryID++) {
				if (getTerritory(l_territoryID).getContinent() == l_continentToDelete) {
					deleteTerritory(l_territoryID);
					// Go back one index since a new territory will occupy this ID after deletion.
					l_territoryID--;
				}
			}
			return d_continents.remove(l_continentToDelete);
		}
		return false;
	}
	
	/**
	 * Returns the number of territories in this map.
	 * @return The number of territories.
	 */
	public int getNumTerritories() {
		return d_territories.size();
	}
	
	/**
	 * Returns the territory by its ID if it exists.
	 * @param l_territoryID The territory's ID.
	 * @return The territory at the ID, otherwise null.
	 */
	public Territory getTerritory(int l_territoryID) {
		if (l_territoryID > 0 && l_territoryID <= getNumTerritories()) {
			return d_territories.get(l_territoryID - 1);
		}
		return null;
	}
	
	/**
	 * Creates a territory at the given ID or at the last ID, whichever is less.
	 * @param p_territoryID The desired ID of the new territory.
	 * @param p_continentID The ID of the continent this territory will belong to.
	 * @return True if a territory was created, otherwise false.
	 */
	public boolean createTerritory(int p_territoryID, int p_continentID) {
		if (p_territoryID > 0 && p_continentID > 0 && p_continentID <= getNumTerritories()) {
			int l_newIdx = Math.min(p_territoryID - 1, getNumTerritories());
			// TODO: What are we doing about names? Defaulting to a random designation for now.
			Random l_random = new Random();
			String l_territoryName = "Testlandia".concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10)));
			Territory l_newTerritory = new Territory(l_territoryName, getContinent(p_continentID));
			d_territories.add(l_newIdx, l_newTerritory);
		}
		return false;
	}
	
	/**
	 * Deletes the territory at the given ID.
	 * @param p_territoryID
	 * @return True if a territory was deleted, otherwise false.
	 */
	public boolean deleteTerritory(int p_territoryID) {
		Territory l_territoryToDelete = getTerritory(p_territoryID);
		if (l_territoryToDelete != null) {
			return d_territories.remove(l_territoryToDelete);
		}
		return false;
	}
	
	/**
	 * Adds a border between two territories.
	 * @param p_firstID The first territory.
	 * @param p_secondID The second territory.
	 * @return True if the border was added, false otherwise.
	 */
	public boolean addBorder(int p_firstID, int p_secondID) {
		//TODO
		return false;
	}
	
	/**
	 * Removes a border between two countries.
	 * @param p_firstID The first territory.
	 * @param p_secondID The second territory.
	 * @return True if the border was added, false otherwise.
	 */
	public boolean removeBorder(int p_firstID, int p_secondID) {
		//TODO
		return false;
	}
	
	/**
	 * Checks the map for correctness (i.e. is a connected graph).
	 * @return whether the map is valid.
	 */
	public boolean validateMap() {
		// TODO: Proper error messages?
		// TODO: Validate each continent is itself a connected graph.
		// TODO: Validate each continent is connected to all others (therefore confirming the whole map is connected).
		// Validate that every territory belongs to a continent.
		for (Territory l_territory : d_territories) {
			if (l_territory.getContinent() == null) {
				return false;
			}
		}
		return false;
	}
}
