package nl.erikjan.generators.testdata.framework;

import static org.junit.Assert.*;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class GeneratorLocatorTest {
    private GeneratorLocator generatorLocator;

    @Before
    public void setup() {
        this.generatorLocator = new GeneratorLocator();
        Map<String, Class<? extends AbstractGenerator>> mapping;
        mapping = new LinkedHashMap<String, Class<? extends AbstractGenerator>>();
        generatorLocator.setGeneratorMapping(mapping);
        mapping.put("/.[regex != '']", ReverseRegularExpressionGenerator.class);
        mapping.put("", StringGenerator.class);
    }

    @Test
    public void shouldFindProperGenerator() {
        FieldProperty property = new FieldProperty();
        property.setRegex("[a-z]");

        Class<? extends AbstractGenerator> generator = generatorLocator.getGenerator(property);
        assertEquals(ReverseRegularExpressionGenerator.class, generator);

        property = new FieldProperty();
        generator = generatorLocator.getGenerator(property);
        assertEquals(StringGenerator.class, generator);

    }
}
