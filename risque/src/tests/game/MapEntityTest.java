package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.MapEntity;

/**
 * Tests the {@link main.game.MapEntity} class.
 */
public class MapEntityTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testInstantiate() {
		MapEntity l_mapEntity = new MapEntity(null);
		assertNotNull(l_mapEntity);
	}
}
 