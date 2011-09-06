package nl.erikjan.generators.testdata;

import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.Generator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class BeanBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BeanBuilder.class.getName());

    @Autowired
    private Interceptor interceptor;

    @Autowired
    private List<Generator> generators;
    private Objenesis objenesis;

    @PostConstruct
    public void init() {
        objenesis = new ObjenesisStd(true);
    }

    public <T> T buildBean(Class<T> type, Map<String, FieldProperty> fieldProperties) {
        T bean = instantiateBean(type);
        setFieldValues(fieldProperties, bean);
        return bean;
    }

    @SuppressWarnings("unchecked")
    private <T> T instantiateBean(Class<T> type) {
        T bean = (T) objenesis.getInstantiatorOf(type).newInstance();
        if (canProxyBean(type)) {
            bean = (T) proxyBean(bean);
        }
        return bean;
    }

    protected boolean canProxyBean(Class<?> type) {
        try {
            type.getConstructor();
        } catch (NoSuchMethodException e) {
            return false;
        }

        return !Modifier.isFinal(type.getModifiers());
    }

    private void setFieldValues(Map<String, FieldProperty> fieldProperties, Object bean) {
        for (String name : fieldProperties.keySet()) {
            FieldProperty fieldProperty = fieldProperties.get(name);
            Object value = instantiateValueForField(fieldProperty);
            setFieldValue(bean, fieldProperty.getType(), name, value);
        }
    }

    protected void setFieldValue(Object bean, Class<?> type, String name, Object value) {
        try {
            PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(bean, name);
            if (propertyDescriptor != null && propertyDescriptor.getWriteMethod() != null) {
                BeanUtils.setProperty(bean, name, value);
            } else {
                Field field = bean.getClass().getDeclaredField(name);
                field.setAccessible(true);
                field.set(bean, value);
            }
        } catch (IllegalArgumentException e) {
            //ignore when the default generated value for this field could not be cast or set.
        } catch (Exception ex) {
            logger.warn("could not create value for field '{}' for type '{}' because {}",
                    new Object[]{name, type, ex.getMessage()});
        }
    }

    protected Object instantiateValueForField(FieldProperty property) {
        for (Generator generator : generators) {
            if (generator.canGenerate(property)) {
                return ConvertUtils.convert(generator.generate(property), property.getType());
            }
        }

        return null;
    }


    private Object proxyBean(Object bean) {
        return proxyBean(bean, ".*get.*");
    }

    Object proxyBean(Class<?> bean, String pattern) {
        return proxyBean((Object) bean, pattern);
    }

    Object proxyBean(Object bean, String methodPattern) {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(interceptor);
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(methodPattern);
        advisor.setPointcut(pointcut);

        ProxyFactory factoryBean = new ProxyFactory();
        factoryBean.setProxyTargetClass(true);
        factoryBean.addAdvisor(advisor);

        if (bean instanceof Class<?>) {
            factoryBean.setTargetClass((Class) bean);
        } else {
            factoryBean.setTarget(bean);
        }
        return factoryBean.getProxy();
    }
}
