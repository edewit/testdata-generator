package nl.erikjan.generators.testdata.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A generator the will generate random string of a given lenght
 * 
 * @author Erik Jan de Wit
 */
@Component
public class StringGenerator implements Generator<StringBuilder> {
    private RandomUtil randomUtil;

    @Autowired
    public StringGenerator(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    private static final int DEFAULT_LENGTH = 10;

    public StringBuilder generate(FieldProperty property) {
        int from = (int) property.getMaxLength();
        int to = (int) property.getMaxLength();

        int stringLength = Math.max(from == to ? from : randomUtil.randomBetween(from, to), DEFAULT_LENGTH);
        StringBuilder sb = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {
            sb.append(randomUtil.randomChar());
        }

        return sb;
    }

    public boolean canGenerate(FieldProperty property) {
        return !property.isLob() && property.getType() != null && (String.class.isAssignableFrom(property.getType())
              || StringBuffer.class.isAssignableFrom(property.getType()));
    }
}
