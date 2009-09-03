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
        Method method = invocation.getMethod();
        if (method.getName().startsWith("get") && !isKnownReturnType(method)) {
            return beanFactory.instanciateBeans(method);
        }

        return invocation.proceed();

    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    private boolean isKnownReturnType(Method method) {
        return method.getReturnType().getName().startsWith("java");
    }
}
