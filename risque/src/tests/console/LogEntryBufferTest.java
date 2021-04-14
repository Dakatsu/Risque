package tests.console;

import static org.junit.Assert.*;

import org.junit.Test;

import main.console.LogEntryBuffer;


public class LogEntryBufferTest {

	/**
	 * Simple test to ensure that we can instantiate this class.
	 */
	@Test
	public void testInstantiate() {
		LogEntryBuffer l_logEntry = new LogEntryBuffer(null);
		assertNotNull(l_logEntry);
	}
	
}
