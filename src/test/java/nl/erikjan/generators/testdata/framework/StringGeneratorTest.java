package nl.erikjan.generators.testdata.framework;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test the StringGenerator.
 * @author Erik Jan de Wit
 */
public class StringGeneratorTest {


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
    public void shouldGenerateAStringWithDefaultLenght() {
        StringGenerator instance = new StringGenerator();
        StringBuilder result = instance.generate();
        assertNotNull(result);
        assertTrue(result.length() == 10);
    }

    @Test
    public void shouldGenrateAStringWithOnlyMinLenght() {
        int length = test(10, 0, 0);
        assertTrue(length > 10);
    }

    private int test(int min, int max, int length) {
        StringGenerator instance = null;
        if (length != 0) {
            instance = new StringGenerator(length);
        } else {
            instance = new StringGenerator(min, max);
        }
        StringBuilder result = instance.generate();
        assertNotNull(result);
        return result.length();
    }
}