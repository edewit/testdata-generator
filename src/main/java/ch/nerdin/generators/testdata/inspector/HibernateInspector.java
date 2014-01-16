package ch.nerdin.generators.testdata.inspector;

import ch.nerdin.generators.testdata.framework.FieldProperty;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;

/**
 *
 * @author edewit
 */
public class HibernateInspector extends AbstractInspector {
    private final static String EMAIL_REGEX = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}";
    /** create a mastercard number */
    private final static String CREDIT_CARD_REGEX = "5[1-5][0-9]{14}";
    private final static String DIGITS_REGEX = "[0-9]*";

    @Override
    void createFieldProperties(String field, Annotation annotation) {
        FieldProperty property = getFieldContext().getFieldProperty(field);
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if (annotationType.equals(Length.class)) {
            Length length = (Length) annotation;
            property.setMaxLength(length.max());
            property.setMinLength(length.min());
        }
        if (annotationType.equals(Range.class)) {
            Range length = (Range) annotation;
            property.setMaxLength(length.max());
            property.setMinLength(length.min());
        }
        if (annotationType.equals(Min.class)) {
            Min min = (Min) annotation;
            property.setMinLength(min.value());
        }
        if (annotationType.equals(Max.class)) {
            Max max = (Max) annotation;
            property.setMaxLength(max.value());
        }
        if (annotationType.equals(Pattern.class)) {
            Pattern pattern = (Pattern) annotation;
            property.setRegex(pattern.regexp());
        }
        if (annotationType.equals(Email.class)) {
            property.setRegex(EMAIL_REGEX);
        }
        if (annotationType.equals(CreditCardNumber.class)) {
            property.setRegex(CREDIT_CARD_REGEX);
        }
        if (annotationType.equals(Digits.class)) {
            property.setRegex(DIGITS_REGEX);
        }
        if (annotationType.equals(Past.class)) {
            property.setPast(true);
        }
        if (annotationType.equals(Future.class)) {
            property.setFuture(true);
        }
    }
}
