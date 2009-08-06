package nl.erikjan.generators.testdata.framework;

/**
 *
 * @author Erik Jan de Wit
 */
public class NumberGenerator extends AbstractGenerator<Double> {
    private double from = Double.MIN_VALUE;
    private double to = Double.MIN_VALUE;

    public NumberGenerator() {
    }

    /**
     * Generate a number within given range.
     * @param from range
     * @param to range
     */
    public NumberGenerator(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public NumberGenerator(FieldProperty property) {
        if (property != null) {
            from = property.getMinLength();
            to = property.getMaxLength();
        }
    }

    @Override
    public Double generate() {
        return RandomUtil.randomBetween(from, to);
    }

}
