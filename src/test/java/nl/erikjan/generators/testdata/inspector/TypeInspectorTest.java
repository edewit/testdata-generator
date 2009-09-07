package nl.erikjan.generators.testdata.inspector;

import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edewit
 */
public class TypeInspectorTest {

    @Test
    public void testInspect() {
        Class<?> type = Employee.class;
        TypeInspector instance = new TypeInspector();
        Map<String, FieldProperty> result = instance.inspect(type);

        assertNotNull(result);
        FieldProperty property = result.get("firstName");
        assertNotNull(property);
        assertEquals(String.class, property.getType());
    }
}
