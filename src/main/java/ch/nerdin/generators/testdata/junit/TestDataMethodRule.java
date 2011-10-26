package ch.nerdin.generators.testdata.junit;

import ch.nerdin.generators.testdata.TestData;
import org.apache.commons.io.IOUtils;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This JUnit rule will ensure that when the test fail and then is rerun the same data is generated.
 * So that one can reproduce the failure.
 *
 * @author edewit
 */
public class TestDataMethodRule extends TestWatchman {

    @Override
    public void starting(FrameworkMethod method) {
        new SeedSaver(method.getMethod().getDeclaringClass().getName()).readSeed();
    }

    @Override
    public void failed(Throwable e, FrameworkMethod method) {
        new SeedSaver(method.getMethod().getDeclaringClass().getName()).writeSeed();
    }

    @Override
    public void succeeded(FrameworkMethod method) {
        new SeedSaver(method.getMethod().getDeclaringClass().getName()).deleteSeedFile();
    }
}
