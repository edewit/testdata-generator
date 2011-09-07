package nl.erikjan.generators.testdata.util;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author edewit
 */
public class DataSetGeneratorTest {

    @Test
    public void testParseInputArguments() throws Exception {
        final String fileLocation = System.getProperty("java.io.tmpdir") + File.separator + "file.xml";
        DataSetGenerator.main(Employee.class.getName(), fileLocation);
        assertTrue(new File(fileLocation).exists());
    }

    @Test
    public void testGenerateDataSet() throws Exception {
        String xml = DataSetGenerator.generateDataSet(Employee.class);
        assertNotNull(xml);
        assertTrue(xml.contains("<dataset>"));
        assertTrue(xml.contains("<Employee"));
    }
}
