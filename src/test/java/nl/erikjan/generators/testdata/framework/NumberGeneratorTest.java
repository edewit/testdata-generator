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
        Double result = instance.generate();
        assertNotNull(result);
        assertTrue(result <= Double.MAX_VALUE && result >= Double.MIN_VALUE);
    }

    @Test
    public void shouldCreateARandomNumberBetweenFromAndTo() {
        NumberGenerator instance = new NumberGenerator(4, 10);
        Double result = instance.generate();
        assertNotNull(result);
        assertTrue(result <= 10 && result >= 4);
    }
}