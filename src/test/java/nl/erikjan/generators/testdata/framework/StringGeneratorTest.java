package nl.erikjan.generators.testdata.framework;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test the StringGenerator.
 *
 * @author Erik Jan de Wit
 */
public class StringGeneratorTest {
    private StringGenerator stringGenerator = new StringGenerator(new RandomUtil());

    @Test
    public void shouldGenerateAStringBetweenFromAndTo() {
        int length = test(4, 10, 0);
        assertTrue(length >= 4 && length <= 10);
    }

    @Test
    public void shouldGenerateAStringWithFixedLength() {
        int length = test(0, 0, 10);
        assertTrue(length == 10);
    }

    @Test
    public void shouldGenerateAStringWithDefaultLength() {
        StringBuilder result = stringGenerator.generate(new FieldProperty());
        assertNotNull(result);
        assertEquals(10, result.length());
    }

    @Test
    public void shouldGenerateAStringWithOnlyMinLength() {
        int length = test(10, 0, 0);
        assertEquals(10, length);
    }

    private int test(int min, int max, int length) {
        FieldProperty property = new FieldProperty();
        if (length != 0) {
            property.setMinLength(length);
            property.setMaxLength(length);
        } else {
            property.setMinLength(min);
            property.setMaxLength(max);
        }
        StringBuilder result = stringGenerator.generate(property);
        assertNotNull(result);
        System.out.println(result);
        return result.length();
    }
}