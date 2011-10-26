package ch.nerdin.generators.testdata.junit;

import ch.nerdin.generators.testdata.framework.integration.Employee;
import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static ch.nerdin.generators.testdata.TestData.createBeanInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * To really use this test you need to run it twice.
 * On the first run it will fail and save the seed on the second it will succeed and run use the saved seed
 * to regenerate the same data again and check that firstName of the employee was the same value as on the failed run.
 *
 * @author edewit
 */
public class TestDataRunnerTest {

    @Rule
    public TestDataMethodRule rule = new TestDataMethodRule();

    @Rule
    public MethodRule watchman = new TestWatchman() {
        @Override
        public void succeeded(FrameworkMethod method) {
            getEmployeeSerializedFile().delete();
        }
    };

    @Test
    public void testRun() throws IOException {
        final File file = getEmployeeSerializedFile();

        final Employee employee = createBeanInstance(Employee.class);
        if (file.exists()) {
            assertEquals(readObject(file), employee.getFirstName());
        } else {
            writeObject(employee, file);
            fail("test failed");
        }
    }

    private File getEmployeeSerializedFile() {
        String tempDir = System.getProperty("java.io.tmpdir");
        return new File(tempDir, "employee.ser");
    }

    private Object readObject(File file) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return IOUtils.readLines(inputStream).get(0);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    private void writeObject(Employee employee, File file) throws IOException {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            IOUtils.write(employee.getFirstName(), stream);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
