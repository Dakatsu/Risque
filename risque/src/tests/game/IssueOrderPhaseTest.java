package tests.game;

import static org.junit.Assert.*;

import org.junit.Test;

import main.game.IssueOrderPhase;

/**
 * Tests the {@link main.game.IssueOrderPhase} class.
 */
public class IssueOrderPhaseTest {

	/**
	 * test to check that object of class is not empty.
	 */
	@Test
	public void testIssueOrderPhaseNotEmpty() {
		IssueOrderPhase l_issueOrder = new IssueOrderPhase(null);
		assertNotNull(l_issueOrder);
	}
	
} 
