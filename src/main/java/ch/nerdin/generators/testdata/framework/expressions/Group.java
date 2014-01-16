package ch.nerdin.generators.testdata.framework.expressions;

import ch.nerdin.generators.testdata.framework.RandomUtil;

import java.util.List;

/**
 * @author edewit
 */
public class Group extends Expression {
    private List<Expression> expressionList;

    public Group(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    @Override
    public void eval(StringBuilder builder) {
        for (Expression expression : expressionList) {
            expression.eval(builder);
        }
    }

    @Override
    public String toString() {
        return String.format("Group[%s]", expressionList.toString());
    }

}
