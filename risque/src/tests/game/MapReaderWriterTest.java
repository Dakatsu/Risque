package tests.game;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import main.game.GameEngine;
import main.game.Map;
import main.game.MapReaderWriter;

/**
 * 
 * Test for Reading and saving the map
 *
 */
public class MapReaderWriterTest {
	/**
	 * Reference to the game engine the map uses.
	 */
	GameEngine d_engine;
	
	/**
	 * Testing that saving a map and them loading it should create an identical map.
	 */
	@Test
	public void mapSavingLoadingTest() {
		Map l_mapToSave = new Map();
		l_mapToSave.setEngine(d_engine);
		assertTrue(l_mapToSave.createContinent(1, 5));
		assertTrue(l_mapToSave.createContinent(2, 5));
		assertTrue(l_mapToSave.createTerritory(1, 1));
		assertTrue(l_mapToSave.createTerritory(2, 1));
		assertTrue(l_mapToSave.createTerritory(3, 2));
		assertTrue(l_mapToSave.createTerritory(4, 2));
		l_mapToSave.addBorder(1, 2);
		l_mapToSave.addBorder(2, 3);
		l_mapToSave.addBorder(3, 4);
		l_mapToSave.addBorder(4, 1);
		Map.SaveToFile(l_mapToSave, "JUnitTest.map");
		
		// Test that the file was saved to a file.
		File l_savedFile = new File("JUnitTest.map");
		assertTrue(l_savedFile.exists());
		
		
		MapReaderWriter mrw = new MapReaderWriter();
		Map l_loadedMap = mrw.LoadFromFile(l_savedFile);
		assertNotNull(l_loadedMap);
		l_loadedMap.setEngine(d_engine);
		
		assertEquals(l_mapToSave.getNumContinents(), l_loadedMap.getNumContinents());
		assertEquals(l_mapToSave.getNumTerritories(), l_loadedMap.getNumTerritories());
		
		for (int l_idx = 1; l_idx <= l_mapToSave.getNumContinents(); l_idx++) {
			assertEquals(l_mapToSave.getContinent(l_idx).getName(), l_loadedMap.getContinent(l_idx).getName());
			assertEquals(l_mapToSave.getContinent(l_idx).getBonusArmies(), l_loadedMap.getContinent(l_idx).getBonusArmies());
		}
		
		for (int l_idx = 1; l_idx <= l_mapToSave.getNumTerritories(); l_idx++) {
			assertEquals(l_mapToSave.getTerritory(l_idx).getName(), l_loadedMap.getTerritory(l_idx).getName());
			assertEquals(l_mapToSave.getContinentID(l_mapToSave.getTerritory(l_idx).getContinent()), l_loadedMap.getContinentID(l_loadedMap.getTerritory(l_idx).getContinent()));
		}
		
		// TODO: Check that borders are the same.
	}
	
	/**
	 * Ensures that class can be instantiated.
	 */
	@Test
	public void testClassObjectNotEmpty() {
		MapReaderWriter mrw = new MapReaderWriter();
		assertNotNull(mrw);
	}
}

	
