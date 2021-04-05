package tests.game;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import main.game.GameEngine;
import main.game.Map;

/**
 * Tests the {@link main.game.Map} class.
 */
public class MapTest {
	
	/**
	 * Reference to the game engine the map uses.
	 */
	GameEngine d_engine;
	
	/**
	 * Sets up the GameEngine before executing a test.
	 */
	@Before
	public void before() {
		d_engine = new GameEngine();
	}

	/**
	 * Tests the creation of an empty map.
	 */
	@Test
	public void emptyMapTest() {
		Map l_testMap = new Map();
		assertNotNull(l_testMap);
		assertEquals(0, l_testMap.getNumContinents());
		assertEquals(0, l_testMap.getNumTerritories());
		
		// This is not a valid map.
		assertFalse(l_testMap.validateMap());
	}
	
	
	/**
	 * Tests the functionality of a basic map with two connected territories on one continent.
	 */
	@Test
	public void twoTerritoryOneContinentMapTest() {
		Map l_testMap = new Map();
		assertTrue(l_testMap.createContinent(1, 5));
		assertTrue(l_testMap.createTerritory(1, 1));
		assertTrue(l_testMap.createTerritory(2, 1));
		l_testMap.addBorder(1, 2);
		
		// Ensure we are considered valid.
		assertTrue(l_testMap.validateMap());
		
		assertEquals(1, l_testMap.getNumContinents());
		assertEquals(2, l_testMap.getNumTerritories());
		
		// Check that the borders return the same results no matter which order they are called.
		assertTrue(l_testMap.doesBorderExist(1, 2));
		assertTrue(l_testMap.doesBorderExist(2, 1));
	}
	
	/**
	 * Tests the functionality of a basic map with two connected territories on two continents.
	 */
	@Test
	public void twoTerritoryTwoContinentMapTest() {
		Map l_testMap = new Map();
		assertTrue(l_testMap.createContinent(1, 5));
		assertTrue(l_testMap.createContinent(2, 5));
		assertTrue(l_testMap.createTerritory(1, 1));
		assertTrue(l_testMap.createTerritory(2, 2));
		l_testMap.addBorder(1, 2);
		
		// Ensure we are considered valid.
		assertTrue(l_testMap.validateMap());
		
		assertEquals(2, l_testMap.getNumContinents());
		assertEquals(2, l_testMap.getNumTerritories());
		
		// Check that the borders return the same results no matter which order they are called.
		assertTrue(l_testMap.doesBorderExist(1, 2));
		assertTrue(l_testMap.doesBorderExist(2, 1));
	}
	
	/**
	 * Tests the functionality of a basic map with four connected territories on two continents.
	 */
	@Test
	public void fourTerritoryTwoContinentMapTest() {
		Map l_testMap = new Map();
		assertTrue(l_testMap.createContinent(1, 5));
		assertTrue(l_testMap.createContinent(2, 5));
		assertTrue(l_testMap.createTerritory(1, 1));
		assertTrue(l_testMap.createTerritory(2, 1));
		assertTrue(l_testMap.createTerritory(3, 2));
		assertTrue(l_testMap.createTerritory(4, 2));
		l_testMap.addBorder(1, 2);
		l_testMap.addBorder(2, 3);
		l_testMap.addBorder(3, 4);
		l_testMap.addBorder(4, 1);
		
		// Ensure we are considered valid.
		assertTrue(l_testMap.validateMap());
		
		assertEquals(2, l_testMap.getNumContinents());
		assertEquals(4, l_testMap.getNumTerritories());
		
		// Check that the borders return the same results no matter which order they are called.
		assertTrue(l_testMap.doesBorderExist(1, 2));
		assertTrue(l_testMap.doesBorderExist(2, 1));
		assertTrue(l_testMap.doesBorderExist(3, 2));
		assertTrue(l_testMap.doesBorderExist(2, 3));
		assertTrue(l_testMap.doesBorderExist(3, 4));
		assertTrue(l_testMap.doesBorderExist(4, 3));
		assertTrue(l_testMap.doesBorderExist(4, 1));
		assertTrue(l_testMap.doesBorderExist(1, 4));
		
		// Check we do not have borders between territories we did not create.
		assertFalse(l_testMap.doesBorderExist(1, 3));
		assertFalse(l_testMap.doesBorderExist(3, 1));
		assertFalse(l_testMap.doesBorderExist(4, 2));
		assertFalse(l_testMap.doesBorderExist(2, 4));
		
		// Test deletions.
		l_testMap.deleteTerritory(4);
		assertEquals(2, l_testMap.getNumContinents());
		assertEquals(3, l_testMap.getNumTerritories());
		assertTrue(l_testMap.doesBorderExist(1, 2));
		assertTrue(l_testMap.doesBorderExist(2, 1));
		assertTrue(l_testMap.doesBorderExist(3, 2));
		assertTrue(l_testMap.doesBorderExist(2, 3));
		l_testMap.deleteContinent(2);
		assertEquals(1, l_testMap.getNumContinents());
		assertEquals(2, l_testMap.getNumTerritories());
		assertTrue(l_testMap.doesBorderExist(1, 2));
		assertTrue(l_testMap.doesBorderExist(2, 1));
	}
	
	/**
	 * Tests that an unconnected map is invalidated.
	 */
	@Test
	public void unconnectedMapTest() {
		Map l_testMap = new Map();
		assertTrue(l_testMap.createContinent(1, 5));
		assertTrue(l_testMap.createContinent(2, 5));
		assertTrue(l_testMap.createTerritory(1, 1));
		assertTrue(l_testMap.createTerritory(2, 1));
		assertTrue(l_testMap.createTerritory(3, 1));
		assertTrue(l_testMap.createTerritory(4, 2));
		assertTrue(l_testMap.createTerritory(5, 2));
		assertTrue(l_testMap.createTerritory(6, 2));
		l_testMap.addBorder(1, 2);
		l_testMap.addBorder(2, 3);
		l_testMap.addBorder(3, 1);
		l_testMap.addBorder(4, 5);
		l_testMap.addBorder(5, 6);
		l_testMap.addBorder(6, 4);
		
		// The map should be invalid; the continents are connected graphs individually but the map is not.
		assertFalse(l_testMap.validateMap());
		
		// The map should be invalid if a continent is not a connected graph, even if the map as a whole is.
		assertTrue(l_testMap.createTerritory(7, 2));
		l_testMap.addBorder(1, 7);
		l_testMap.addBorder(3, 4);
		assertFalse(l_testMap.validateMap());
	}
	
	/**
	 * Testing that saving a map and them loading it should create an identical map.
	 */
	@Test
	public void mapSavingLoadingTest() {
		Map l_savedMap = new Map();
		l_savedMap.setEngine(d_engine);
		assertTrue(l_savedMap.createContinent(1, 5));
		assertTrue(l_savedMap.createContinent(2, 5));
		assertTrue(l_savedMap.createTerritory(1, 1));
		assertTrue(l_savedMap.createTerritory(2, 1));
		assertTrue(l_savedMap.createTerritory(3, 2));
		assertTrue(l_savedMap.createTerritory(4, 2));
		l_savedMap.addBorder(1, 2);
		l_savedMap.addBorder(2, 3);
		l_savedMap.addBorder(3, 4);
		l_savedMap.addBorder(4, 1);
		l_savedMap.saveToFile("JUnitTest.map");
		
		Map l_loadedMap = new Map();
		l_loadedMap.setEngine(d_engine);
		l_loadedMap.loadFromFile("JUnitTest.map");
		
		assertEquals(l_savedMap.getNumContinents(), l_loadedMap.getNumContinents());
		assertEquals(l_savedMap.getNumTerritories(), l_loadedMap.getNumTerritories());
		
		for (int l_idx = 1; l_idx <= l_savedMap.getNumContinents(); l_idx++) {
			assertEquals(l_savedMap.getContinent(l_idx).getName(), l_loadedMap.getContinent(l_idx).getName());
			assertEquals(l_savedMap.getContinent(l_idx).getBonusArmies(), l_loadedMap.getContinent(l_idx).getBonusArmies());
		}
		
		for (int l_idx = 1; l_idx <= l_savedMap.getNumTerritories(); l_idx++) {
			assertEquals(l_savedMap.getTerritory(l_idx).getName(), l_loadedMap.getTerritory(l_idx).getName());
			assertEquals(l_savedMap.getContinentID(l_savedMap.getTerritory(l_idx).getContinent()), l_loadedMap.getContinentID(l_loadedMap.getTerritory(l_idx).getContinent()));
		}
		
		// TODO: Check that borders are the same.
	}
}
