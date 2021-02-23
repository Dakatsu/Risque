package tests.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite to run all JUnit tests for the {@link main.game} package.
 */
@RunWith(Suite.class)
@SuiteClasses({ ContinentTest.class, TerritoryTest.class, MapTest.class, PlayerTest.class, GameEngineTest.class })
public class GameTestSuite {
}
