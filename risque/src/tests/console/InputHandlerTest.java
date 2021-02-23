package tests.console;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.InputHandler;

/**
 * Tests the {@link main.console.InputHandler} class.
 */
public class InputHandlerTest {

	/**
	 * Simple test to ensure we can instantiate this class. Placeholder until we have better tests.
	 */
	@Test
	public void testNotNull() {
		InputHandler l_iHandler = new InputHandler(null);
		assertNotNull(l_iHandler);
	}

}
