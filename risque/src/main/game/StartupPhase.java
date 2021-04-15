package main.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents the start of the game, before gameplay has begun.
 *
 */
public class StartupPhase extends Phase {

	/**
	 * Auto-generated constructor stub.
	 * @param p_engine The game engine context.
	 */
	StartupPhase(GameEngine p_engine) {
		super(p_engine);
	}
	
	/**
	 * Loads a map, replacing the current one.
	 * @param p_mapName The name of the map to be loaded.
	 */
	@Override
	public void loadMap(String p_mapName) {
		File l_file = new File(p_mapName);
		if (l_file.exists()) {
			boolean l_shouldUseConquestAdapter = false;
			// If the map begins with [map], assume it's a conquest map file and load the proper reader instead.
			try {
				BufferedReader l_bufferedReader = new BufferedReader(new FileReader(l_file));
				String firstLine = l_bufferedReader.readLine();
				if (firstLine.startsWith("[")) {
					l_shouldUseConquestAdapter = true;
				}
				l_bufferedReader.close();
			}
			catch (IOException e) {
				// Do nothing.
			}
			MapReaderWriter l_mapReader = l_shouldUseConquestAdapter ?  new Adapter(new Adaptee()) : new MapReaderWriter();
			Map l_map = d_engine.onCreateEntity(l_mapReader.loadFromFile(l_file));
			// If we have a map, tell the game engine.
			if (l_map != null) {
				d_engine.setMap(l_map);
				d_engine.broadcastMessage((l_shouldUseConquestAdapter ? "Conquest" : "Domination") + " map \"" + p_mapName + "\" successfully loaded!");
			} 
			else {
				d_engine.broadcastMessage("The file \"" + p_mapName + "\" is not a valid map file.");
			}
		} 
		else {
			d_engine.broadcastMessage("The file \"" + p_mapName + "\" could not be loaded. Please check the file name.");
		}
	}
	
	/**
	 * Saves a map to a file name.
	 * @param p_mapName The name for the new map.
	 * @param p_saveAsConquest Should the map be saved using the conquest map adapter?
	 */
	@Override
	public void saveMap(String p_mapName, boolean p_saveAsConquest) {
		Map l_map = d_engine.getMap();
		if (l_map != null) {
			if (l_map.validateMap()) {
				MapReaderWriter l_mapReader = p_saveAsConquest ?  new Adapter(new Adaptee()) : new MapReaderWriter();
				l_mapReader.saveToFile(d_engine.getMap(), p_mapName);
				d_engine.broadcastMessage("Map saved to: " + p_mapName);
			}
			else {
				d_engine.broadcastMessage("The map must be valid to save it.");
			}
		}
		else {
			d_engine.broadcastMessage("No map is loaded.");
		}
	}
	
	/**
	 * Prints the map file to the screen.
	 */
	@Override
	public void showMap() {
		if (d_engine.getMap() != null) {
			d_engine.broadcastMessage(d_engine.getMap().toText());
		}
		else {
			d_engine.broadcastMessage("There is no map to show.");
		}
	}
	
	@Override
	public void addContinent(int p_cID, int p_cValue) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().createContinent(p_cID, p_cValue)) {
			d_engine.broadcastMessage("New continent created. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			d_engine.broadcastMessage("The continent could not be created.");
		}
	}
	
	/**
	 * Opens the map for editing, or creates a new one if it does not exist.
	 * Note that while a new map will be created if one does not exist, it will not be saved to that file.
	 * The saveMap command must be used to save a map after editing.
	 * @param p_mapName The name of the map to edit. It is created if it does not exist.
	 */
	@Override
	public void editMap(String p_mapName) {
		boolean l_shouldCreateNewMap = true;
		File l_file = new File(p_mapName);
		if (l_file.exists()) {
			boolean l_shouldUseConquestAdapter = false;
			// If the map begins with [map], assume it's a conquest map file and load the proper reader instead.
			try {
				BufferedReader l_bufferedReader = new BufferedReader(new FileReader(l_file));
				String firstLine = l_bufferedReader.readLine();
				if (firstLine.startsWith("[")) {
					l_shouldUseConquestAdapter = true;
				}
				l_bufferedReader.close();
			}
			catch (IOException e) {
				// Do nothing.
			}
			MapReaderWriter l_mapReader = l_shouldUseConquestAdapter ?  new Adapter(new Adaptee()) : new MapReaderWriter();
			Map l_map = d_engine.onCreateEntity(l_mapReader.loadFromFile(l_file));
			// If we have a map, tell the game engine.
			if (l_map != null) {
				d_engine.setMap(l_map);
				d_engine.broadcastMessage((l_shouldUseConquestAdapter ? "Conquest" : "Domination") + " map \"" + p_mapName + "\" already exists and was successfully loaded!");
				l_shouldCreateNewMap = false;
			} 
		}
		if (l_shouldCreateNewMap) {
			Map l_map = d_engine.onCreateEntity(new Map());
			d_engine.setMap(l_map);
			d_engine.broadcastMessage("A blank map has been created for editing.");
		}
	}
	
	@Override
	public void removeContinent(int p_cID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().deleteContinent(p_cID)) {
			d_engine.broadcastMessage("Continent removed. The number of continents is now " + d_engine.getMap().getNumContinents());
		}
		else {
			d_engine.broadcastMessage("No continents were removed. Please ensure a valid ID was entered.");
		}
	}
	
	@Override
	public void addTerritory(int p_tID, int p_cID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().createTerritory(p_tID, p_cID)) {
			d_engine.broadcastMessage("New territory created. The number of territories is now " + d_engine.getMap().getNumTerritories());
		}
		else {
			d_engine.broadcastMessage("Territory was not added. Please check your parameters.");
		}
	}
	
	@Override
	public void removeTerritory(int p_tID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().deleteTerritory(p_tID)) {
			d_engine.broadcastMessage("The territory was removed.");
		}
		else {
			d_engine.broadcastMessage("Territory was not added. Please check the input ID.");
		}
	}
	
	@Override
	public void addNeighbours(int p_firstID, int p_secondID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().addBorder(p_firstID, p_secondID)) {
			d_engine.broadcastMessage("Neighbours successfuly linked.");
		}
		else {
			d_engine.broadcastMessage("Invalid IDs or the territories were already neighbours.");
		}
	}
	
	@Override
	public void removeNeighbours(int p_firstID, int p_secondID) {
		if (d_engine.getMap() == null) {
			d_engine.broadcastMessage("That command cannot be used without a loaded map.");
		}
		else if (d_engine.getMap().deleteBorder(p_firstID, p_secondID)) {
			d_engine.broadcastMessage("Territories are no longer neighbours.");
		}
		else {
			d_engine.broadcastMessage("Invalid IDs or the territories were not neighbours.");
		}
	}
	
	@Override
	public void assignTerritories() {
		// Print out an error message if we cannot start the game.
		if (d_engine.getMap() == null || !d_engine.getMap().validateMap()) {
			d_engine.broadcastMessage("Error: a valid map must be loaded.");
		}
		else if (d_engine.getNumPlayers() < 2) {
			d_engine.broadcastMessage("Error: cannot start a game with just one player.");
		}
		else if (d_engine.getMap().getNumTerritories() < d_engine.getNumPlayers()) {
			d_engine.broadcastMessage("Error: there must be at least one territory per player.");
		}
		// Otherwise, divvy up the territories between the players.
		// TODO: Make this actually random and have some balance calculations.
		for (int l_idx = 1; l_idx <= d_engine.getMap().getNumTerritories(); l_idx++) {
			d_engine.changeTerritoryOwner(d_engine.getMap().getTerritory(l_idx), d_engine.getPlayerByID(l_idx % d_engine.getNumPlayers()));
		}
		d_engine.broadcastMessage("Territories have been assigned and the game has started. Good luck!");
		d_engine.setPhase(new IssueOrderPhase(this.d_engine));
	}
}
