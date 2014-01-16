package ch.nerdin.generators.testdata.util;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.CollectionModel;
import freemarker.ext.beans.StringModel;
import freemarker.template.*;
import ch.nerdin.generators.testdata.BeanFactory;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.Column;
import java.io.*;
import java.util.*;

/**
 * @author edewit
 */
public class DataSetGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DataSetGenerator.class.getName());

    private static BeanFactory beanFactory;
    private static Configuration configuration;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-beangenerator.xml");
        beanFactory = (BeanFactory) context.getBean("beanFactory");

        configuration = new Configuration();
        configuration.setClassForTemplateLoading(DataSetGenerator.class, "/");
        configuration.setObjectWrapper(new DefaultObjectWrapper());
    }

    public static void main(String... arg) throws ClassNotFoundException, IOException, TemplateException {
        if (arg.length < 1) {
            System.err.println("usage: " + DataSetGenerator.class + " the.entity.bean.ToGenerateDataSetFor outputFile");
        }

        Class<?> beanClass = Class.forName(arg[0]);
        String xml = generateDataSet(beanClass);
        if (arg.length == 2) {
            FileOutputStream fos = new FileOutputStream(new File(arg[1]));
            try {
                fos.write(xml.getBytes(), 0, xml.getBytes().length);
            } finally {
                fos.close();
            }
        } else {
            System.out.println(xml);
        }

    }

    public static String generateDataSet(Class<?> beanClass) throws IOException, TemplateException {
        List<Object> beans = new ArrayList<Object>();
        for(int i = 0; i < 10; i++) {
            beans.add(beanFactory.instantiateBean(beanClass));
        }

        Map<String, Object> root = new HashMap<String, Object>();
        root.put("beans", beans);
        root.put("invokeBean", new DynamicList());
        root.put("invokeMethod", new DynamicMethod());

        Template temp = configuration.getTemplate("dataset.ftl");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(new BufferedOutputStream(stream));
        temp.process(root, out);
        out.flush();

        return stream.toString();
    }

    private static abstract class DynamicProperty implements TemplateDirectiveModel {
        protected Object invokeMethod(Map map) {
            String method = ((SimpleScalar) map.get("method")).getAsString();
            Object bean = ((BeanModel)map.get("bean")).getWrappedObject();
            Object property = null;

            try {
                if (PropertyUtils.isReadable(bean, method)) {
                    property = PropertyUtils.getProperty(bean, method);
                }
            } catch (Exception e) {
                logger.warn("Error calling {} on bean {}", method, bean.getClass());
            }
            return property;
        }
    }

    private static class DynamicList extends DynamicProperty {

        public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                            TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
            Object property = invokeMethod(map);

            String alias = ((SimpleScalar) map.get("alias")).getAsString();
            CollectionModel list = (CollectionModel) environment.getVariable(alias);
            ArrayList<Object> result = new ArrayList<Object>();
            if (list != null) {
                TemplateModelIterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object o = iterator.next();
                    result.add(o);
                }
            }

            if (property instanceof Collection) {
                result.addAll((Collection<?>) property);
            } else {
                result.add(property);
            }
            environment.setVariable(alias, new CollectionModel(result, new DefaultObjectWrapper()));
        }
    }

    private static class DynamicMethod extends DynamicProperty {

        public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                            TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
            Object property = invokeMethod(map);

            if (property != null) {
                String propertyName = ((SimpleScalar) map.get("method")).getAsString();
                Class beanClass = (Class) ((StringModel)environment.getVariable("beanClass")).getWrappedObject();
                try {
                    final Column annotation = beanClass.getDeclaredField(propertyName).getAnnotation(Column.class);
                    if (annotation != null && StringUtils.isNotBlank(annotation.name())) {
                        propertyName = annotation.name();
                    }
                } catch (NoSuchFieldException e) {
                    logger.error("no such field {} on bean of type {}", propertyName, beanClass);
                }

                environment.getOut().append(propertyName)
                        .append("=\"").append(String.valueOf(property)).append("\" ");
            }
        }
    }
}
