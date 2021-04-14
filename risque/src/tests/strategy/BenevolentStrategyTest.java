package tests.strategy;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.strategy.BenevolentStrategy;

/**
 * Tests the {@link main.strategy.BenevolentStrategy} class.
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
