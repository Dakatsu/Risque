package main.game;

import java.io.File;


/**
 * This class is used to implement Adapter Pattern
 *
 */
public class Adapter extends MapReaderWriter{
	Adaptee d_adp = new Adaptee();

	/**
	 * Constructor to initialize adaptee object
	 * 
	 * @param adp Object of adaptee class
	 */
	public Adapter(Adaptee adp) {
		this.d_adp = adp;
	}

	/**
	 * This method loads the map file 
	 */
	@Override
	public Map LoadFromFile(File p_file) {
		return d_adp.LoadFromFile(p_file);
	}


}
