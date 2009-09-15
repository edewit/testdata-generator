package nl.erikjan.generators.testdata;

import java.lang.reflect.Field;
import java.util.Map;
import javax.annotation.PostConstruct;
import nl.erikjan.generators.testdata.framework.FieldContext;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.GeneratorCatalog;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
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

    private static Logger logger = LoggerFactory.getLogger(BeanBuilder.class.getName());
    @Autowired
    private Interceptor interceptor;
    private GeneratorCatalog catalog;

    @PostConstruct
    public void init() throws Exception {
        catalog = new GeneratorCatalog();
        catalog.loadCatalog();
    }

    //TODO complex fields e.g. Collections solve that.
    public Object buildBean(Class<?> type, Map<String, FieldProperty> fieldProperties) {
        Object bean = null;
        try {
            // TODO type.newInstance can be smarter...
            bean = proxyBean(type.newInstance());
        } catch (Exception e) {
            logger.error("could not instantiate bean of type '{}'", type);
            logger.error("due to exection", e);
            return null;
        }

        String name = null;
        try {
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                name = field.getName();
                Object value = instantiateValueForField(fieldProperties.get(name));
                if (value != null) {
                    BeanUtils.setProperty(bean, name, value);
                }
            }
        } catch (Exception ex) {
            logger.warn("could not create value for field '{}' for type '{}'", name, type);
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

    private Object proxyBean(Object bean) throws InstantiationException, IllegalAccessException {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(interceptor);
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".*");
        advisor.setPointcut(pointcut);

        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setProxyTargetClass(true);
        factoryBean.addAdvisor(advisor);
        factoryBean.setTarget(bean);
        return factoryBean.getObject();
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }
}
