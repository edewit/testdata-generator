package ch.nerdin.generators.testdata.unittest;

import java.io.IOException;

/**
 * TestNg version of the {@link TestDataRunnerTest} uses {@link java.util.ServiceLoader} so unlike the JUnit example
 * for testng you don't need to do anything.
 *
 * @author edewit
 */
public class TestNgDataTest extends AbstractTestFrameworkTester{

    @Test(enabled = false)
    public void testRun() throws IOException {
        theTest();
    }
}
