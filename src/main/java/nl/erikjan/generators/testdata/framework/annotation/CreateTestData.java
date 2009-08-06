package nl.erikjan.generators.testdata.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Erik Jan de Wit
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface CreateTestData {
    int min() default 50;
    int max() default 50;
    Class<? extends Collection> collectionType() default ArrayList.class;
}
