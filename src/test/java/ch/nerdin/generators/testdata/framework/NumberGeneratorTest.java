package ch.nerdin.generators.testdata.framework;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test to see if the NumberGenerator works.
 * @author Erik Jan de Wit
 */
public class NumberGeneratorTest {
    private NumberGenerator numberGenerator = new NumberGenerator(new RandomUtil());

    @Test
    public void shouldCreateARandomNumber() {
        Double result = numberGenerator.generate(new FieldProperty());
        assertNotNull(result);
    }

    @Test
    public void shouldCreateARandomNumberBetweenFromAndTo() {
        FieldProperty property = new FieldProperty();
        property.setMinLength(4);
        property.setMaxLength(10);
        Double result = numberGenerator.generate(property);
        assertNotNull(result);
        assertTrue(result <= 10 && result >= 4);
    }

    @Test
    public void shouldGenerateForNumberFieldProperties() {
        FieldProperty property = new FieldProperty();
        property.setType(Integer.class);
        assertTrue(numberGenerator.canGenerate(property));

        property.setType(int.class);
        assertTrue(numberGenerator.canGenerate(property));
    }
}