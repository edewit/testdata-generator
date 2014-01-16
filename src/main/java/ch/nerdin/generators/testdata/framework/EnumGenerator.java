package ch.nerdin.generators.testdata.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author edewit
 */
@Component
public class EnumGenerator implements Generator<Enum<?>> {
    private RandomUtil randomUtil;

    @Autowired
    public EnumGenerator(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    public Enum<?> generate(FieldProperty property) {
        Object[] enumConstants = property.getType().getEnumConstants();
        return (Enum<?>) enumConstants[randomUtil.randomBetween(0, enumConstants.length)];
    }

    public boolean canGenerate(FieldProperty property) {
        return property.getType().isEnum();
    }
}
