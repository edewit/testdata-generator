package ch.nerdin.generators.testdata.inspector;

import java.util.HashMap;
import java.util.Map;
import ch.nerdin.generators.testdata.framework.FieldProperty;
import ch.nerdin.generators.testdata.framework.integration.Address;
import ch.nerdin.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edewit
 */
public class JpaInspectorTest {

    @Test
    public void testInspect() throws Exception {
        Class<?> type = Employee.class;
        JpaInspector instance = new JpaInspector();
        FieldContext fieldContext = new FieldContext(new HashMap<String, FieldProperty>(), type);
        instance.execute(fieldContext);
        Map<String, FieldProperty> result = fieldContext.getInspectedFields();

        assertNotNull(result);
        FieldProperty property = result.get("comment");
        assertNotNull(property);
        assertTrue(property.isLob());

        fieldContext = new FieldContext(new HashMap<String, FieldProperty>(), Address.class);
        instance.execute(fieldContext);
        result = fieldContext.getInspectedFields();
        assertNotNull(result);
        property = result.get("postalCode");
        assertNotNull(property);
        assertEquals(30, property.getMinLength());
        assertEquals(30, property.getMaxLength());
    }
}
