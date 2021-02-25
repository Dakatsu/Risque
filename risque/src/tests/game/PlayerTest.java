package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Player;

/**
 * Tests the {@link main.game.Player} class.
 */
public class PlayerTest {

	/**
	 * Tests functions related to the player's name.
	 */
	@Test
	public void nameTest() {
		String l_testName = "Kyle";
		Player l_player = new Player(l_testName);
		assertEquals(l_player.getName(), l_testName);
		String l_newName = "Swaggins";
		l_player.setName(l_newName);
		assertEquals(l_player.getName(), l_newName);
	}
}
