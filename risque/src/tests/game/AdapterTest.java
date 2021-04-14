package tests.game;
import static org.junit.Assert.*;
import org.junit.Test;

import main.game.Adaptee;
import main.game.Adapter;

/**
 * Tests the {@link main.game.Adapter} class.
 */
public class AdapterTest {
	
	/**
	 * Simple test to ensure we can instantiate this class.
	 */
	@Test
	public void testInstantiate() {
		Adaptee l_adaptee = new Adaptee();
		Adapter l_adapter = new Adapter(l_adaptee);
		assertNotNull(l_adapter);
	}
	
}
