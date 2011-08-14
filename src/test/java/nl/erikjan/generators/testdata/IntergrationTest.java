package nl.erikjan.generators.testdata;

import java.util.Collection;
import java.util.HashSet;
import nl.erikjan.generators.testdata.framework.annotation.CreateTestData;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
@SuppressWarnings({"ALL"})
public class IntergrationTest {

    private BeanFactory factory;

    @Before
    public void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-beangenerator.xml");
        factory = (BeanFactory) context.getBean("beanFactory");
    }

    @Test
    public void shouldWorkBackToFront() throws Exception {
        Employee emp = (Employee) factory.instantiateBeans(getClass().getMethod("findEmployeeById", Long.class));
        assertNotNull(emp);
        assertNull(emp.address);
        assertNotNull(emp.getAddress());
        Collection<Employee> result = (Collection<Employee>) factory.instantiateBeans(getClass().getMethod("findAllEmployee"));
        assertNotNull(result);
        assertTrue(result.size() >= 10 && result.size() < 100);
        assertTrue(result instanceof HashSet);
    }

    @Test
    public void shouldBuildBeanForClass() throws Exception {
        Employee employee = factory.instantiateBean(Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getAddress());
    }

    @CreateTestData(collectionType = HashSet.class, min = 10, max = 100)
    public Collection<Employee> findAllEmployee() {
        return null;
    }

    public Employee findEmployeeById(Long id) {
        return null;
    }
}
