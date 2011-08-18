package nl.erikjan.generators.testdata.inspector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import org.springframework.util.StringUtils;

/**
 * TODO use the chain magnetism here and use a list for the generators.
 * chain could be usefull here case all thise inspectors could add to the FieldProperties
 * But it make no sense for the generators because they can either generate a value or not.
 */
public abstract class AbstractInspector implements Inspector {


    public Map<String, FieldProperty> inspect(Class<?> type) {
        Map<String, FieldProperty> properties = new HashMap<String, FieldProperty>();

        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            addProperty(annotations, field.getType(), field.getName(), properties);
        }

        Method[] methods = type.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            String name = StringUtils.uncapitalize(method.getName().substring(3));
            addProperty(annotations, method.getReturnType(), name, properties);
        }

        return properties;
    }

    private void addProperty(Annotation[] annotations, Class<?> type, String name, Map<String, FieldProperty> properties) {
        for (Annotation annotation : annotations) {
            if (handlesAnnotation(annotation)) {
                FieldProperty property = createFieldProperties(annotation);
                property.setType(type);
                properties.put(name, property);
            }
        }
    }

    abstract FieldProperty createFieldProperties(Annotation annotation);

    abstract boolean handlesAnnotation(Annotation annotation);

}
