package nl.erikjan.generators.testdata;

import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class BeanBuilderTest {

    private BeanBuilder beanBuilder;

    @Before
    public void setup() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext-beangenerator.xml"});

        beanBuilder = (BeanBuilder) context.getBean("beanBuilder");

    }

    @Test
    public void shouldBuildBean() {
        Map<String, FieldProperty> fieldProperties = new HashMap<String, FieldProperty>();
        FieldProperty property = new FieldProperty();
        property.setMinLength(0);
        property.setMaxLength(40);
        property.setType(String.class);
        fieldProperties.put("firstName", property);

        Object result = beanBuilder.buildBean(Employee.class, fieldProperties);
        assertNotNull(result);
        assertTrue(result instanceof Employee);
        Employee employee = (Employee) result;
        assertNotNull(employee.getFirstName());
        int length = employee.getFirstName().length();
        assertTrue(length > 0 && length <= 40);
    }

    @Test
    public void shouldWorkForModelClassesWithoutDefaultConstructor() {
        NoDefaultConstructorModel model = beanBuilder.buildBean(NoDefaultConstructorModel.class, new HashMap<String, FieldProperty>());
        assertNotNull(model);
    }

    @Test
    public void shouldWorkForModelClassesWithFinalFields() throws Exception {
        HashMap<String, FieldProperty> fieldProperties = new HashMap<String, FieldProperty>();
        FieldProperty property = new FieldProperty();
        fieldProperties.put("fieldName", property);
        property.setType(String.class);
        FinalFieldModel model = beanBuilder.buildBean(FinalFieldModel.class, fieldProperties);
        assertNotNull(model);
        assertNotNull(model.getFieldName());
    }

    @Test
    public void shouldBeAbleToTellThatItCantCreateAProxyForFinalTypes() {
        assertFalse(beanBuilder.canProxyBean(String.class));
    }

    public static class NoDefaultConstructorModel {
        public NoDefaultConstructorModel(int param) {
        }
    }

}