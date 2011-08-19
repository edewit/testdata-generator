package nl.erikjan.generators.testdata.inspector;

import java.util.HashMap;
import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class HibernateInspectorTest {

    @Test
    public void testInspect() throws Exception {
        Class<?> type = Employee.class;
        HibernateInspector instance = new HibernateInspector();
        FieldContext fieldContext = new FieldContext(new HashMap<String, FieldProperty>(), type);
        instance.execute(fieldContext);
        Map<String, FieldProperty> result = fieldContext.getInspectedFields();

        assertNotNull(result);
        FieldProperty property = result.get("firstName");
        assertNotNull(property);
        assertEquals(40, property.getMaxLength());
        assertEquals(0, property.getMinLength());

        property = result.get("lastName");
        assertNotNull(property);
        assertEquals("[A-Z]{1}[a-z]{5}", property.getRegex());
    }

}