package tests.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.controller.BenevolentStrategy;

/**
 * Tests the {@link main.controller.BenevolentStrategy} class.
 */
public class BenevolentStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		BenevolentStrategy l_benevolent = new BenevolentStrategy();
		assertNotNull(l_benevolent);
	}

}
