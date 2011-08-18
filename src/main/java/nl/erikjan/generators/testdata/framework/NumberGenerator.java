package nl.erikjan.generators.testdata.framework;

/**
 *
 * @author Erik Jan de Wit
 */
public class NumberGenerator extends AbstractGenerator<Double> {

   @Override
   public Double generate(FieldProperty property) {
      double from = property.getMinLength();
      double to = property.getMaxLength();
      return RandomUtil.randomBetween(from, to);
   }

   @Override
   public boolean canGenerate(FieldProperty property) {
      return property.getType() != null && (property.getType().isPrimitive()
              || Number.class.isAssignableFrom(property.getType()));
   }
}
