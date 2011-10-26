package ch.nerdin.generators.testdata.junit;

import ch.nerdin.generators.testdata.framework.integration.Employee;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static ch.nerdin.generators.testdata.TestData.createBeanInstance;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author edewit
 */
public abstract class AbstractTestFrameworkTester {

    public void theTest() throws IOException {
        final File file = getEmployeeSerializedFile();

        final Employee employee = createBeanInstance(Employee.class);
        if (file.exists()) {
            assertEquals(readObject(file), employee.getFirstName());
            getEmployeeSerializedFile().delete();
        } else {
            writeObject(employee, file);
            fail("test failed");
        }
    }

    protected File getEmployeeSerializedFile() {
        String tempDir = System.getProperty("java.io.tmpdir");
        return new File(tempDir, "employee.ser");
    }

    protected Object readObject(File file) throws IOException {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return IOUtils.readLines(inputStream).get(0);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    protected void writeObject(Employee employee, File file) throws IOException {
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            IOUtils.write(employee.getFirstName(), stream);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
