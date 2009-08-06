package nl.erikjan.generators.testdata;

import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class Interceptor implements MethodInterceptor {

    @Autowired
    private BeanFactory beanFactory;

    //TODO fix this...
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (invocation.getMethod().getName().startsWith("get")) {
            Method method = invocation.getMethod();
            return beanFactory.instanciateBeans(method);
        }

        return invocation.proceed();

    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
