package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.AggressiveStrategy;

/**
 * Tests the {@link main.controller.AggressiveStrategy} class.
 */
public class AggressiveStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		AggressiveStrategy l_agreesive = new AggressiveStrategy();
		assertNotNull(l_agreesive);
	}
	
}
 