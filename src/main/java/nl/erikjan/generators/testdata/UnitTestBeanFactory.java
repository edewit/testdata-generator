package nl.erikjan.generators.testdata;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

/**
 * @author edewit
 */
public class UnitTestBeanFactory {

    private static BeanFactory beanFactory;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-beangenerator.xml");
        beanFactory = (BeanFactory) context.getBean("beanFactory");
    }

    public static <T> T createBeanInstance(Class<T> beanClass) {
        return beanFactory.instantiateBean(beanClass);
    }

    public static <T> T createService(Class<T> serviceClass) {
        return (T) beanFactory.proxyBean(serviceClass, ".*");
    }

}
