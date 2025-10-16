package ch.nerdin.generators.testdata.framework.expressions;

/**
 * @author edewit
 */
public class UnParsed extends Expression {

    private String tokens;

    public UnParsed(String tokens) {
        this.tokens = tokens;
    }

    @Override
    public void eval(StringBuilder builder) {
        throw new IllegalArgumentException("cannot call eval on unparsed expression");
    }

    @Override
    public String toString() {
        return tokens;
    }
}
