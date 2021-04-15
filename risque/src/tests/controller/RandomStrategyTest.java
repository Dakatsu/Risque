package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.RandomStrategy;
import main.game.Player;

/**
 * Tests the {@link main.controller.RandomStrategy} class.
 */
public class RandomStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class. 
	 */
	@Test
	public void testNotNull() {
		Player l_player = new Player("Test");
		RandomStrategy l_random = new RandomStrategy(l_player);
		assertNotNull(l_random);
	}

}
