package ch.nerdin.generators.testdata;

import ch.nerdin.generators.testdata.framework.integration.Employee;
import ch.nerdin.generators.testdata.framework.integration.Manager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

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
    }

    @Test
    public void testInstantiateAnotherBean() throws Exception {
        Manager manager = factory.instantiateBean(Manager.class);
        assertNotNull(manager);
        assertNotNull(manager.getMemo());
        assertTrue(manager.getMemo().length() > 10);
    }

    @Test
    @SuppressWarnings({"unchecked"})
    public void shouldBeAbleToInstantiateMap() throws Exception {
        Map<String, Employee> mapTest = (Map<String, Employee>) factory.instantiateBeans(getClass().getMethod("mapTest"));
        assertNotNull(mapTest);
        for (Map.Entry<String, Employee> entry : mapTest.entrySet()) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
        }
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public Map<String, Employee> mapTest() {
        return null;
    }
}
