package nl.erikjan.generators.testdata.inspector;

import java.lang.annotation.Annotation;
import javax.persistence.Column;
import javax.persistence.Lob;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import org.springframework.stereotype.Component;

/**
 * 
 */
@Component
public class JpaInspector extends AbstractInspector {

    @Override
    FieldProperty createFieldProperties(Annotation annotation) {
        FieldProperty property = new FieldProperty();
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (annotationType.equals(Column.class)) {
            Column column = (Column) annotation;
            property.setMaxLength(column.length());
            property.setMinLength(column.length());
        }
        
        if (annotationType.equals(Lob.class)) {
            property.setLob(true);
        }
        
//        if (annotationType.equals(Patt))

        return property;
    }

    @Override
    boolean handlesAnnotation(Annotation annotation) {
        return annotation.annotationType().getName().startsWith("javax.persistence");
    }
}
