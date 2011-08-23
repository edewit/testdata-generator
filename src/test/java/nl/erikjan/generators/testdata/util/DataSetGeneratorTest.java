package nl.erikjan.generators.testdata.util;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author edewit
 */
public class DataSetGeneratorTest {

    @Test
    public void testParseInputArguments() throws Exception {

    }

    @Test
    public void testGenerateDataSet() throws Exception {
        String xml = DataSetGenerator.generateDataSet(Employee.class);
        assertNotNull(xml);
        assertTrue(xml.contains("<dataset>"));
        assertTrue(xml.contains("<Employee"));
    }
}
