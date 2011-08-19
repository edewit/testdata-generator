package nl.erikjan.generators.testdata.inspector;

import java.lang.annotation.Annotation;
import javax.persistence.Column;
import javax.persistence.Lob;
import nl.erikjan.generators.testdata.framework.FieldProperty;

/**
 * 
 */
public class JpaInspector extends AbstractInspector {

    @Override
    void createFieldProperties(String fieldName, Annotation annotation) {
        FieldProperty property = fieldContext.getFieldProperty(fieldName);
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (annotationType.equals(Column.class)) {
            Column column = (Column) annotation;
            property.setMaxLength(column.length());
            property.setMinLength(column.length());
        }
        
        if (annotationType.equals(Lob.class)) {
            property.setLob(true);
        }
    }
}
