package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Continent;

/**
 * Tests the {@link main.game.Continent} class.
 */
public class ContinentTest {

	/**
	 * Tests getname() function.
	 */
	@Test
	public void getNameTest() { 
		String l_testName = "Ontario";
		Continent l_continent = new Continent(l_testName);
		assertEquals(l_continent.getName(), l_testName);
	}
		
	/**
	 * Tests setname() function.
	 */
	@Test
	public void setNameTest() {
		String l_newName = "Quebec";
		Continent l_continent = new Continent(l_newName);
		l_continent.setName(l_newName);
		assertEquals(l_continent.getName(), l_newName);
	}
	 
	/**
	 * Test getBonusArmies() function.
	 */
	@Test
	public void getBonusArmyTest() {
		int l_numBonusArmies = 5;
		Continent l_continent = new Continent("Testlandia", l_numBonusArmies);
		assertEquals(l_continent.getBonusArmies(), l_numBonusArmies);
	}
	
	/**
	 * Test setBonusArmies() function.
	 */
	@Test
	public void setBonusArmyTest() {
		int l_numBonusArmies = 3;
		Continent l_continent = new Continent("Testlandia", l_numBonusArmies);
		// Ensure we can set it to a new positive number.
		int l_newNumBonusArmies = 9;
		assertTrue(l_continent.setBonusArmies(l_newNumBonusArmies));
		assertEquals(l_continent.getBonusArmies(), l_newNumBonusArmies);
		// Ensure we cannot set it to negative. 
		int l_negativeBonusArmies = -69;
		assertFalse(l_continent.setBonusArmies(l_negativeBonusArmies));
		assertEquals(l_continent.getBonusArmies(), l_newNumBonusArmies);
	}
	
	/**
	 * Simple test to ensure we can instantiate this class.
	 */ 
	@Test
	public void testClassObjectNotNull() {
		int l_bonusArmy = 0;
		String l_name = "Mansajan";
		Continent l_continent = new Continent(l_name, l_bonusArmy);
		assertNotNull(l_continent);
	}
}
