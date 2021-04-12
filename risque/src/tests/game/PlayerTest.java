package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Player;

/**
 * Tests the {@link main.game.Player} class.
 */
public class PlayerTest {

	/**
	 * Tests getName() function of Player class.
	 */
	@Test
	public void getNameTest() {
		String l_testName = "Funny";
		Player l_player = new Player(l_testName);
		assertEquals(l_player.getName(), l_testName);
	}
	
	/** 
	 * Tests setName() function of Player class.
	 */
	@Test
	public void setNameTest() {
		String l_newName = "Sir";
		Player l_player = new Player(l_newName);
		l_player.setName(l_newName);
		assertEquals(l_player.getName(), l_newName);
	}
	
	/**
	 * Test to show that class can be instantiated.
	 */
	@Test
	public void classInstantiateTest() {
		String l_name = "Sajan";
		Player l_player = new Player(l_name);
		assertNotNull(l_player);
	}
}
