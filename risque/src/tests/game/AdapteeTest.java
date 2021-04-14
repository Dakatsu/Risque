package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Adaptee;

/**
 * Tests the {@link main.game.Adapter} class.
 */
public class AdapteeTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testAdaptee() {
		Adaptee l_adaptee = new Adaptee();
		assertNotNull(l_adaptee);
	}
	
}
