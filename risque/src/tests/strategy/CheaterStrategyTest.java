package tests.strategy;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;
import main.strategy.CheaterStrategy;

/**
 * Tests the {@link main.strategy.CheaterStrategy} class.
 */
public class CheaterStrategyTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		CheaterStrategy l_cheater = new CheaterStrategy();
		assertNotNull(l_cheater);
	}

}
