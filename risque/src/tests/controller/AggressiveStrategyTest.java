package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.AggressiveStrategy;
import main.game.Player;

/**
 * Tests the {@link main.controller.AggressiveStrategy} class.
 */
public class AggressiveStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		Player l_player = new Player("Test");
		AggressiveStrategy l_agreesive = new AggressiveStrategy(l_player);
		assertNotNull(l_agreesive);
	}
	
}
 