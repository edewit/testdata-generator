package ch.nerdin.generators.testdata;

import freemarker.template.TemplateException;
import ch.nerdin.generators.testdata.util.DataSetGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    /**
     * Create a instance of beanClass that is filled with test data.
     * Will generate data for the 'simple types like String boolean and char
     * if the class is en entity bean it will use the annotations to be sure
     * that the data is valid. If the class could be proxied then get methods
     * of complex type like entity relations will be intercepted and new
     * filled beans will be created on demand. If you bean could not be proxied,
     * because it was final or doesn't have a default constructor the beans
     * will be created and filled recursively.
     *
     * @param beanClass the class to generate test data for.
     * @param <T> the instance type
     * @return the instance of the class that is filled with data.
     */
    public static <T> T createBeanInstance(Class<T> beanClass) {
        return beanFactory.instantiateBean(beanClass);
    }

    /**
     * Convenience method will create beans/bean matching the return type of the method
     * @param method the type to create beans for
     * @return instances of the return type of the method filled with test data.
     */
    public static Object createBeanInstances(Method method) {
        try {
            return beanFactory.instantiateBeans(method);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Create a proxy that will return instances filled with test data for
     * all called methods. A convenient way to have a service mock service
     *
     * @param serviceClass class or interface of service that must be mocked
     * @param <T> the instance of the service
     * @return a proxy of the specified service class that will return test data instances
     */
    public static <T> T createService(Class<T> serviceClass) {
        return (T) beanFactory.proxyBean(serviceClass, ".*");
    }

    /**
     * Convenience method to create a DBUnit type xml file for given beanClass. This method
     * will generate xml data for the specified bean and it's children.
     *
     * @param beanClass the class to create the xml for
     * @param outputFile the location of the create xml file
     * @throws IOException when I couldn't write to the file or some other IO related problem ;)
     */
    public static void createDBUnitDataSet(Class<?> beanClass, File outputFile) throws IOException {
        String xml = generateXml(beanClass);
        writeToFile(outputFile, xml);
    }

    private static String generateXml(Class<?> beanClass) throws IOException {
        String xml;
        try {
            xml = DataSetGenerator.generateDataSet(beanClass);
        } catch (TemplateException e) {
            throw new RuntimeException("an unexpected error occurred in creating the xml file", e);
        }
        return xml;
    }

    private static void writeToFile(File outputFile, String xml) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFile);
        try {
            final byte[] xmlBytes = xml.getBytes();
            fos.write(xmlBytes, 0, xmlBytes.length);
        } finally {
            fos.close();
        }
    }
}
