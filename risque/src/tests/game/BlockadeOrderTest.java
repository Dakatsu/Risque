package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Adaptee;
import main.game.BlockadeOrder;

/**
 * Tests the {@link main.game.BlockadeOrder} class.
 */
public class BlockadeOrderTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testNotNull() {
		BlockadeOrder l_blockade = new BlockadeOrder(null);
		assertNotNull(l_blockade);
	}

}
