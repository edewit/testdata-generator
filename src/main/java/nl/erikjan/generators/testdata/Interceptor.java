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

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (isKnownReturnType(method)) {
            return invocation.proceed();
        }

        return beanFactory.instantiateBeans(method);
    }

    private boolean isKnownReturnType(Method method) {
        return method.getReturnType().getName().startsWith("java.lang");
    }
}
