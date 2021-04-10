package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.DeployOrder;

/**
 * Tests the {@link main.game.DeployOrder} class.
 */
public class DeployOrderTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testInstantiateDeployOrder() {
		int l_numArmies = 0;
		DeployOrder l_deployOrder = new DeployOrder(null, l_numArmies);
		assertNotNull(l_deployOrder);
	}
}
  