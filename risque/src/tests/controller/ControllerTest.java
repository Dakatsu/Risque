package tests.controller;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import main.controller.Controller;

/**
 * Tests the {@link main.controller.Controller} class.
 */
public class ControllerTest {
	
	/**
	 * Simple test to ensure we can instantiate this class. Placeholder until we have better tests.
	 */
	@Test
	public void testNotNull() {
		Controller l_controller = new Controller(null);
		assertNotNull(l_controller);
	}
	
}
