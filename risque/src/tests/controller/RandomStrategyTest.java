package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.RandomStrategy;

/**
 * Tests the {@link main.controller.RandomStrategy} class.
 */
public class RandomStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class. 
	 */
	@Test
	public void testNotNull() {
		RandomStrategy l_random = new RandomStrategy();
		assertNotNull(l_random);
	}

}
