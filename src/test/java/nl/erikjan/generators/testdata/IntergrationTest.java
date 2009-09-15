package nl.erikjan.generators.testdata;

import java.util.Collection;
import java.util.HashSet;
import nl.erikjan.generators.testdata.framework.annotation.CreateTestData;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class IntergrationTest {

    @Test
    public void shouldWorkBackToFront() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-beangenerator.xml");
        BeanFactory factory = (BeanFactory) context.getBean("beanFactory");
        Employee emp = (Employee) factory.instanciateBeans(getClass().getMethods()[2]);
        assertNotNull(emp);
        assertNull(emp.address);
        assertNotNull(emp.getAddress());
        Collection<Employee> result = (Collection<Employee>) factory.instanciateBeans(getClass().getMethods()[1]);
        assertNotNull(result);
        assertTrue(result.size() >= 10 && result.size() < 100);
        assertTrue(result instanceof HashSet);
    }

    @CreateTestData(collectionType = HashSet.class, min = 10, max = 100)
    public Collection<Employee> findAllEmployee() {
        return null;
    }

    public Employee findEmployeeById(Long id) {
        return null;
    }
}
