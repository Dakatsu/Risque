package tests.game;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.game.GameEngine;

/**
 * Tests the {@link main.game.GameEngine} class.
 */
public class GameEngineTest {
	
	GameEngine d_engine;
	
	@Before
	public void before() {
		d_engine = new GameEngine();
		d_engine.start();
	}
	
	@After
	public void after() {
		d_engine.finishAndQuit();
	}

	/**
	 * Ensures that we always have a default blank map at start.
	 */
	@Test
	public void getDefaultMap() {
		assertNotNull(d_engine);
		assertNotNull(d_engine.getMap());
	}

}
