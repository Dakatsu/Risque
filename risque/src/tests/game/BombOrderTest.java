package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.BombOrder;
import main.game.Player;

/**
 * Tests the {@link main.game.BombOrder} class.
 */
public class BombOrderTest {

	/**
	 * Test to show that class can be instantiated.
	 */
	@Test
	public void classInstantiateTest() {
		BombOrder l_bomb = new BombOrder(null);
		assertNotNull(l_bomb);
	}
	
}
