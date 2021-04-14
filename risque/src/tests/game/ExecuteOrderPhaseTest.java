package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.ExecuteOrderPhase;
import main.game.GameEngine;

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
	
	/**
	 * Test to check checkForWinner() function.
	 */
	@Test
	public void testCheckForWinner() {
		GameEngine l_game = new GameEngine();
		ExecuteOrderPhase l_executeOrder = new ExecuteOrderPhase(l_game);
		assertEquals(l_executeOrder.checkForWinner(), null);
	}	
	
}
   