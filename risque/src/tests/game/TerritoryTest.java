package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Territory;

/**
 * Tests the {@link main.game.Territory} class.
 */
public class TerritoryTest {

	/**
	 * Tests functions related to the territory's name.
	 */
	@Test
	public void nameTest() {
		String l_testName = "Florida";
		Territory l_territory = new Territory(l_testName);
		assertEquals(l_territory.getName(), l_testName);
		String l_newName = "Quebec";
		l_territory.setName(l_newName);
		assertEquals(l_territory.getName(), l_newName);
	}

	/**
	 * Test to show that class can be instantiated.
	 */
	@Test
	public void territoryTest() {
		String l_string = "Ontario";
		Territory l_territory = new Territory(l_string, null);
		assertNotNull(l_territory);
	} 
}
