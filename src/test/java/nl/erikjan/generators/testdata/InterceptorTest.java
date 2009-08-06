package nl.erikjan.generators.testdata;

import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test to see if the Interceptor works.
 */
public class InterceptorTest {

    @Test
    public void shouldDoSomething() {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"applicationContext-beangenerator.xml"});

        BeanFactory beanFactory = (BeanFactory) context.getBean("beanFactory");
        assertNotNull(beanFactory);
    }
}
