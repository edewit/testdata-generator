package nl.erikjan.generators.testdata.inspector;

import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class HibernateInspectorTest {

    @Test
    public void testInspect() {
        Class<?> type = Employee.class;
        HibernateInspector instance = new HibernateInspector();
        Map<String, FieldProperty> result = instance.inspect(type);

        assertNotNull(result);
        FieldProperty property = result.get("firstName");
        assertNotNull(property);
        assertEquals(40, property.getMaxLength());
        assertEquals(0, property.getMinLength());

        property = result.get("lastName");
        assertNotNull(property);
        assertEquals("[A-Z]{1}[a-z]*", property.getRegex());
    }

}