package ch.nerdin.generators.testdata.framework.expressions;

/**
 * @author edewit
 */
public class Or extends Expression {

    private Expression right;
    private Expression left;

    public Or(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void eval(StringBuilder builder) {
        if (random.randomBetween(0d, 1d) > 0.5) {
            right.eval(builder);
        } else {
            left.eval(builder);
        }
    }
}
