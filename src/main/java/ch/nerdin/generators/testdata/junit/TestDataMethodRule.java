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
        File seedFile = getSeedFile(method);

        FileInputStream inputStream = null;
        if (seedFile.exists()) {
            try {
                inputStream = new FileInputStream(seedFile);
                final String seed = IOUtils.readLines(inputStream).get(0);
                TestData.setSeed(Long.valueOf(seed));
            } catch (IOException e) {
                throw new RuntimeException("could not recover seedFile", e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    @Override
    public void failed(Throwable e, FrameworkMethod method) {
        File seedFile = getSeedFile(method);

        if (!seedFile.exists()) {
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(seedFile);
                IOUtils.write(String.valueOf(TestData.getSeed()), output);
            } catch (IOException ex) {
                throw new RuntimeException("could not save current seed", ex);
            } finally {
                IOUtils.closeQuietly(output);
            }
        }
    }

    @Override
    public void succeeded(FrameworkMethod method) {
        getSeedFile(method).delete();
    }

    private File getSeedFile(FrameworkMethod method) {
        String tempDir = System.getProperty("java.io.tmpdir");
        return new File(tempDir, method.getMethod().getDeclaringClass().getName());
    }

}
