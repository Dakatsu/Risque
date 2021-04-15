package tests.game;

import static org.junit.Assert.*;

import java.lang.ModuleLayer.Controller;

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
	 * Ensures the correct running of setMap().
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
	
	/**
	 * Ensures that there is no controller connected at start.
	 */
	@Test
	public void getControllerTest() {
		assertNotNull(d_engine);
		assertNull(d_engine.getController());
	}
	
	/**
	 * Ensures that there is no player at start.
	 */
	@Test
	public void getNumPlayersTest() {
		assertEquals(d_engine.getNumPlayers(), 0);
	}
	
	/**
	 * Tests addPlayer() function.
	 */
	@Test
	public void addPlayersTest() {
		assertEquals(d_engine.addPlayer("sajan"), true);
	}
	
	/**
	 * Tests removePlayer() function.
	 */
	@Test
	public void removePlayersTest() {
		assertEquals(d_engine.removePlayer("sajan"), false);
		d_engine.addPlayer("sajan");
		assertEquals(d_engine.removePlayer("sajan"), true);
	}
	
	/**
	 * Tests getPlayerByName() function.
	 */
	@Test
	public void getPlayerByNameTest() {
		d_engine.addPlayer("Mansajan");
		assertEquals(d_engine.getPlayerByName("Ronaldo"), null);
	}
	
	/**
	 * Tests getPlayerById() function.
	 */
	@Test
	public void getPlayerByIdTest() {
		assertNull(d_engine.getPlayerByID(1));
		d_engine.addPlayer("Mansajan");
		d_engine.addPlayer("Kyle");
		assertNotNull(d_engine.getPlayerByID(1));
		assertNotNull(d_engine.getPlayerByID(0));
	}
	
}
