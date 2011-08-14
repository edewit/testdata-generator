package nl.erikjan.generators.testdata;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import nl.erikjan.generators.testdata.framework.integration.ServiceDaoTest;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * @author edewit
 */
public class UnitTestBeanFactoryTest {
    @Test
    public void testCreateService() throws Exception {
        ServiceDaoTest serviceTest = UnitTestBeanFactory.createService(ServiceDaoTest.class);
        assertNotNull(serviceTest);
        Employee employee = serviceTest.findEmpoyeeById(0L);
        assertNotNull(employee);
    }
}
