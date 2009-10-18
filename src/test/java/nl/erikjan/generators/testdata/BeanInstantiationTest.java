package nl.erikjan.generators.testdata;

import static org.junit.Assert.*;
import org.junit.Test;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

/**
 *
 * @author edewit
 */
public class BeanInstantiationTest {

    @Test
    public void shouldInstantateBean() {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator instantiator = objenesis.getInstantiatorOf(String.class);
        String string = (String)instantiator.newInstance();
        assertNotNull(string);
    }
}
