package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.AirliftOrder;

/**
 * Tests the {@link main.game.AirliftOrder} class.
 */
public class AirliftOrderTest {

	/**
	 * Test to show that class can be instantiated.
	 */
	@Test
	public void classInstantiateTest() {
		AirliftOrder l_air = new AirliftOrder(null, null, 4);
		assertNotNull(l_air);
	}
	
}  
