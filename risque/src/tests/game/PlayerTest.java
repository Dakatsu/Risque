package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Continent;
import main.game.Order;
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
	
	/**
	 * Tests ownsTerritory() function of Player class.
	 */
	@Test
	public void ownsTerritoryTest() {
		Territory l_territory = new Territory("Toronto");
		Territory l_territory2 = new Territory("Montreal");
		Player l_player = new Player("Europe");
		l_player.addOwnedTerritory(l_territory); 
		assertEquals(l_player.ownsTerritory(l_territory), true);
		assertEquals(l_player.ownsTerritory(l_territory2), false);
	}
	
	/**
	 * Tests addOwnedContinent() function of Player class.
	 */
	@Test
	public void addOwnedContinentTest() {
		Continent l_continent = new Continent("Asia");
		Player l_player = new Player("str");
		assertEquals(l_player.addOwnedContinent(l_continent), true);
		assertEquals(l_player.addOwnedContinent(l_continent), false);	
	}
	
	/**
	 * Tests removeOwnedContinent() function of Player class.
	 */
	@Test
	public void removeOwnedContinentTest() {
		Continent l_continent = new Continent("Asia");
		Player l_player = new Player("str");
		l_player.addOwnedContinent(l_continent);
		assertEquals(l_player.removeOwnedContinent(l_continent), true);
		assertEquals(l_player.removeOwnedContinent(l_continent), false);	
	}
	
	/**
	 * Tests ownsContinent() function of Player class.
	 */ 
	@Test
	public void ownsContinentTest() { 
		Continent l_continent = new Continent("Asia");
		Continent l_continent2 = new Continent("Asia");
		Player l_player = new Player("str");
		l_player.addOwnedContinent(l_continent);
		assertEquals(l_player.ownsContinent(l_continent), true);
		assertEquals(l_player.ownsContinent(l_continent2), false);	
	}
	
	/**
	 * Tests getNumUndeployedArmies() function of Player class.
	 */
	@Test
	public void getNumUndeployedArmiesTest() { 
		Player l_player = new Player("str");
		assertEquals(l_player.getNumUndeployedArmies(), 0);
	}
	
	/**
	 * Tests setNumUndeployedArmies() function of Player class.
	 */
	@Test
	public void setNumUndeployedArmiesTest() { 
		Player l_player = new Player("str");
		assertEquals(l_player.setNumUndeployedArmies(4), 4);
	}
	
	/**
	 * Tests addUndeployedArmies() function of Player class.
	 */ 
	@Test
	public void addUndeployedArmiesTest() { 
		Player l_player = new Player("str");
		l_player.setNumUndeployedArmies(4);
		l_player.addUndeployedArmies(5);
		assertEquals(l_player.getNumUndeployedArmies(), 9);
	}
	
	/**
	 * Tests removeUndeployedArmies() function of Player class.
	 */
	@Test
	public void removeUndeployedArmiesTest() { 
		Player l_player = new Player("str");
		l_player.setNumUndeployedArmies(4);
		assertEquals(l_player.removeUndeployedArmies(5), 4);
		l_player.setNumUndeployedArmies(4);
		assertEquals(l_player.removeUndeployedArmies(2), 2);
	}
	
	/**
	 * Tests addCard() function of Player class.
	 */
	@Test
	public void addCardTest() { 
		String l_card= "card";
		Player l_player = new Player("str");	
		assertEquals(l_player.addCard(l_card), true);
	}
	
	/**
	 * Tests removeCard() function of Player class.
	 */
	@Test
	public void removeCardTest() { 
		String l_card= "card";
		Player l_player = new Player("str");	
		l_player.addCard(l_card);
		assertEquals(l_player.removeCard(l_card), true);
	}
	 
	/**
	 * Tests hasCard() function of Player class.
	 */
	@Test
	public void hasCardTest() { 
		String l_card= "card";
		Player l_player = new Player("str");	
		l_player.addCard(l_card);
		assertEquals(l_player.hasCard(l_card), true);
	}
}

	