package ch.nerdin.generators.testdata.framework.expressions;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author edewit
 */
public class Or extends Expression {

    private Expression left;
    private Expression right;

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

    public void setOperands(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("Or[%s, %s]", left != null ? left.toString() : "null", right != null ? right.toString() : "null");
    }

    public static class OrBuilder extends RegexBuilder {
        private Pattern orExpression = Pattern.compile("\\|");
        @Override
        public Pattern getPattern() {
            return orExpression;
        }

        @Override
        public Expression getExpression() {
            // Operands will be wired up in post-processing
            return new Or(null, null);
        }
    }
}
