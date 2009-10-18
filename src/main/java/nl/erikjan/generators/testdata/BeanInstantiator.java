package nl.erikjan.generators.testdata;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

/**
 *
 * @author edewit
 */
@Component
public class BeanInstantiator {

    @Autowired
    private Interceptor interceptor;

    private Objenesis objenesis = new ObjenesisStd();

    public Object instantiateBean(Class<?> type) throws InstantiationException, IllegalAccessException {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(interceptor);
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern(".*");
        advisor.setPointcut(pointcut);

        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setProxyTargetClass(true);
        factoryBean.addAdvisor(advisor);
        factoryBean.setTarget(objenesis.getInstantiatorOf(type).newInstance());
        return factoryBean.getObject();
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }
}
