package nl.erikjan.generators.testdata.inspector;

import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;

/**
 *
 */
public interface Inspector {

    Map<String, FieldProperty> inspect(Class<?> type);
}
