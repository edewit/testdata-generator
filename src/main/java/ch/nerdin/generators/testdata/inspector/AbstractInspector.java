package ch.nerdin.generators.testdata.inspector;

import ch.nerdin.generators.testdata.framework.FieldProperty;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 */
public abstract class AbstractInspector implements Command {

    public static final String GET_METHOD_PREFIX = "get";
    private FieldContext fieldContext;

    public boolean execute(Context context) throws Exception {
        fieldContext = (FieldContext) context;
        Class<?> type = fieldContext.getInspectClass();
        Map<String, FieldProperty> properties = fieldContext.getInspectedFields();
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            fieldContext.getFieldProperty(field.getName()).setType(field.getType());
            addProperty(field.getName(), annotations);
        }

        Method[] methods = type.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (method.getName().startsWith(GET_METHOD_PREFIX)) {
                String name = StringUtils.uncapitalize(method.getName().substring(GET_METHOD_PREFIX.length()));
                if (!properties.containsKey(name)) {
                    fieldContext.getFieldProperty(name).setType(method.getReturnType());
                }

                addProperty(name, annotations);
            }
        }

        return false;
    }


    private void addProperty(String fieldName, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            createFieldProperties(fieldName, annotation);
        }
    }

    abstract void createFieldProperties(String fieldName, Annotation annotation);

    protected FieldContext getFieldContext() {
        return fieldContext;
    }
}
