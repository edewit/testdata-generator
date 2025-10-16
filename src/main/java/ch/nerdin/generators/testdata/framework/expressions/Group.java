package ch.nerdin.generators.testdata.framework.expressions;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author edewit
 */
public class Group extends Expression {
    private List<Expression> children;

    public Group(List<Expression> children) {
        this.children = children;
    }

    @Override
    public void eval(StringBuilder builder) {
        for (Expression expression : children) {
            expression.eval(builder);
        }
    }

    public void setChildren(List<Expression> children) {
        this.children = children;
    }

    public List<Expression> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return String.format("Group[%s]", children != null ? children.toString() : "null");
    }

    public static class GroupBuilder extends RegexBuilder {
        private final Pattern groupExpression = Pattern.compile("\\(");

        @Override
        public Pattern getPattern() {
            return groupExpression;
        }

        @Override
        public Expression getExpression() {
            return new Group(null);
        }
    }
}
