package nl.erikjan.generators.testdata;

import java.util.HashMap;
import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

/**
 *
 */
public class BeanBuilderTest {

    @Test
    public void shouldBuildBean() {
        Map<String, FieldProperty> fieldProperties = new HashMap<String, FieldProperty>();
        FieldProperty property = new FieldProperty();
        property.setMinLength(0);
        property.setMaxLength(40);
        property.setType(String.class);
        fieldProperties.put("firstName", property);

        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext-beangenerator.xml"});

        BeanBuilder instance = (BeanBuilder) context.getBean("beanBuilder");
        Object result = instance.buildBean(Employee.class, fieldProperties);
        assertNotNull(result);
        assertTrue(result instanceof Employee);
        Employee employee = (Employee) result;
        assertNotNull(employee.getFirstName());
        int length = employee.getFirstName().length();
        assertTrue(length > 0 && length <= 40);
    }
}