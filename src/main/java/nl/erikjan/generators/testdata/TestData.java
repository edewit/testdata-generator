package nl.erikjan.generators.testdata;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

/**
 * @author edewit
 */
public class TestData {

    private static BeanFactory beanFactory;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-beangenerator.xml");
        beanFactory = (BeanFactory) context.getBean("beanFactory");
    }

    public static <T> T createBeanInstance(Class<T> beanClass) {
        return beanFactory.instantiateBean(beanClass);
    }

    public static Object createBeanInstances(Method method) {
        try {
            return beanFactory.instantiateBeans(method);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T createService(Class<T> serviceClass) {
        return (T) beanFactory.proxyBean(serviceClass, ".*");
    }

}
