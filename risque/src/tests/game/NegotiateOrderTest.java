package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Adaptee;
import main.game.NegotiateOrder;

/**
 * Tests the {@link main.game.NegotiateOrder} class.
 */
public class NegotiateOrderTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		NegotiateOrder l_negotiate = new NegotiateOrder(null);
		assertNotNull(l_negotiate);
	}

}
 