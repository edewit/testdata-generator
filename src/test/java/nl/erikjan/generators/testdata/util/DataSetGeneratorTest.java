package nl.erikjan.generators.testdata.util;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;

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
        System.out.println(xml);
    }
}
