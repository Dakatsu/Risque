package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Continent;
import main.game.DeployOrder;
import main.game.Territory;

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
	
	/**
	 * Test to check getNumArmies() function.
	 */ 
	@Test
	public void testGetNumArmies() {
		int l_numArmies = 4;
		DeployOrder l_deployOrder = new DeployOrder(null, l_numArmies);
		l_deployOrder.setNumArmies(l_numArmies);
		assertEquals(l_deployOrder.getNumArmies(),4);
	}
	
	/**
	 * Simple test to check setNumArmies() function.
	 */
	@Test
	public void testSetNumArmies() {
		int l_numArmies = 0;
		DeployOrder l_deployOrder = new DeployOrder(null, l_numArmies);
		assertEquals(l_deployOrder.setNumArmies(l_numArmies), false);
	}
}
  