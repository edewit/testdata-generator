package ch.nerdin.generators.testdata;

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
    public void shouldInstantiateBean() {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator Instantiator = objenesis.getInstantiatorOf(String.class);
        String string = (String)Instantiator.newInstance();
        assertNotNull(string);
    }
}
