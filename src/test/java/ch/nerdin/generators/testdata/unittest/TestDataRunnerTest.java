package ch.nerdin.generators.testdata.unittest;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

/**
 * To really use this test you need to run it twice.
 * On the first run it will fail and save the seed on the second it will succeed and run use the saved seed
 * to regenerate the same data again and check that firstName of the employee was the same value as on the failed run.
 *
 * @author edewit
 */
public class TestDataRunnerTest extends AbstractTestFrameworkTester {

    @Rule
    public TestDataMethodRule rule = new TestDataMethodRule();

    @Test
    @Ignore
    public void testRun() throws IOException {
        theTest();
    }


}
