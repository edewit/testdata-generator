package ch.nerdin.generators.testdata.junit;

import org.testng.annotations.Test;

import java.io.IOException;

/**
 * TestNg version of the {@link TestDataRunnerTest} uses {@link java.util.ServiceLoader} so unlike the JUnit example
 * for testng you don't need to do anything.
 *
 * @author edewit
 */
public class TestNgDataTest extends AbstractTestFrameworkTester{

    @Test
    public void testRun() throws IOException {
        theTest();
    }
}
