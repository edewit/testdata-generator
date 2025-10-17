package ch.nerdin.generators.testdata;

import java.util.Collection;
import java.util.HashSet;
import ch.nerdin.generators.testdata.framework.annotation.CreateTestData;
import ch.nerdin.generators.testdata.framework.integration.Employee;
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
        assertNotNull(emp.getEmployeeType());

        Collection<Employee> result = (Collection<Employee>) factory.instantiateBeans(getClass().getMethod("findAllEmployee"));
        assertNotNull(result);
        assertTrue(result.size() >= 10 && result.size() < 20);
        assertTrue(result instanceof HashSet);
    }

    @Test
    public void shouldBuildBeanForClass() throws Exception {
        Employee employee = factory.instantiateBean(Employee.class);
        assertNotNull(employee);
        assertNotNull(employee.getAddress());
    }

    @Test
    public void shouldWorkForPrefixedFields() throws Exception {
        FinalFieldModel fieldModel = factory.instantiateBean(FinalFieldModel.class);
        assertNotNull(fieldModel);
        assertNotNull(fieldModel.getName());
        assertNotNull(fieldModel.getEmployees());
        assertNotNull(fieldModel.getEmployees().get(0).getLastName());
    }

    @CreateTestData(collectionType = HashSet.class, min = 10, max = 20)
    public Collection<Employee> findAllEmployee() {
        return null;
    }

    public Employee findEmployeeById(Long id) {
        return null;
    }
}
