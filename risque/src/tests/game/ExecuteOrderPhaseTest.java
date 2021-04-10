package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.AdvanceOrder;
import main.game.ExecuteOrderPhase;

/**
 * Tests the {@link main.game.ExecuteOrderPhase} class.
 */
public class ExecuteOrderPhaseTest {

	/**
	 * Simple test to check that object of class is not null.
	 */
	@Test
	public void testInstantiateExecuteOrder() {
		ExecuteOrderPhase l_executeOrder = new ExecuteOrderPhase(null);
		assertNotNull(l_executeOrder);
	}
}
   