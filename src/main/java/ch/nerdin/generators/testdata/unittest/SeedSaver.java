package ch.nerdin.generators.testdata.unittest;

import ch.nerdin.generators.testdata.TestData;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Seed Saver saves the seed of the random to a file in /tmp/testframwork.seed
 * @author edewit
 */
public class SeedSaver {

    /**
     * Read the seed file, if it exist and set it on the random so the same data is generated.
     */
    public void readSeed() {
        File seedFile = getSeedFile();

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

    public void writeSeed() {
        File seedFile = getSeedFile();

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

    public void deleteSeedFile() {
        getSeedFile().delete();
    }

    private File getSeedFile() {
        String tempDir = System.getProperty("java.io.tmpdir");
        return new File(tempDir, "testframework.seed");
    }

}
