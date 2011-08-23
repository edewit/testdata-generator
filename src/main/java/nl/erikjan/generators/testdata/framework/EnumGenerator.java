package nl.erikjan.generators.testdata.framework;

import org.springframework.stereotype.Component;

/**
 * @author edewit
 */
@Component
public class EnumGenerator implements Generator<Enum<?>> {
    public Enum<?> generate(FieldProperty property) {
        Object[] enumConstants = property.getType().getEnumConstants();
        return (Enum<?>) enumConstants[RandomUtil.randomBetween(0, enumConstants.length)];
    }

    public boolean canGenerate(FieldProperty property) {
        return property.getType().isEnum();
    }
}
