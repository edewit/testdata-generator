package nl.erikjan.generators.testdata;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.erikjan.generators.testdata.framework.AbstractGenerator;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.GeneratorLocator;
import nl.erikjan.generators.testdata.framework.ReverseRegularExpressionGenerator;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BeanBuilder {

    private Logger logger = Logger.getLogger(BeanBuilder.class.getName());
    @Autowired
    private BeanInstantiator beanInstantiator;
    private List<GeneratorLocator> typeMapping;

    //TODO complex fields e.g. Collections solve that.
    public Object buildBean(Class<?> type, Map<String, FieldProperty> fieldProperties) {
        Object bean = null;
        try {
            bean = beanInstantiator.instantiateBean(type);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "could not instantiate bean of type '{0}'", type);
            return null;
        }

        String name = null;
        try {
            Field[] fields = type.getDeclaredFields();
            for (Field field : fields) {
                name = field.getName();
                Object value = instantiateValueForField(field, fieldProperties.get(name));
                if (value != null) {
                    BeanUtils.setProperty(bean, field.getName(), value);
                }
            }
        } catch (Exception ex) {
            logger.log(Level.WARNING, "could not create value for field '{0}' for type {1}", new Object[] {name, type});
            ex.printStackTrace();
        }

        return bean;
    }

    private Object instantiateValueForField(Field field, FieldProperty property) throws Exception {
        Object generatedValue = null;
        Class<? extends AbstractGenerator> generatorClass = findGeneratorClass(field, property);

        if (generatorClass != null) {
            Constructor constructor = generatorClass.getConstructor(FieldProperty.class);
            AbstractGenerator generator = (AbstractGenerator) constructor.newInstance(property);
            generatedValue = generator.generate();
        }

        return generatedValue;
    }

    /**
     * TODO: Have to find something that is a little cleaner than this.
     * @param field
     * @param property
     * @return
     */
    private Class<? extends AbstractGenerator> findGeneratorClass(Field field, FieldProperty property) {
        Class<? extends AbstractGenerator> generatorClass = null;
        Class<?> type = field.getType();
        for (GeneratorLocator generatorLocator : typeMapping) {
            if (generatorLocator.getType().contains(type)) {
                generatorClass = generatorLocator.getGenerator(property);
            }
        }

        return generatorClass;
    }

    public void setTypeMapping(List<GeneratorLocator> typeMapping) {
        this.typeMapping = typeMapping;
    }

    public void setBeanInstantiator(BeanInstantiator beanInstantiator) {
        this.beanInstantiator = beanInstantiator;
    }
}
