package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.BenevolentStrategy;
import main.game.Player;

/**
 * Tests the {@link main.controller.BenevolentStrategy} class.
 */
public class BenevolentStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		Player l_player = new Player("Test");
		BenevolentStrategy l_benevolent = new BenevolentStrategy(l_player);
		assertNotNull(l_benevolent);
	}

}
