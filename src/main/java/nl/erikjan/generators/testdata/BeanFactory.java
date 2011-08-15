package nl.erikjan.generators.testdata;

import java.lang.annotation.Annotation;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.analyzer.ReturnTypeAnalyzers;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import nl.erikjan.generators.testdata.framework.RandomUtil;
import nl.erikjan.generators.testdata.framework.annotation.CreateTestData;
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

    public Object instantiateBeans(Method method) throws InstantiationException, IllegalAccessException {
        Object createObject = null;

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            CreateTestData testData = getAnnotation(method);
            Collection collection = testData.collectionType().newInstance();
            int randomSize = RandomUtil.randomBetween(testData.min(), testData.max());
            for (int i = 0; i < randomSize; i++) {
                collection.add(instantiateBean(method));
            }
            createObject = collection;
        } else {
            createObject = instantiateBean(method);
        }

        return createObject;
    }

    public <T> T instantiateBean(Class<T> beanClass) {
        return beanBuilder.buildBean(beanClass, inspectBean(beanClass));
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

    private Object instantiateBean(Method method) {
        Object object = null;
        Class<?> returnType = findReturnType(method);
        if (returnType != null) {
            Map<String, FieldProperty> fieldProperties = inspectBean(returnType);
            object = beanBuilder.buildBean(returnType, fieldProperties);
        }
        return object;
    }

    Object proxyBean(Class<?> bean, String pattern) {
        return beanBuilder.proxyBean(bean, pattern);
    }

    private CreateTestData getAnnotation(Method method) {
        if (method.isAnnotationPresent(CreateTestData.class)) {
            return method.getAnnotation(CreateTestData.class);
        }

        return new CreateTestData() {

            public int min() {
                return 50;
            }

            public int max() {
                return 50;
            }

            public Class<? extends Collection> collectionType() {
                return ArrayList.class;
            }

            public Class<? extends Annotation> annotationType() {
                return CreateTestData.class;
            }
        };
    }
}
