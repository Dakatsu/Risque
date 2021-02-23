package tests.game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContinentTest.class, TerritoryTest.class, MapTest.class, PlayerTest.class })
public class GameTestSuite {

}
