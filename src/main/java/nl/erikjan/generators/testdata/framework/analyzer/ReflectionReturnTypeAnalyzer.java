package nl.erikjan.generators.testdata.framework.analyzer;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author edewit
 */
@Component
public class ReflectionReturnTypeAnalyzer implements ReturnTypeAnalyzers {

    public Class<?>[] findClass(Method method) {
        Class<?>[] foundClass = null;
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            List<Type> result = Arrays.asList(((ParameterizedType) type).getActualTypeArguments());
            foundClass = result.toArray(new Class<?>[result.size()]);
        } else if (!((Class<?>) type).isInterface()) {
            foundClass = new Class<?>[] {(Class<?>) type };
        }

        return foundClass;
    }
}
