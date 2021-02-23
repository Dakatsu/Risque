package main.game;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Represents a map file. It may be valid and playable or in the midst of being created and unplayable.
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
	 * The lists of territories that make up each continent.
	 */
	HashMap<Continent, LinkedList<Territory>> d_continentTerritories;
	
	/**
	 * Default constructor that creates an empty map.
	 */
	public Map() {
		d_continents = new LinkedList<>();
		d_territories = new LinkedList<>();
		d_borders = new HashMap<>();
		d_continentTerritories = new HashMap<>();
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
			// TODO: What are we doing about the names? Defaulting to a random name.
			Random l_random = new Random();
			String l_continentName = "Continentia".concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10)));
			// TODO: What are we doing about the bonus armies? Defaulting to five.
			int l_numBonusArmies = 5;
			Continent l_newContinent = new Continent(l_continentName, l_numBonusArmies);
			d_continents.add(l_newIdx, l_newContinent);
			// Create an empty entry in the continent/territory map.
			d_continentTerritories.put(l_newContinent, new LinkedList<Territory>());
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
		return deleteContinent(getContinent(p_continentID));
	}
	
	/**
	 * Deletes the continent by reference.
	 * All territories connected to this continent will also be removed.
	 * @param p_continent The continent to remove.
	 * @return True if a continent was deleted, otherwise false.
	 */
	private boolean deleteContinent(Continent l_continent) {
		if (l_continent != null) {
			// The continent should always be in the continent/territory map even if it has no territories.
			for (Territory l_territory : d_continentTerritories.get(l_continent)) {
				deleteTerritory(l_territory);
			}
			d_continentTerritories.remove(l_continent);
			return d_continents.remove(l_continent);
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
		if (p_territoryID > 0 && p_continentID > 0 && p_continentID <= getNumContinents()) {
			int l_newIdx = Math.min(p_territoryID - 1, getNumTerritories());
			// TODO: What are we doing about names? Defaulting to a random designation for now.
			Random l_random = new Random();
			String l_territoryName = "Testlandia".concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10)));
			Territory l_newTerritory = new Territory(l_territoryName, getContinent(p_continentID));
			// Make an empty list of neighbours to start.
			d_borders.put(l_newTerritory, new LinkedList<Territory>());
			d_continentTerritories.get(getContinent(p_continentID)).add(l_newTerritory);
			d_territories.add(l_newIdx, l_newTerritory);
			return true;
		}
		return false;
	}
	
	/**
	 * Deletes the territory at the given ID.
	 * @param p_territoryID The ID of the territory to delete.
	 * @return True if a territory was deleted, otherwise false.
	 */
	public boolean deleteTerritory(int p_territoryID) {
		return deleteTerritory(getTerritory(p_territoryID));
	}
	
	/**
	 * Deletes the territory by reference.
	 * @param p_territory The territory to delete.
	 * @return True if a territory was deleted, otherwise false.
	 */
	private boolean deleteTerritory(Territory p_territory) {
		if (p_territory != null) {
			for (Territory l_otherTerritory : d_territories) {
				d_borders.get(l_otherTerritory).remove(p_territory);
			}
			d_borders.remove(p_territory);
			return d_territories.remove(p_territory);
		}
		return false;
	}
	
	/**
	 * Checks to see if a border exists between the two territories.
	 * @param p_firstID The first territory's ID.
	 * @param p_secondID The second territory's ID.
	 * @return True if the border exists, false otherwise.
	 */
	public boolean doesBorderExist(int p_firstID, int p_secondID) {
		return doesBorderExist(getTerritory(p_firstID), getTerritory(p_secondID));
	}
	
	/**
	 * Checks to see if a border exists between the two territories.
	 * @param p_first The first territory.
	 * @param p_second The second territory.
	 * @return True if the border exists, false otherwise.
	 */
	private boolean doesBorderExist(Territory p_first, Territory p_second) {
		// Can we border ourself? Let's go with 'no'.
		if (p_first == p_second) {
			return false;
		}
		// Note: which territory is first or second should not affect the outcome of this result.
		if (p_first != null && p_second != null && d_borders.containsKey(p_first)) {
			LinkedList<Territory> l_borderingTerritories = d_borders.get(p_first);
			return l_borderingTerritories.contains(p_second);
		}
		return false;
	}
	
	/**
	 * Adds a border between two territories.
	 * @param p_firstID The first territory's ID.
	 * @param p_secondID The second territory's ID.
	 * @return True if the border was added, false otherwise.
	 */
	public boolean addBorder(int p_firstID, int p_secondID) {
		// Don't allow us to border ourself.
		if (p_firstID == p_secondID) {
			return false;
		}
		Territory l_firstTerritory = getTerritory(p_firstID);
		Territory l_secondTerritory = getTerritory(p_secondID);
		if (l_firstTerritory != null && l_secondTerritory != null) {
			// There should already exist an empty LinkedList upon creation of the territory, so this should always work.
			d_borders.get(l_firstTerritory).add(l_secondTerritory);
			d_borders.get(l_secondTerritory).add(l_firstTerritory);
			return true;
		}
		return false;
	}
	
	/**
	 * Removes a border between two territories.
	 * @param p_firstID The first territory's ID.
	 * @param p_secondID The second territory's ID.
	 * @return True if a border was deleted, false otherwise.
	 */
	public boolean deleteBorder(int p_firstID, int p_secondID) {
		// A border with ourself should have never been created.
		if (p_firstID == p_secondID) {
			return false;
		}
		Territory l_firstTerritory = getTerritory(p_firstID);
		Territory l_secondTerritory = getTerritory(p_secondID);
		if (l_firstTerritory != null && l_secondTerritory != null) {
			LinkedList<Territory> l_firstNeighbours = d_borders.get(l_firstTerritory);
			boolean l_removedFromFirst = l_firstNeighbours.remove(l_secondTerritory);
			LinkedList<Territory> l_secondNeighbours = d_borders.get(l_secondTerritory);
			boolean l_removedFromSecond = l_secondNeighbours.remove(l_firstTerritory);
			// TODO: output an error if just one territory had the other as its neighbour.
			// That implies that there is a fault in the creation process.
			return l_removedFromFirst || l_removedFromSecond;
		}
		return false;
	}
	
	/**
	 * Checks the map for correctness (i.e. is a connected graph).
	 * @return whether the map is valid.
	 */
	public boolean validateMap() {
		// TODO: Proper error messages?
		// Make sure we have at least two territories (even though a two-territory game would be sad).
		if (getNumTerritories() <= 1) {
			return false;
		}
		System.out.println("Got to A");
		// Make sure we have at least one continent.
		if (getNumContinents() == 0) {
			return false;
		}
		
		System.out.println("Got to B");
		// Validate that every territory belongs to a continent.
		for (Territory l_territory : d_territories) {
			if (l_territory.getContinent() == null) {
				return false;
			}
		}
		
		System.out.println("Got to C");
		// Validate that each continent is a connected graph in itself.
		for (Continent l_continent : d_continents) {
			if (!validateContinent(l_continent)) {
				return false;
			}
		}
		
		/* TODO: Replace this nasty algorithm with three nested for-loops with a better graph-traversing one.
		 * Validate that each continent is connected to the rest.
		 * Firstly, consider first continent as a connected (there may be only one).
		 * Then, traverse every territory in that continent and add any continents that have bordering territories with ours.
		 * Return true if we have added every continent to the list of connected continents.
		 * 
		 */
		System.out.println("Got to D");
		LinkedList<Continent> l_connectedContinents = new LinkedList<>();
		l_connectedContinents.add(getContinent(1));
		for (int l_idx = 0; l_idx < l_connectedContinents.size(); l_idx++) {
			LinkedList<Territory> l_continentTerritories = d_continentTerritories.get(l_connectedContinents.get(l_idx));
			for (Territory l_territory : l_continentTerritories) {
				for (Territory l_borderingTerritory : d_borders.get(l_territory)) {
					Continent l_borderingContinent = l_borderingTerritory.getContinent();
					if (!l_connectedContinents.contains(l_borderingContinent)) {
						l_connectedContinents.add(l_borderingContinent);
						if (l_connectedContinents.size() == getNumContinents()) {
							return true;
						}
					}
				}
			}
		}
		return l_connectedContinents.size() == getNumContinents();
	}
	
	/**
	 * Validates the continent by checking that it has at least one territory and that every territory within it is connected.
	 * @param l_continent The continent to validate.
	 * @return True if the continent is valid, otherwise false.
	 */
	private boolean validateContinent(Continent l_continent) {
		// We cannot be valid if we have no territories.
		LinkedList<Territory> l_territoriesOnContinent = d_continentTerritories.get(l_continent);
		if (d_continentTerritories.get(l_continent).size() == 0) {
			return false;
		}
		
		/* TODO: Replace this with a proper graph-traversing algorithm instead of this Big O(n^2) monstrosity.
		 * We add one territory to the connected territories list (we may only have one).
		 * Then, we traverse all territories on the continent and add any we border.
		 * We do this until either we run out of territories in the connected territory list,
		 *   or we have accounted for every territory.
		 */
		LinkedList<Territory> l_connectedTerritories = new LinkedList<>();
		l_connectedTerritories.add(l_territoriesOnContinent.getFirst());
		for (int l_idx = 0; l_idx < l_connectedTerritories.size(); l_idx++) {
			for (Territory l_territory : l_territoriesOnContinent) {
				if (!l_connectedTerritories.contains(l_territory) && doesBorderExist(l_connectedTerritories.get(l_idx), l_territory)) {
					l_connectedTerritories.add(l_territory);
					if (l_connectedTerritories.size() == d_continentTerritories.get(l_continent).size()) {
						return true;
					}
				}
			}
		}
		
		return l_connectedTerritories.size() == d_continentTerritories.get(l_continent).size();
	}
}
