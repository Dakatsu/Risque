package main.console;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.game.GameEngine;
import main.game.GameObserver;

/**
 * An observer that exports messages to a log file.
 * @author Kyle
 *
 */
public class LogEntryBuffer implements GameObserver {
	/**
	 * The game engine we are observing.
	 */
	private GameEngine d_engine;
	
	/**
	 * The log file we would like to write messages to.
	 */
	private String d_fileName;
	
	/**
	 * Our writer to the log file.
	 */
	private FileWriter d_fileWriter;
	
	/**
	 * Default constructor: register us with the game engine.
	 * @param p_engine The game engine.
	 */
	public LogEntryBuffer(GameEngine p_engine) {
		if (p_engine != null) {
			setEngine(p_engine);
			getEngine().addObserver(this);
		}
		createAndOpenLogFile();
	}
	
	/**
	 * Gets the game engine currently connected to this buffer.
	 * @return the current game engine.
	 */
	public GameEngine getEngine() {
		return d_engine;
	}
	
	/**
	 * Sets the game engine.
	 * @param p_engine the new game engine
	 */
	public void setEngine(GameEngine p_engine) {
		d_engine = p_engine;
	}
	
	/**
	 * Creates a new log file with an auto-generated name and opens our file writer to edit it.
	 */
	public void createAndOpenLogFile() {
		try {
			Date l_date = new Date();
			SimpleDateFormat l_formatter = new SimpleDateFormat("yyyyMMdd-HHmmss");
			d_fileName = "Risque-" + l_formatter.format(l_date) + ".log";
			d_fileWriter = new FileWriter(d_fileName, true);
		}
		catch (IOException l_exception) {
			// Silently remove us as an observer if we fail to open the writer.
			if (getEngine() != null) {
				getEngine().removeObserver(this);
			}
		}
	}

	/**
	 * Writes a new message to the log.
	 */
	@Override
	public void onAddMessage(String p_message) {
		if (d_fileWriter != null) {
			try {
				d_fileWriter.write(p_message + '\n');
			} catch (IOException l_exception) {
				// Silently remove us as an observer if we get an exception.
				if (getEngine() != null) {
					getEngine().removeObserver(this);
				}
			}
		}
	}
	
	/**
	 * Close the log file upon quitting.
	 */
	@Override
	public void onQuit() {
		try {
			if (d_fileWriter != null) {
				d_fileWriter.close();
			}
		} 
		catch (IOException l_exception) {
			// Do nothing. We're exiting the program anyway.
		}
	}
}
