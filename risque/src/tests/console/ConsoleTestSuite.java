package tests.console;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite to run all JUnit tests for the {@link main.console} package.
 */
@RunWith(Suite.class)
@SuiteClasses({ConsoleTest.class, InputHandlerTest.class})
public class ConsoleTestSuite {
}
