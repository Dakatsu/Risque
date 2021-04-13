package main.game;

import java.io.File;
import java.io.FileNotFoundException;
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
	
	public Map LoadFromFile(File p_file) {
		// TODO: this could be turned into a static function that returns a new Map object.
		Map l_map = new Map();
		/**
		 * Reference on what a .map file entails:
		 * http://domination.sourceforge.net/makemaps.shtml
		 */
		try {
			Scanner l_reader = new Scanner(p_file);
			String l_section = "";
			int l_terrNum = 0;
			while (l_reader.hasNextLine()) {
				String l_line = l_reader.nextLine();
				
				// Ignore comments, which start with: ;
				if (!l_line.startsWith(";")) {
					
					if (l_line.startsWith("[")) {
						l_section = l_line.substring(1, l_line.length() - 1);
					}
					else {
						switch(l_section) {
							case "Continents":
								String l_splitCont[] = l_line.split("=");
								String l_cname = l_splitCont[0];
								
								if (l_splitCont[0].contains(" ")) {									
									l_cname = l_splitCont[0].replaceAll(" ", "_");
								}
								if (l_splitCont.length >= 2) {
									l_map.createContinent(l_cname, Integer.parseInt(l_splitCont[1]), l_map.getNumContinents() + 1);
								}
								break;
							case "Territories":
								if (l_line.length() == 0) {
									continue;
								}
								String l_splitTerr[] = l_line.split(",");
								String l_coname = l_splitTerr[0];
								String l_contName = l_splitTerr[3];
								if (l_contName.contains(" "))
									l_contName = l_contName.replaceAll(" ", "_");
								
								if (l_coname.contains(" "))
									l_coname = l_coname.replaceAll(" ", "_");
								if (l_splitTerr.length >= 4) {
									l_map.createTerritory(l_coname, l_splitTerr, l_map.getContinentByName(l_contName));
								}
							default:
								// Do nothing. We do not care about any other sections.
						}
					}
				}
			}
			l_reader.close();
			return l_map;
//			if (l_map.validateMap()) {
//				return l_map;
//			}
//			else {
//				return null;
//			}
		} 
		catch (FileNotFoundException | NumberFormatException l_exception) {
			return null;
		}
	}
}
