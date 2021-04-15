package main.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class is used for reading and writing map files in Conquest format
 *
 */
public class Adaptee {
	
	/**
	 * Loads a map from a given file and returns it.
	 * Note that attributes not used by this game are ignored and not loaded.
	 * This means that those attributes will not be present when saving a map from this game.
	 * 
	 * @param p_file The file to load from (including the extension).
	 * @return Whether a valid map was loaded.
	 */
	
	public Map loadFromFile(File p_file) {
		// TODO: this could be turned into a static function that returns a new Map object.
		Map l_map = new Map();
		/**
		 * Reference on what a .map file entails:
		 * http://domination.sourceforge.net/makemaps.shtml
		 */
		try {
			Scanner l_reader = new Scanner(p_file);
			String l_section = "";
			// Map of each continent name to its ID.
			HashMap<String, Integer> l_continentIDs = new HashMap<>();
			// Map of each territory to the names of its bordering territories.
			HashMap<Territory, LinkedList<String>> l_territoryNeighbours = new HashMap<>();
			while (l_reader.hasNextLine()) {
				String l_line = l_reader.nextLine();
				
				// Ignore comments, which start with: ;
				if (!l_line.startsWith(";")) {
					
					if (l_line.startsWith("[")) {
						l_section = l_line.substring(1, l_line.length() - 1).toLowerCase();
					}
					else {
						switch(l_section) {
							case "continents":
								String l_splitLine[] = l_line.split("=");
								// If we added the continent, add it to the hashmap.
								if (l_splitLine.length >= 2 && l_map.createContinent(l_splitLine[0].replaceAll(" ", "_"), Integer.parseInt(l_splitLine[1]), l_map.getNumContinents() + 1)) {
									l_continentIDs.put(l_splitLine[0], l_map.getNumContinents());
								}
								break;
							case "territories":
								String l_terrSplitLine[] = l_line.split(",");
								/*
								 * Conquest format uses these elements, comma-delimited:
								 * 0: the territory name.
								 * 1,2: territory coordinates (not used for our game)
								 * 3: the name of the continent it belongs on
								 * 4+: the names of the territories this territory borders.
								 * As such, there must be at least 4 fields. If there are no bordering territories, this map will be invalid and not returned.
								 */
								//System.out.println("1: " + (l_terrSplitLine.length > 3));
								//System.out.println("2: " + (l_continentIDs.containsKey(l_terrSplitLine[3])));
								//System.out.println("? - " + l_terrSplitLine[3]);
								if (l_terrSplitLine.length > 3 && l_continentIDs.containsKey(l_terrSplitLine[3])) {
									l_map.createTerritory(l_map.getNumTerritories() + 1, l_terrSplitLine[0].replaceAll(" ", "_"), l_continentIDs.get(l_terrSplitLine[3]));
									// Add the names of the territories so we can connect them later.
									Territory l_terr = l_map.getTerritory(l_map.getNumTerritories());
									if (l_terr != null && l_terrSplitLine.length > 4) {
										LinkedList<String> l_terrNames = new LinkedList<>();
										for (int l_idx = 4; l_idx < l_terrSplitLine.length; l_idx++) {
											l_terrNames.add(l_terrSplitLine[l_idx].replaceAll(" ", "_"));
										}
										l_territoryNeighbours.put(l_terr, l_terrNames);
									}
								}
								break;
							default:
								// Do nothing. We do not care about any other sections.
						}
					}
				}
			}
			// Connect territories based on the list of names.
			for (int l_idx = 1; l_idx <= l_map.getNumTerritories(); l_idx++) {
				Territory l_territory = l_map.getTerritory(l_idx);
				if (l_territory != null) {
					LinkedList<String> l_nameList = l_territoryNeighbours.get(l_territory);
					for (String l_neighbourName : l_nameList) {
						Territory l_neighbour = l_map.getTerritory(l_neighbourName);
						if (l_neighbour != null && l_territory != l_neighbour) {
							l_map.addBorder(l_territory, l_neighbour);
						}
					}
				}
				
			}
			l_reader.close();
			if (l_map.validateMap()) {
				return l_map;
			}
		} 
		catch (FileNotFoundException | NumberFormatException l_exception) {
			l_exception.printStackTrace();
			// Do nothing; we'll just return null below.
		}
		// Return null if we did not load a valid map by this point.
		return null;
	}
	
	/**
	 * Saves the input map to a given file name. Overwrites any existing map with the same name.
	 * The map will only save if it is valid.
	 * @param p_map The map to save.
	 * @param p_fileName The name of the file to save to, including the extension.
	 * @return Whether the file was successfully saved.
	 */
	public boolean saveToFile(Map p_map, String p_fileName) {
		/**
		 * Reference on what a conquest .map file entails:
		 * http://www.windowsgames.co.uk/conquest_create.html
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
			
			// Begin by writing the barebones [map] section.
			FileWriter l_writer = new FileWriter(p_fileName);
			l_writer.write("[map]\nauthor=Risque-SOEN6441-Team-21\n\n[continents]\n");
			
			// Write the continents.
			for (int l_idx = 1; l_idx <= p_map.getNumContinents(); l_idx++) {
				Continent l_continent = p_map.getContinent(l_idx);
				if (l_continent != null) {
					l_writer.write(l_continent.getName() + "=" + l_continent.getBonusArmies() + "\n");
				}
			}
			
			// Write the territories (with their neighbours listed in-line).
			l_writer.write("\n[territories]\n");
			for (int l_idx = 1; l_idx <= p_map.getNumTerritories(); l_idx++) {
				Territory l_territory = p_map.getTerritory(l_idx);
				if (l_territory != null) {
					LinkedList<Territory> l_neighbours = new LinkedList<>();
					for (Territory l_testNeighbour : p_map.getTerritories()) {
						if (p_map.doesBorderExist(l_territory, l_testNeighbour)) {
							l_neighbours.add(l_testNeighbour);
						}
					}
					l_writer.write(l_territory.getName() + ",0,0," + l_territory.getContinent().getName());
					for (Territory l_neighbour : l_neighbours) {
						l_writer.write("," + l_neighbour.getName());
					}
					l_writer.write("\n");
				}
			}
			
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
}
