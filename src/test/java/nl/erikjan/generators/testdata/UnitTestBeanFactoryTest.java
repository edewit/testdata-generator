package nl.erikjan.generators.testdata;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import nl.erikjan.generators.testdata.framework.integration.ExampleWrappedClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * @author edewit
 */
public class UnitTestBeanFactoryTest {

    @Test
    public void testCreateService() {
        ExampleWrappedClass exampleWrappedClass = UnitTestBeanFactory.createService(ExampleWrappedClass.class);
        assertNotNull(exampleWrappedClass);
        List<Employee> employees = exampleWrappedClass.findByName("name");
        assertNotNull(employees);
        assertTrue(employees.size() >= 3 && employees.size() <= 5);
        assertTrue(employees instanceof LinkedList);
    }

    @Test
    public void testCreateBeanInstace() {
        Employee employee = UnitTestBeanFactory.createBeanInstance(Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getFirstName());
    }
}
