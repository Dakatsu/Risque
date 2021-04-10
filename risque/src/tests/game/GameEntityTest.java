package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.DeployOrder;
import main.game.GameEntity;

/**
 * Tests the {@link main.game.GameEntity} class.
 */
public class GameEntityTest {

	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testGameEntityNotNull() {
		GameEntity l_GameEntity = new GameEntity(null);
		assertNotNull(l_GameEntity);
	}
}
 