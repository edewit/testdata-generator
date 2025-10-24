package ch.nerdin.generators.testdata.inspector;

import ch.nerdin.generators.testdata.framework.FieldProperty;
import org.apache.commons.chain.impl.ContextBase;

import java.util.Map;

/**
 * @author edewit
 */
public class FieldContext extends ContextBase {

    private final Map<String, FieldProperty> inspectedFields;
    private final Class<?> inspectClass;

    public FieldContext(Map<String, FieldProperty> inspectedFields, Class<?> inspectClass) {
        this.inspectedFields = inspectedFields;
        this.inspectClass = inspectClass;
    }

    public FieldProperty getFieldProperty(String field) {
        return inspectedFields.computeIfAbsent(field, k -> new FieldProperty());
    }

    public Map<String, FieldProperty> getInspectedFields() {
        return inspectedFields;
    }

    public Class<?> getInspectClass() {
        return inspectClass;
    }
}
