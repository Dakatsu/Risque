package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.AdvanceOrder;

/**
 * Tests the {@link main.game.AdvanceOrder} class.
 */
public class AdvanceOrderTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testInstantiate() {
		int l_numArmiesAdvancing = 0;
		AdvanceOrder l_advanceOrder = new AdvanceOrder(null, null, l_numArmiesAdvancing);
		assertNotNull(l_advanceOrder);
	} 
}
  