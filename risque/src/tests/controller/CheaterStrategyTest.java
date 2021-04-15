package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.CheaterStrategy;
import main.game.Player;

/**
 * Tests the {@link main.controller.CheaterStrategy} class.
 */
public class CheaterStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		Player l_player = new Player("Test");
		CheaterStrategy l_cheater = new CheaterStrategy(l_player);
		assertNotNull(l_cheater);
	}

}
