package tests.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.game.GameEngine;
import main.game.Map;

/**
 * Tests the {@link main.game.GameEngine} class.
 */
public class GameEngineTest {
	
	/**
	 * Reference to the game engine we're testing.
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
	 * Ensures that there is no map loaded at start.
	 */
	@Test
	public void getDefaultMap() {
		assertNotNull(d_engine);
		assertNull(d_engine.getMap());
	}

	/**
	 * Ensures that there is no map loaded at start.
	 */
	@Test
	public void addNewMap() {
		Map l_testMap = new Map();
		d_engine.setMap(l_testMap);
		assertNotNull(d_engine.getMap());
		assertEquals(l_testMap, d_engine.getMap());
	}
	
	/**
	 * Ensures that class can be instantiated.
	 */
	@Test
	public void testClassObjectNotEmpty() {
		assertNotNull(d_engine);
	}
}
