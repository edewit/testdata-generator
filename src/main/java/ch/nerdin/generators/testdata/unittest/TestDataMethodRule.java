package ch.nerdin.generators.testdata.unittest;

import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

/**
 * This JUnit rule will ensure that when the test fail and then is rerun the same data is generated.
 * So that one can reproduce the failure.
 *
 * @author edewit
 */
public class TestDataMethodRule extends TestWatchman {

    @Override
    public void starting(FrameworkMethod method) {
<<<<<<< HEAD
        new SeedSaver(getSeedName(method)).readSeed();
=======
        new SeedSaver().readSeed();
>>>>>>> removed file name and added some javadoc
    }

    @Override
    public void failed(Throwable e, FrameworkMethod method) {
<<<<<<< HEAD
        new SeedSaver(getSeedName(method)).writeSeed();
=======
        new SeedSaver().writeSeed();
>>>>>>> removed file name and added some javadoc
    }

    @Override
    public void succeeded(FrameworkMethod method) {
<<<<<<< HEAD
        new SeedSaver(getSeedName(method)).deleteSeedFile();
    }

    private String getSeedName(FrameworkMethod method) {
        return method.getMethod().getDeclaringClass().getName();
=======
        new SeedSaver().deleteSeedFile();
>>>>>>> removed file name and added some javadoc
    }
}
