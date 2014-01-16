package ch.nerdin.generators.testdata.framework.expressions;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author edewit
 */
public class Length extends Expression {
    private static final List<String> TOKENS = Arrays.asList("*", "?");
    private Expression expression;
    private Integer length;
    private Integer high;

    public Length(String lengthExpression) {
        if (TOKENS.contains(lengthExpression)) {
            this.length = 0;
            if ("?".equals(lengthExpression)) {
                this.high = 1;
            } else {
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

    public void setOperationExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void eval(StringBuilder builder) {
        int count = high != null ? random.randomBetween(length, high) : length;
        for (int i = 1; i < count; i++) {
            expression.eval(builder);
        }
    }

    @Override
    public String toString() {
        return String.format("Length[%d, %d]", length, high);
    }

    public Expression getExpression() {
        return expression;
    }

    public static class LengthBuilder extends RegexBuilder {
        Pattern lengthExpression = Pattern.compile("\\{(.+?)\\}|\\*|\\?");

        @Override
        public Pattern getPattern() {
            return lengthExpression;
        }

        @Override
        public Expression getExpression() {
            return new Length(match.group(1) != null ? match.group(1) : match.group());
        }
    }
}
