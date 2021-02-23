package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.Map;

/**
 * Tests the {@link main.game.Map} class.
 */
public class MapTest {

	/**
	 * Tests the creation of an empty map.
	 */
	@Test
	public void emptyMapTest() {
		Map l_testMap = new Map();
		assertNotNull(l_testMap);
		assertEquals(0, l_testMap.getNumContinents());
		assertEquals(0, l_testMap.getNumTerritories());
	}

}
