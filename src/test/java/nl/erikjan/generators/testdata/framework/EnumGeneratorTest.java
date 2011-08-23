package nl.erikjan.generators.testdata.framework;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * @author edewit
 */
public class EnumGeneratorTest {

    private FieldProperty property;

    @Before
    public void setup() {
        property = new FieldProperty();
        property.setType(AnEnum.class);
    }

    @Test
    public void testGenerate() throws Exception {
        EnumGenerator enumGenerator = new EnumGenerator();
        Enum<?> anEnum = enumGenerator.generate(property);
        List<AnEnum> values = Arrays.asList(AnEnum.values());
        assertTrue(values.contains(anEnum));
    }

    @Test
    public void testCanGenerate() throws Exception {
        EnumGenerator enumGenerator = new EnumGenerator();
        assertTrue(enumGenerator.canGenerate(property));
        FieldProperty fieldProperty = new FieldProperty();
        fieldProperty.setType(String.class);
        assertFalse(enumGenerator.canGenerate(fieldProperty));
    }

    @SuppressWarnings("unused")
    public static enum AnEnum {
        ONE,
        TWO,
        THREE
    }
}
