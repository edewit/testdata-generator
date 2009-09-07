package nl.erikjan.generators.testdata.inspector;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author edewit
 */
@Component
public class TypeInspector implements Inspector {

    public Map<String, FieldProperty> inspect(Class<?> type) {
        Map<String, FieldProperty> properties = new HashMap<String, FieldProperty>();

        Method[] methods = type.getMethods();

        for (Method method : methods) {
            if (method.getName().startsWith("get")) {
                FieldProperty property = new FieldProperty();
                property.setType(method.getReturnType());
                String name = StringUtils.uncapitalize(method.getName().substring(3));
                properties.put(name, property);
            }
        }

        return properties;
    }
}
