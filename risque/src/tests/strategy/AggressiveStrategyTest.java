package tests.strategy;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.strategy.AggressiveStrategy;

/**
 * Tests the {@link main.strategy.AggressiveStrategy} class.
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
 