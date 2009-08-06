package nl.erikjan.generators.testdata.framework;

/**
 * A generator the will generate random string of a given lenght
 * 
 * @author Erik Jan de Wit
 */
public class StringGenerator extends AbstractGenerator<StringBuilder> {
    private static final int DEFAULT_LENGTH = 10;
    private int from;
    private int to;
    private int length;

    public StringGenerator() {
        length = DEFAULT_LENGTH;
    }

    /**
     * Generates strings random with given bounds. e.g.
     * from 5 to 10 will generate strings with a mininum length of 5
     * and a maximum of 10.
     * @param from index
     * @param to index
     */
    public StringGenerator(int from, int to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Generates strings with a fixed length.
     * @param length the length of the generated strings
     */
    public StringGenerator(int length) {
        this.length = length;
    }

    public StringGenerator(FieldProperty property) {
        if (property != null) {
            from = (int) property.getMinLength();
            to = (int) property.getMaxLength();
        } else {
            length = DEFAULT_LENGTH;
        }
    }

    @Override
    public StringBuilder generate() {
        int stringLength = length != 0 ? length : RandomUtil.randomBetween(from, to);
        StringBuilder sb = new StringBuilder(stringLength);

        for (int i = 0; i < stringLength; i++) {
            sb.append(RandomUtil.randomChar());
        }
        
        return sb;
    }
}
