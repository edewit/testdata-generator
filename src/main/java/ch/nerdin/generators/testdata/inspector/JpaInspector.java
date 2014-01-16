package ch.nerdin.generators.testdata.inspector;

import java.lang.annotation.Annotation;
import javax.persistence.Column;
import javax.persistence.Lob;
import ch.nerdin.generators.testdata.framework.FieldProperty;

/**
 * 
 */
public class JpaInspector extends AbstractInspector {

    @Override
    void createFieldProperties(String fieldName, Annotation annotation) {
        FieldProperty property = getFieldContext().getFieldProperty(fieldName);
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
