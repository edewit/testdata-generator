package ch.nerdin.generators.testdata;

import ch.nerdin.generators.testdata.framework.FieldProperty;
import ch.nerdin.generators.testdata.framework.RandomUtil;
import ch.nerdin.generators.testdata.framework.analyzer.ReturnTypeAnalyzers;
import ch.nerdin.generators.testdata.framework.annotation.CreateTestData;
import ch.nerdin.generators.testdata.framework.annotation.DefaultTestData;
import ch.nerdin.generators.testdata.inspector.FieldContext;
import ch.nerdin.generators.testdata.inspector.InspectionCatalog;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Gathers the meta data for the bean to be generated and then delegates the work to instantiate the bean
 * and set the values to the BeanBuilder.
 */
@Service
public class BeanFactory {

    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class.getName());
    private static final CreateTestData DEFAULT_DATA_SETTINGS = new DefaultTestData();
    private InspectionCatalog catalog;

    @Autowired
    private List<ReturnTypeAnalyzers> returnTypeAnalyzers;

    @Autowired
    private BeanBuilder beanBuilder;

    @Autowired
    private RandomUtil randomUtil;

    @PostConstruct
    public void init() {
        catalog = new InspectionCatalog();
        catalog.loadCatalog();
    }

    public Object instantiateBeans(Method method) {
        Object createObject;
        CreateTestData testData = getAnnotation(method);
        int randomSize = randomUtil.randomBetween(testData.min(), testData.max());

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            Collection collection = newInstance(testData.collectionType());
            for (int i = 0; i < randomSize; i++) {
                collection.add(instantiateBean(method));
            }

            createObject = collection;
        } else if (Map.class.isAssignableFrom(method.getReturnType())) {
            Map map = newInstance(testData.mapType());
            for (int i = 0; i < randomSize; i++) {
                Class<?>[] types = findReturnType(method);
                map.put(instantiateType(types[0]), instantiateType(types[1]));
            }
            createObject = map;
        } else {
            createObject = instantiateBean(method);
        }

        return createObject;
    }

    private Object instantiateType(Class<?> type) {
        return isComplexType(type) ? instantiateBean(type)
                : beanBuilder.instantiateValueForField(new FieldProperty(type));
    }

    private <T> T newInstance(Class<T> collectionType) {
        try {
            return collectionType.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("could not create new instance for collection '" + collectionType
                    + "' check you annotation settings", e);
        }
    }

    public <T> T instantiateBean(Class<T> beanClass) {
        if (!beanBuilder.canProxyBean(beanClass)) {
            return recurseComplexFields(beanClass);
        }

        return beanBuilder.buildBean(beanClass, inspectBean(beanClass));
    }

    private <T> T recurseComplexFields(Class<T> beanClass) {
        Map<String, FieldProperty> fieldProperties = inspectBean(beanClass);
        T bean = beanBuilder.buildBean(beanClass, fieldProperties);
        for (Map.Entry<String, FieldProperty> entry : fieldProperties.entrySet()) {
            Class<?> type = entry.getValue().getType();
            if (isComplexType(type)) {
                try {
                    PropertyDescriptor descriptor = PropertyUtils.getPropertyDescriptor(bean, entry.getKey());
                    beanBuilder.setFieldValue(bean, type, entry.getKey(), instantiateBeans(descriptor.getReadMethod()));
                } catch (Exception e) {
                    //TODO another...
                }
            }
        }
        return bean;
    }

    private boolean isComplexType(Class<?> type) {
        return !(type.getName().startsWith("java.lang") || type.isPrimitive() || type.isEnum());
    }

    private Map<String, FieldProperty> inspectBean(Class<?> type) {
        Map<String, FieldProperty> fieldProperties = new HashMap<String, FieldProperty>();
        Catalog generators = catalog.getCatalog();
        Command inspectorChain = generators.getCommand("InspectorChain");
        FieldContext context = new FieldContext(fieldProperties, type);
        try {
            inspectorChain.execute(context);
        } catch (Exception e) {
            logger.error("could not inspect bean because of unexpected exception", e);
        }

        return fieldProperties;
    }

    private Class<?>[] findReturnType(Method method) {
        Class<?>[] foundClass = null;
        Iterator<ReturnTypeAnalyzers> iter = returnTypeAnalyzers.iterator();
        while (foundClass == null && iter.hasNext()) {
            ReturnTypeAnalyzers analyzer = iter.next();
            foundClass = analyzer.findClass(method);
        }

        return foundClass;
    }

    private Object instantiateBean(Method method) {
        Object object = null;
        Class<?>[] returnType = findReturnType(method);
        if (returnType != null) {
            //TODO maps
            object = instantiateBean(returnType[0]);
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

        return DEFAULT_DATA_SETTINGS;
    }
}
