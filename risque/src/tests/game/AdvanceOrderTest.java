package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.AdvanceOrder;
import main.game.Territory;

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

  
	/**
	 * Basic test of the random casualty function.
	 */
	@Test
	public void testCasualtyCalculation() {
		int l_numRounds = 100;
		float l_pctKillChance = 0.5f;
		int l_maxCasualties = 50;
		AdvanceOrder l_advanceOrder = new AdvanceOrder(null, null, 0);
		// Try the function a thousand times. It's random, but ensure the number is between 0 and the max casualties or numRounds.
		for (int l_idx = 0; l_idx < 1000; l_idx++) {
			int l_result = l_advanceOrder.calcNumCasualties(l_numRounds, l_pctKillChance, l_maxCasualties);
			assertTrue(l_result <= l_maxCasualties);
			assertTrue(l_result <= l_numRounds);
			assertTrue(l_result >= 0);
		}
		// Ensure that a 0% chance of kill results in no deaths, and 100% chance of kill results in maximum/total casualties.
		int l_noKillResult = l_advanceOrder.calcNumCasualties(l_numRounds, 0.0f, l_maxCasualties);
		assertEquals(l_noKillResult, 0);
		int l_maxKillResult = l_advanceOrder.calcNumCasualties(l_numRounds, 1.0f, l_maxCasualties);
		assertEquals(l_maxKillResult, l_maxCasualties);
		int l_allKillResult = l_advanceOrder.calcNumCasualties(l_numRounds, 1.0f, l_numRounds * 1000);
		assertEquals(l_allKillResult, l_numRounds);
	}
	
	/**
	 * Basic test to check functioning of execute function.
	 */
	@Test
	public void testexecute() {
		Territory l_fromTerritory = new Territory("string",null);
		Territory l_toTerritory = new Territory("full",null);
		AdvanceOrder l_advanceOrder = new AdvanceOrder(l_fromTerritory, l_toTerritory , 0);
		assertEquals(l_advanceOrder.execute(), false); 
	}
}