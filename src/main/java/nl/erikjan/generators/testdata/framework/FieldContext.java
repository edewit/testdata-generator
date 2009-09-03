package nl.erikjan.generators.testdata.framework;

import org.apache.commons.chain.impl.ContextBase;

/**
 *
 * @author edewit
 */
public class FieldContext extends ContextBase {

    private FieldProperty fieldProperty;

    public FieldContext() {}

    public FieldContext(FieldProperty property) {
       this.fieldProperty = property;
    }

    public FieldProperty getFieldProperty() {
        return fieldProperty;
    }

    public void setFieldProperty(FieldProperty fieldProperty) {
        this.fieldProperty = fieldProperty;
    }
}
