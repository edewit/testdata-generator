package nl.erikjan.generators.testdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.erikjan.generators.testdata.framework.FieldProperty;
import nl.erikjan.generators.testdata.framework.GeneratorLocator;
import nl.erikjan.generators.testdata.framework.StringGenerator;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 */
public class BeanBuilderTest {

    @Test
    public void shouldBuildBean() {
        Map<String, FieldProperty> fieldProperties = new HashMap<String, FieldProperty>();
        FieldProperty property = new FieldProperty();
        property.setMinLength(0);
        property.setMaxLength(40);
        fieldProperties.put("firstName", property);

        BeanBuilder instance = new BeanBuilder();
        List<GeneratorLocator> mapping = new ArrayList<GeneratorLocator>();
        GeneratorLocator locator = new GeneratorLocator();
        locator.setType(Arrays.asList(new Class<?>[] {String.class}));
        Map generatorMapping = new HashMap();
        generatorMapping.put("", StringGenerator.class);
        locator.setGeneratorMapping(generatorMapping);
        mapping.add(locator);
        instance.setTypeMapping(mapping);

        Object result = instance.buildBean(Employee.class, fieldProperties);
        assertNotNull(result);
        assertEquals(Employee.class, result.getClass());
        assertNotNull(((Employee)result).getFirstName());
    }
}