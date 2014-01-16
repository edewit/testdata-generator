package ch.nerdin.generators.testdata.framework;

import ch.nerdin.generators.testdata.framework.expressions.Character;
import ch.nerdin.generators.testdata.framework.expressions.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static ch.nerdin.generators.testdata.framework.expressions.Expression.Builder;
import static ch.nerdin.generators.testdata.framework.expressions.Length.LengthBuilder;

/**
 * @author edewit
 */
public class RegularExpressionGenerator implements Generator<String> {

    public String generate(FieldProperty property) {
        String regularExpression = property.getRegex();
        validateExpression(regularExpression);

        List<Expression> expressionList = parse(regularExpression);

        return generateString(expressionList);
    }

    private String generateString(List<Expression> expressionList) {
        StringBuilder builder = new StringBuilder();
        for (Expression expression : expressionList) {
            expression.eval(builder);
        }
        return builder.toString();
    }

    private void validateExpression(String regularExpression) {
        Pattern.compile(regularExpression);
    }

    private List<Expression> expressionList = new ArrayList<Expression>();

    List<Expression> parse(String regularExpression) {
        expressionList.add(new UnParsed(regularExpression));
        reParseExpressionList(new Range.RangeBuilder());
        reParseExpressionList(new Length.LengthBuilder());
        reParseExpressionList(new Character.CharacterBuilder());

        return expressionList;
    }

    private void reParseExpressionList(Builder buildAction) {
        for (int i = 0, expressionListSize = expressionList.size(); i < expressionListSize; i++) {
            Expression expr = expressionList.get(i);
            if (expr instanceof UnParsed) {
                String unParsed = expr.toString();
                buildAction.with(unParsed);
                int index = 0;
                if (buildAction.containsExpression()) {
                    expressionList.remove(i);
                    do {
                        expressionList.add(i++, buildAction.getExpression());

                        if (buildAction.getStart() > 0) {
                            String newUnParsed = unParsed.substring(index, buildAction.getStart());
                            expressionList.add(Math.max(i++ - 1, 0), new UnParsed(newUnParsed));
                        }
                        index = buildAction.getEnd();
                    } while (buildAction.containsExpression());

                    addUnParsedEnd(buildAction, unParsed);
                }
            }
        }
    }

    private void addUnParsedEnd(Builder buildAction, String unParsed) {
        if (buildAction.getEnd() < unParsed.length()) {
            String newUnParsed = unParsed.substring(buildAction.getEnd(), unParsed.length());
            expressionList.add(new UnParsed(newUnParsed));
        }
    }

    public boolean canGenerate(FieldProperty property) {
        return StringUtils.isNotBlank(property.getRegex());
    }
}
