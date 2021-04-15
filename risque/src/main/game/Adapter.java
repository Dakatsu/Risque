package main.game;

import java.io.File;


/**
 * This class is used to implement Adapter Pattern
 *
 */
public class Adapter extends MapReaderWriter {
	Adaptee d_adp = new Adaptee();

	/**
	 * Constructor to initialize adaptee object
	 * 
	 * @param p_adp Object of adaptee class
	 */
	public Adapter(Adaptee p_adp) {
		this.d_adp = p_adp;
	}

	/**
	 * This method loads the map file 
	 * @param p_file The file to load the map from.
	 */
	@Override
	public Map loadFromFile(File p_file) {
		return d_adp.loadFromFile(p_file);
	}
	
	/**
	 * Saves the input map to a given file name. Overwrites any existing map with the same name.
	 * The map will only save if it is valid.
	 * @param p_map The map to save.
	 * @param p_fileName The name of the file to save to, including the extension.
	 * @return Whether the file was successfully saved.
	 */
	@Override
	public boolean saveToFile(Map p_map, String p_fileName) {
		return d_adp.saveToFile(p_map, p_fileName);
	}
}
