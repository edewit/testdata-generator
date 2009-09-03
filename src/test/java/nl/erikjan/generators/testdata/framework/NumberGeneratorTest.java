package nl.erikjan.generators.testdata.framework;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test to see if the NumberGenerator works.
 * @author Erik Jan de Wit
 */
public class NumberGeneratorTest {

    @Test
    public void shouldCreateARandomNumber() {
        NumberGenerator instance = new NumberGenerator();
        Double result = instance.generate(new FieldProperty());
        assertNotNull(result);
    }

    @Test
    public void shouldCreateARandomNumberBetweenFromAndTo() {
        FieldProperty property = new FieldProperty();
        property.setMinLength(4);
        property.setMaxLength(10);
        NumberGenerator instance = new NumberGenerator();
        Double result = instance.generate(property);
        assertNotNull(result);
        assertTrue(result <= 10 && result >= 4);
    }

    @Test
    public void shouldGenerateForNumberFieldProperties() {
        FieldProperty property = new FieldProperty();
        property.setType(Integer.class);
        NumberGenerator instance = new NumberGenerator();
        assertTrue(instance.canGenerate(property));

        property.setType(int.class);
        assertTrue(instance.canGenerate(property));
    }
}