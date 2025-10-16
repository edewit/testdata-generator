package ch.nerdin.generators.testdata.framework.expressions;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author edewit
 */
public class Length extends Expression {
    private static final List<String> TOKENS = Arrays.asList("*", "?", "+");
    private Expression target;
    private Integer length;
    private Integer high;

    public Length(String lengthExpression, Expression target) {
        this.target = target;
        if (TOKENS.contains(lengthExpression)) {
            if ("?".equals(lengthExpression)) {
                this.length = 0;
                this.high = 1;
            } else if ("+".equals(lengthExpression)) {
                this.length = 1;
                this.high = 10;
            } else {  // "*"
                this.length = 0;
                this.high = 10;
            }
        } else {
            final String[] parts = lengthExpression.split(",");
            this.length = Integer.valueOf(parts[0]);
            if (parts.length > 1) {
                this.high = Integer.valueOf(parts[1]);
            }
        }
    }

    @Override
    public void eval(StringBuilder builder) {
        int count = high != null ? random.randomBetween(length, high) : length;
        for (int i = 0; i < count; i++) {
            target.eval(builder);
        }
    }

    public void setTarget(Expression target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return String.format("Length[%d, %d, %s]", length, high, target != null ? target.toString() : "null");
    }

    public static class LengthBuilder extends RegexBuilder {
        Pattern lengthExpression = Pattern.compile("\\{(.+?)}|\\*|\\?|\\+");

        @Override
        public Pattern getPattern() {
            return lengthExpression;
        }

        @Override
        public Expression getExpression() {
            // Target will be wired up in post-processing
            return new Length(match.group(1) != null ? match.group(1) : match.group(), null);
        }
    }
}
