package matt;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ SpringIntegrationTestDemoApplicationTests.class, 
				StringTransformerTest.class })

public class AllTests {

}
