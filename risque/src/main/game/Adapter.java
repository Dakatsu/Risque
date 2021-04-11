package main.game;

import java.io.File;


/**
 * This class is used to implement Adapter Pattern
 *
 */
public class Adapter extends MapReaderWriter{
	Adaptee adp = new Adaptee();

	/**
	 * Constructor to initialize adaptee object
	 * 
	 * @param adp Object of adaptee class
	 */
	public Adapter(Adaptee adp) {
		this.adp = adp;
	}

	/**
	 * This method loads the map file 
	 */
	@Override
	public Map LoadFromFile(File p_file) {
		return adp.LoadFromFile(p_file);
	}


}
