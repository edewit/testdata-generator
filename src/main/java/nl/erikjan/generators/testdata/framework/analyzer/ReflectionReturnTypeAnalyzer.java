package nl.erikjan.generators.testdata.framework.analyzer;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.stereotype.Component;

/**
 *
 * @author edewit
 */
@Component
public class ReflectionReturnTypeAnalyzer implements ReturnTypeAnalyzers {

    public Class<?> findClass(Method method) {
        Class<?> foundClass = null;
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType) {
            Type returnType = ((ParameterizedType) type).getActualTypeArguments()[0];
            foundClass = (Class<?>) returnType;
        } else if (!((Class<?>) type).isInterface()) {
            foundClass = (Class<?>) type;
        }

        return foundClass;
    }
}
