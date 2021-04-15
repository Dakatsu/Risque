package main.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class is used for reading and writing map files in Domination format
 *
 */
public class MapReaderWriter {
	
	
	/**
	 * Loads a map from a given file and returns it.
	 * Note that attributes not used by this game are ignored and not loaded.
	 * This means that those attributes will not be present when saving a map from this game.
	 * 
	 * @param p_file The file to load from (including the extension).
	 * @return Whether a valid map was loaded.
	 */
	public Map loadFromFile(File p_file) {
		Map l_map = new Map();
		/**
		 * Reference on what a .map file entails:
		 * http://domination.sourceforge.net/makemaps.shtml
		 */
		try {
			Scanner l_reader = new Scanner(p_file);
			String l_section = "";
			while (l_reader.hasNextLine()) {
				String l_line = l_reader.nextLine();
				
				// Ignore comments, which start with: ;
				if (!l_line.startsWith(";")) {
					
					if (l_line.startsWith("[")) {
						l_section = l_line.substring(1, l_line.length() - 1);
					}
					else {
						String l_splitLine[] = l_line.split(" ");
						switch(l_section) {
							case "continents":
								if (l_splitLine.length >= 2) {
									l_map.createContinent(l_splitLine[0], Integer.parseInt(l_splitLine[1]), l_map.getNumContinents() + 1);
								}
								break;
							case "countries":
								if (l_splitLine.length >= 3) {
									l_map.createTerritory(Integer.parseInt(l_splitLine[0]), l_splitLine[1], Integer.parseInt(l_splitLine[2]));
								}
								break;
							case "borders":
								if (l_splitLine.length >= 2) {
									for (int l_idx = 1; l_idx < l_splitLine.length; l_idx++) {
										l_map.addBorder(Integer.parseInt(l_splitLine[0]), Integer.parseInt(l_splitLine[l_idx]));
									}
								}
								break;
							default:
								// Do nothing. We do not care about any other sections.
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
}
