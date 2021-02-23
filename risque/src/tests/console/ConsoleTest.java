package tests.console;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.Console;

/**
 * Tests the {@link main.console.Console} class.
 */
public class ConsoleTest {

	/**
	 * Simple test to ensure we can instantiate this class. Placeholder until we have better tests.
	 */
	@Test
	public void testNotNull() {
		Console l_console = new Console(null, null);
		assertNotNull(l_console);
	}

}
