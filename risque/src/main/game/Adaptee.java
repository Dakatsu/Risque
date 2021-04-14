package main.game;

import java.io.File;
import java.io.FileNotFoundException;
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
		System.out.println("Testing");
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
				System.out.println("Getting next line.");
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
								System.out.println("Continents: " + l_line +  " - " + l_splitLine.length);
								// If we added the continent, add it to the hashmap.
								if (l_splitLine.length >= 2 && l_map.createContinent(l_splitLine[0].replaceAll(" ", "_"), Integer.parseInt(l_splitLine[1]), l_map.getNumContinents() + 1)) {
									System.out.println("Putting continent!!!");
									l_continentIDs.put(l_splitLine[0], l_map.getNumContinents());
								}
								break;
							case "territories":
								String l_terrSplitLine[] = l_line.split(",");
								System.out.println("  Terr: " + l_line + " - " + l_terrSplitLine.length);
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
									System.out.println("    Terr created?");
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
								System.out.println("Default");
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
}
