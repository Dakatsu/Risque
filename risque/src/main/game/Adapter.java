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
	 */
	@Override
	public Map loadFromFile(File p_file) {
		return d_adp.loadFromFile(p_file);
	}


}
