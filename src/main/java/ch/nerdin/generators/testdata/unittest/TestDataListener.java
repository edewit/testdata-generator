package ch.nerdin.generators.testdata.unittest;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * TestNG TestListener that will save the seed on failure and regenerate the same test data for as long as it fails.
 *
 * @author edewit
 */
public class TestDataListener extends TestListenerAdapter {

    @Override
    public void onTestStart(ITestResult tr) {
        new SeedSaver().readSeed();
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        new SeedSaver().writeSeed();
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        new SeedSaver().deleteSeedFile();
    }
}
