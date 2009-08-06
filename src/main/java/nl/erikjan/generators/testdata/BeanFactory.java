package nl.erikjan.generators.testdata;

import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.analyzer.ReturnTypeAnalyzers;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nl.erikjan.generators.testdata.inspector.Inspector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BeanFactory {

    @Autowired
    private List<Inspector> inspectors;

    @Autowired
    private List<ReturnTypeAnalyzers> returnTypeAnalyzers;

    @Autowired
    private BeanBuilder beanBuilder;

    public Object instanciateBeans(Method method) {
        Object createObject = null;
        Object bean = instanciateBean(method);

        if (bean != null) {
            if (method.getReturnType().isAssignableFrom(Collection.class)) {
                //TODO read collecction size from annotation
            } else {
                createObject = bean;
            }
        }

        return createObject;
    }

    private Map<String, FieldProperty> inspectBean(Class<?> type) {
        Map<String, FieldProperty> fieldProperties = new HashMap<String, FieldProperty>();
        for (Inspector inspector : inspectors) {
            fieldProperties.putAll(inspector.inspect(type));
        }

        return fieldProperties;
    }

    private Class<?> findReturnType(Method method) {
        Class<?> foundClass = null;
        Iterator<ReturnTypeAnalyzers> iter = returnTypeAnalyzers.iterator();
        while (foundClass == null && iter.hasNext()) {
            ReturnTypeAnalyzers analyzer = iter.next();
            foundClass = analyzer.findClass(method);
        }

        return foundClass;
    }

    private Object instanciateBean(Method method) {
        Object object = null;
        Class<?> returnType = findReturnType(method);
        if (returnType != null) {
            Map<String, FieldProperty> fieldProperties = inspectBean(returnType);
            object = beanBuilder.buildBean(returnType, fieldProperties);
        }
        return object;
    }
}
