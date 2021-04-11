package main.game;

import java.io.File;


/**
 * This class is used to implement Adapter Pattern
 *
 */
public class Adapter extends MapReaderWriter{
	Adaptee adp = new Adaptee();

	/**
	 * Constructor to initialize ConquestReaderWriter object
	 * 
	 * @param crw Object of ConquestReaderWriter class
	 */
	public Adapter(Adaptee adp) {
		this.adp = adp;
	}

	/**
	 * This method parses the map file in Conquest format
	 * 
	 * @param map  Map Object
	 * @param file Map file to be parsed
	 * @return 1 if successful else 0
	 */
	@Override
	public Map LoadFromFile(File p_file) {
		return adp.LoadFromFile(p_file);
	}


}
