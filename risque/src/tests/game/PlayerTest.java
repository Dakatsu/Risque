package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Player;
import main.game.Territory;

/**
 * Tests the {@link main.game.Player} class.
 */
public class PlayerTest {

	/**
	 * Tests getName() function of Player class.
	 */
	@Test
	public void getNameTest() {
		String l_testName = "Kyle";
		Player l_player = new Player(l_testName);
		assertEquals(l_player.getName(), l_testName);
	}
	
	/** 
	 * Tests setName() function of Player class.
	 */
	@Test
	public void setNameTest() {
		String l_newName = "Mansajan";
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
	
	/**
	 * Tests addOwnedTerritory() function of Player class.
	 */
	@Test
	public void addOwnedTerritoryTest() {
		Territory l_territory = new Territory("Ontario");
		Player l_player = new Player("Europe");
		assertEquals(l_player.addOwnedTerritory(l_territory), true);
		assertEquals(l_player.addOwnedTerritory(l_territory), false);	
	}
	
	/**
	 * Tests removeOwnedTerritory() function of Player class.
	 */
	@Test
	public void removeOwnedTerritoryTest() {
		Territory l_territory = new Territory("Vancouver");
		Player l_player = new Player("Europe");
		l_player.addOwnedTerritory(l_territory);
		assertEquals(l_player.removeOwnedTerritory(l_territory), true);
		assertEquals(l_player.removeOwnedTerritory(l_territory), false);	
	}
	
	/**
	 * Tests getNumTerritoriesOwned() function of Player class.
	 */
	@Test
	public void getNumTerritoriesOwnedTest() {
		Territory l_territory = new Territory("Vancouver");
		Territory l_territory2 = new Territory("Ontario");
		Player l_player = new Player("Europe");
		l_player.addOwnedTerritory(l_territory); 
		l_player.addOwnedTerritory(l_territory2);
		assertEquals(l_player.getNumTerritoriesOwned(), 2);	
	}
}
