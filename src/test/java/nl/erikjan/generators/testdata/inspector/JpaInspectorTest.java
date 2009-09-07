package nl.erikjan.generators.testdata.inspector;

import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.integration.Adres;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edewit
 */
public class JpaInspectorTest {

    @Test
    public void testInspect() {
        Class<?> type = Employee.class;
        JpaInspector instance = new JpaInspector();
        Map<String, FieldProperty> result = instance.inspect(type);

        assertNotNull(result);
        FieldProperty property = result.get("comment");
        assertNotNull(property);
        assertTrue(property.isLob());

        result = instance.inspect(Adres.class);
        assertNotNull(result);
        property = result.get("postalCode");
        assertNotNull(property);
        assertEquals(30, property.getMinLength());
        assertEquals(30, property.getMaxLength());
    }
}
