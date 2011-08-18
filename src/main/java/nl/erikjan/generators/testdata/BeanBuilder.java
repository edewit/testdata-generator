package nl.erikjan.generators.testdata;

import java.util.Map;
import javax.annotation.PostConstruct;
import nl.erikjan.generators.testdata.framework.FieldContext;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.GeneratorCatalog;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BeanBuilder {

    private static final Logger logger = LoggerFactory.getLogger(BeanBuilder.class.getName());
    @Autowired
    private Interceptor interceptor;
    private GeneratorCatalog catalog;
    private Objenesis objenesis;

    @PostConstruct
    public void init() throws Exception {
        catalog = new GeneratorCatalog();
        catalog.loadCatalog();
        objenesis = new ObjenesisStd(true);
    }

    public <T> T buildBean(Class<T> type, Map<String, FieldProperty> fieldProperties) {
        T bean = instantiateBean(type);
        setFieldValues(fieldProperties, bean);
        return bean;
    }

    private void setFieldValues(Map<String, FieldProperty> fieldProperties, Object bean) {
        FieldProperty fieldProperty = null;
        for (String name : fieldProperties.keySet()) {
            try {
                fieldProperty = fieldProperties.get(name);
                Object value = instantiateValueForField(fieldProperty);
                BeanUtils.setProperty(bean, name, value);
            } catch (IllegalArgumentException e) {
                //ignore when the default generated value for this field could not be cast or set.
            } catch (Exception ex) {
                logger.warn("could not create value for field '{}' for type '{}'", name, fieldProperty.getType());
            }

        }
    }

    private <T> T instantiateBean(Class<T> type) {
        return instantiateBean(type, true);
    }

    @SuppressWarnings("unchecked")
    private <T> T instantiateBean(Class<T> type, boolean proxy) {
        T bean;
        try {
            bean = (T) objenesis.getInstantiatorOf(type).newInstance();
            if (proxy) {
                bean = (T) proxyBean(bean);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("could not instantiate/proxy bean of type " + type, e);
        }
        return bean;
    }

    private Object instantiateValueForField(FieldProperty property) throws Exception {
        Catalog generators = catalog.getCatalog();
        Command generatorChain = generators.getCommand("GeneratorChain");
        FieldContext context = new FieldContext(property);
        generatorChain.execute(context);
        return context.get("value");
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

        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setProxyTargetClass(true);
        factoryBean.addAdvisor(advisor);

        if (bean instanceof Class<?>) {
            factoryBean.setTargetClass((Class) bean);
        } else {
            factoryBean.setTarget(bean);
        }
        return factoryBean.getObject();
    }
}
