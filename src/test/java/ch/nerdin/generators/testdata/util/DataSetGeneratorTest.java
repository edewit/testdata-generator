package ch.nerdin.generators.testdata.util;

import ch.nerdin.generators.testdata.framework.integration.Employee;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * @author edewit
 */
public class DataSetGeneratorTest {

    @Test
    public void testParseInputArguments() throws Exception {
        final File fileLocation = File.createTempFile("file", ".xml");
        DataSetGenerator.main(Employee.class.getName(), fileLocation.getAbsolutePath());
        assertTrue(fileLocation.exists());
    }

    @Test
    public void testGenerateDataSet() throws Exception {
        String xml = DataSetGenerator.generateDataSet(Employee.class);
        assertNotNull(xml);
        assertTrue(xml.contains("<dataset>"));
        assertTrue(xml.contains("<Employee"));
    }
}
