package nl.erikjan.generators.testdata.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Erik Jan de Wit
 */
@Component
public class NumberGenerator implements Generator<Double> {
    private RandomUtil randomUtil;

    @Autowired
    public NumberGenerator(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }


    public Double generate(FieldProperty property) {
        double from = property.getMinLength();
        double to = property.getMaxLength();
        if (from == 0 && to == 0) {
            return (double) randomUtil.nextLong();
        }
        return randomUtil.randomBetween(from, to);
    }

    public boolean canGenerate(FieldProperty property) {
        return property.getType() != null && (property.getType().isPrimitive()
                || Number.class.isAssignableFrom(property.getType()));
    }
}
