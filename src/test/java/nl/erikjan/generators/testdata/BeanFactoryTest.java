package nl.erikjan.generators.testdata;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import nl.erikjan.generators.testdata.framework.integration.Manager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author edewit
 */
public class BeanFactoryTest {

    private BeanFactory factory;

    @Before
    public void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-beangenerator.xml");
        factory = (BeanFactory) context.getBean("beanFactory");
    }

    @Test
    public void testInstantiateBean() throws Exception {
        Employee employee = factory.instantiateBean(Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getFirstName());
        assertTrue(employee.getLastName().matches(Employee.LAST_NAME_PATTERN));

        Manager manager = factory.instantiateBean(Manager.class);
        assertNotNull(manager);
        assertNotNull(manager.getMemo());
        assertTrue(manager.getMemo().length() > 10);
    }
}
