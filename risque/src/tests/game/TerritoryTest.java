package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Continent;
import main.game.Territory;

/**
 * Tests the {@link main.game.Territory} class.
 */
public class TerritoryTest {

	/**
	 * Tests function getName() of Territory class.
	 */
	@Test
	public void getNameTest() {
		String l_testName = "Florida";
		Territory l_territory = new Territory(l_testName);
		assertEquals(l_territory.getName(), l_testName);
	} 
	
	/**
	 * Tests function setName() of Territory class.
	 */
	@Test
	public void setNameTest() {
		String l_newName = "Vancouver"; 
		Territory l_territory = new Territory(l_newName);
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
	
	/**
	 * Tests getDisplayName() function. 
	 */
	@Test
	public void getDisplayNameTest() { 
		String l_testName = "Van_couv_er";
		Territory l_territory = new Territory(l_testName);
		l_territory.getName();
		assertEquals(l_territory.getDisplayName(), "Van couv er");
	}
}

