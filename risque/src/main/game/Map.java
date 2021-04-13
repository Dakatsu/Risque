package main.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a map file. It may be valid and playable or in the midst of being created and unplayable.
 * @author Kyle
 */
public class Map extends GameEntity {
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
	 * Gets a shallow copy of the list of territories.
	 * @return The list of territories.
	 */
	public LinkedList<Continent> getContinents() {
		return new LinkedList<Continent>(d_continents);
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
	 * Returns the continent's ID by its reference.
	 * @param l_continent The continent.
	 * @return The continent's ID if it exists, otherwise 0.
	 */
	public int getContinentID(Continent l_continent) {
		return d_continents.indexOf(l_continent) + 1;
	}
	
	/**
	 * Creates a continent at the given ID or at the last ID, whichever is less.
	 * Initializes the continent with a name and bonus army count.
	 * @param p_name The name of the new continent.
	 * @param p_numBonusArmies The number of bonus armies for the continent.
	 * @param p_continentID The desired ID of the new continent.
	 * @return True if a continent was created, otherwise false.
	 */
	public boolean createContinent(String p_name, int p_numBonusArmies, int p_continentID) {
		if (p_continentID > 0) {
			int l_newIdx = Math.min(p_continentID - 1, getNumContinents());
			Continent l_newContinent = new Continent(p_name, p_numBonusArmies);
			l_newContinent.setMap(this);
			d_continents.add(l_newIdx, l_newContinent);
			// Create an empty entry in the continent/territory map.
			d_continentTerritories.put(l_newContinent, new LinkedList<Territory>());
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a continent at the given ID or at the last ID, whichever is less.
	 * Gives it a generated name and five bonus armies.
	 * @param p_continentID The desired ID of the new continent.
	 * @param p_continentValue The number of bonus armies this continent gives when controlled.
	 * @return True if a continent was created, otherwise false.
	 */
	public boolean createContinent(int p_continentID, int p_continentValue) {
		// TODO: What are we doing about the names? Defaulting to a random name.
		Random l_random = new Random();
		String l_continentName = "Continentia".concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10)));
		return createContinent(l_continentName, p_continentValue, p_continentID);
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
	 * Gets a shallow copy of the list of territories.
	 * @return The list of territories.
	 */
	public LinkedList<Territory> getTerritories() {
		return new LinkedList<Territory>(d_territories);
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
	 * Returns the territory's ID by its reference.
	 * @param l_territory The continent.
	 * @return The territory's ID if it exists, otherwise 0.
	 */
	public int getTerritoryID(Territory l_territory) {
		return d_territories.indexOf(l_territory) + 1;
	}
	
	/**
	 * Creates a territory at the given ID or at the last ID, whichever is less.
	 * @param p_territoryID The desired ID of the new territory.
	 * @param p_name The human-readable name for this territory.
	 * @param p_continentID The ID of the continent this territory will belong to.
	 * @return True if a territory was created, otherwise false.
	 */
	public boolean createTerritory(int p_territoryID, String p_name, int p_continentID) {
		if (p_territoryID > 0 && p_continentID > 0 && p_continentID <= getNumContinents()) {
			int l_newIdx = Math.min(p_territoryID - 1, getNumTerritories());
			Territory l_newTerritory = new Territory(p_name, getContinent(p_continentID));
			l_newTerritory.setMap(this);
			// Make an empty list of neighbours to start.
			d_borders.put(l_newTerritory, new LinkedList<Territory>());
			d_continentTerritories.get(getContinent(p_continentID)).add(l_newTerritory);
			d_territories.add(l_newIdx, l_newTerritory);
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a territory at the given ID or at the last ID, whichever is less.
	 * @param p_territoryID The desired ID of the new territory.
	 * @param p_continentID The ID of the continent this territory will belong to.
	 * @return True if a territory was created, otherwise false.
	 */
	public boolean createTerritory(int p_territoryID, int p_continentID) {
		// TODO: What are we doing about names? Defaulting to a random designation for now.
		Random l_random = new Random();
		String l_territoryName = "Testlandia".concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10))).concat(String.valueOf(l_random.nextInt(10)));
		return createTerritory(p_territoryID, l_territoryName, p_continentID);
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
	 * Adds a border between two territories by ID.
	 * @param p_firstID The first territory's ID.
	 * @param p_secondID The second territory's ID.
	 * @return True if the border was added, false otherwise.
	 */
	public boolean addBorder(int p_firstID, int p_secondID) {
		return addBorder(getTerritory(p_firstID), getTerritory(p_secondID));
	}
	
	/**
	 * Adds a border between two territories.
	 * @param p_first The first territory.
	 * @param p_second The second territory.
	 * @return True if the border was added, false otherwise.
	 */
	public boolean addBorder(Territory p_first, Territory p_second) {
		// Don't allow us to border ourself.
		if (p_first == p_second) {
			return false;
		}
		if (p_first != null && p_second != null) {
			// There should already exist an empty LinkedList upon creation of the territory, so this should always work.
			// Do not add duplicates.
			boolean bAddedBorders = false;
			LinkedList<Territory> l_firstBorders = d_borders.get(p_first);
			if (!l_firstBorders.contains(p_second)) {
				l_firstBorders.add(p_second);
				bAddedBorders = true;
			}
			LinkedList<Territory> l_secondBorders = d_borders.get(p_second);
			if (!l_secondBorders.contains(p_first)) {
				l_secondBorders.add(p_first);
				bAddedBorders = true;
			}
			return bAddedBorders;
		}
		return false;
	}
	
	/**
	 * Removes a border between two territories by ID.
	 * @param p_firstID The first territory's ID.
	 * @param p_secondID The second territory's ID.
	 * @return True if a border was deleted, false otherwise.
	 */
	public boolean deleteBorder(int p_firstID, int p_secondID) {
		return deleteBorder(getTerritory(p_firstID), getTerritory(p_secondID));
	}
	
	/**
	 * Removes a border between two territories.
	 * @param p_first The first territory.
	 * @param p_second The second territory.
	 * @return True if a border was deleted, false otherwise.
	 */
	public boolean deleteBorder(Territory p_first, Territory p_second) {
		// A border with ourself should have never been created.
		if (p_first == p_second) {
			return false;
		}
		if (p_first != null && p_second != null) {
			LinkedList<Territory> l_firstNeighbours = d_borders.get(p_first);
			boolean l_removedFromFirst = l_firstNeighbours.remove(p_second);
			LinkedList<Territory> l_secondNeighbours = d_borders.get(p_second);
			boolean l_removedFromSecond = l_secondNeighbours.remove(p_first);
			// TODO: output an error if just one territory had the other as its neighbour.
			// That implies that there is a fault in the creation process.
			return l_removedFromFirst || l_removedFromSecond;
		}
		return false;
	}
	
	/**
	 * Returns a (copied) list of the territories belonging to a specific continent.
	 * @param p_continent The continent.
	 * @return A linked list of the territories belonging to the continent.
	 */
	public LinkedList<Territory> getContinentTerritories(Continent p_continent) {
		if (d_continentTerritories.containsKey(p_continent)) {
			return new LinkedList<Territory>(d_continentTerritories.get(p_continent));
		}
		return null;
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
		
		// Make sure we have at least one continent.
		if (getNumContinents() == 0) {
			return false;
		}
		
		// Validate that every territory belongs to a continent.
		for (Territory l_territory : d_territories) {
			if (l_territory.getContinent() == null) {
				return false;
			}
		}
		
		// Validate that each continent is a connected graph in itself.
		for (Continent l_continent : d_continents) {
			if (!validateContinent(l_continent)) {
				return false;
			}
		}
		
		/* TODO: Replace this nasty algorithm containing three nested for-loops with a better graph-traversing one.
		 * Validate that each continent is connected to the rest.
		 * Firstly, consider first continent as a connected (there may be only one).
		 * Then, traverse every territory in that continent and add any continents that have bordering territories with ours.
		 * Return true if we have added every continent to the list of connected continents.
		 * 
		 */
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
	private boolean validateContinent(Continent p_continent) {
		// We cannot be valid if we have no territories.
		LinkedList<Territory> l_territoriesOnContinent = d_continentTerritories.get(p_continent);
		if (d_continentTerritories.get(p_continent).size() == 0) {
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
					if (l_connectedTerritories.size() == d_continentTerritories.get(p_continent).size()) {
						return true;
					}
				}
			}
		}
		
		return l_connectedTerritories.size() == d_continentTerritories.get(p_continent).size();
	}
	
	
	/**
	 * Saves the input map to a given file name. Overwrites any existing map with the same name.
	 * The map will only save if it is valid.
	 * @param p_map The map to save.
	 * @param p_fileName The name of the file to save to, including the extension.
	 * @return Whether the file was successfully saved.
	 */
	public static boolean SaveToFile(Map p_map, String p_fileName) {
		/**
		 * Reference on what a .map file entails:
		 * http://domination.sourceforge.net/makemaps.shtml
		 */
		
		// Do not allow us to save if the map is not valid.
		if (!p_map.validateMap()) {
			return false;
		}
		
		try {
			// Attempt to create the file. Override it if it already exists.
			File l_file = new File(p_fileName);
			if (l_file.exists()) {
				l_file.delete();
			}
			
			// Begin by writing a comment containing the file name and that it was made by this program.
			FileWriter l_writer = new FileWriter(p_fileName);
			l_writer.write("; map: " + p_fileName + "\n; created in Risque, a game project for Concordia University's SOEN 6441 class\n\n");
			
			/**
			 *  Write the contents of the map as text to the file.
			 */
			l_writer.write(p_map.toText());
			
			/**
			 * Close the file writer once we have finished.
			 */
			l_writer.close();
			return true;
		} 
		catch (IOException l_exception) {
			return false;
		}
	}
	
	/**
	 * Outputs the current map as text format. Identical to the representation in a .map file.
	 * @return The map as text.
	 */
	public String toText() {
		/**
		 * Reference on what a .map file entails:
		 * http://domination.sourceforge.net/makemaps.shtml
		 */
		String l_mapAsString = "";
		
		/**
		 *  Write the continents header, then print out the continents in this format:
		 *    continent-name number-of-bonus-armies colour
		 *  TODO: colour is not loaded or used by this game, so we are defaulting to everything being purple.
		 */
		l_mapAsString += continentsToString();
		
		/**
		 *  Write the countries header, then print out the territories in this format:
		 *    territory-ID territory-name continent-ID coordinate coordinate
		 *  While doing so, prepare the border strings. They are the territory's ID followed by the ID of every bordering nation.
		 *  TODO: we do not use coordinates for this game, so defaulting to zeroes for both.
		 */
		LinkedList<String> l_borderStrings = new LinkedList<>();
		l_mapAsString += "\n[countries]\n";
		for (int l_tID = 1; l_tID <= getNumTerritories(); l_tID++) {
			Territory l_territory = getTerritory(l_tID);
			int l_cID = getContinentID(l_territory.getContinent());
			l_mapAsString += l_tID + " " + l_territory.getName() +  " " + l_cID + " 0 0\n";
			
			// Write the border strings.
			String l_borderString = Integer.toString(l_tID);
			if (d_borders.containsKey(l_territory)) {
				LinkedList<Territory> l_borderingTerritories = d_borders.get(l_territory);
				for (Territory l_borderingTerritory : l_borderingTerritories) {
					l_borderString += " " + Integer.toString(getTerritoryID(l_borderingTerritory));
				}
			}
			l_borderStrings.add(l_borderString);
		}
		
		/**
		 *  Write the borders header, then print out the borders we already calculated above.
		 */
		l_mapAsString += "\n[borders]\n";
		for (String l_borderString : l_borderStrings) {
			l_mapAsString += l_borderString + "\n";
		}
		
		// Return the final result.
		return l_mapAsString;
	}
	
	/**
	 * Outputs the continents as a string. Identical to the representation in a .map file.
	 * @return The string representation of the continents.
	 */
	public String continentsToString() {
		/**
		 *  Write the continents header, then print out the continents in this format:
		 *    continent-name number-of-bonus-armies colour
		 *  TODO: colour is not loaded or used by this game, so we are defaulting to everything being purple.
		 */
		String l_continentsAsString = "[continents]\n";
		for (int l_cID = 1; l_cID <= getNumContinents(); l_cID++) {
			Continent l_continent = getContinent(l_cID);
			l_continentsAsString += l_continent.getName() + " " + l_continent.getBonusArmies() + " purple\n";
		}
		return l_continentsAsString;
	}
}
