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
        DataSetGenerator.generateDataSet(Employee.class);
    }
}
