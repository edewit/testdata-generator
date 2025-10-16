package ch.nerdin.generators.testdata.framework;

import ch.nerdin.generators.testdata.framework.expressions.Character;
import ch.nerdin.generators.testdata.framework.expressions.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static ch.nerdin.generators.testdata.framework.expressions.Expression.Builder;

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

    private final List<Expression> expressionList = new ArrayList<Expression>();

    List<Expression> parse(String regularExpression) {
        expressionList.add(new UnParsed(regularExpression));
        reParseExpressionList(new Range.RangeBuilder());
        reParseExpressionList(new Length.LengthBuilder());
        reParseExpressionList(new Or.OrBuilder());
        reParseExpressionList(new Character.CharacterBuilder());
        reParseExpressionList(new Group.GroupBuilder());

        System.out.println("pre groups = " + expressionList);

        // Post-process to wire up Group, Length, and Or to their operands
        wireUpGroupsInList(expressionList);
        System.out.println("post groups = " + expressionList);

        wireUpOperatorsInList(expressionList);

        System.out.println("post = " + expressionList);

        return expressionList;
    }

    /**
     * Wire up Group expressions in a list (used for nested groups)
     */
    private void wireUpGroupsInList(List<Expression> list) {
        for (int i = 0; i < list.size(); i++) {
            Expression expr = list.get(i);

            if (expr instanceof Character && Objects.equals(((Character) expr).getCharacter(), '(')) {
                Group group = new Group(null);
                expressionList.set(i, group);
                // Find the matching closing parenthesis
                int closeIndex = findMatchingCloseParenInList(list, i);
                if (closeIndex > i) {
                    // Extract children between the opening and closing parentheses
                    // Keep parentheses temporarily for nested group matching
                    List<Expression> children = new ArrayList<>();
                    for (int j = i + 1; j < closeIndex; j++) {
                        Expression child = list.get(j);
                        children.add(child);
                    }

                    // Recursively wire up nested Groups within the children
                    wireUpGroupsInList(children);

                    // Recursively wire up Length and Or within the group
                    wireUpOperatorsInList(children);

                    // Set the children on the Group
                    group.setChildren(children);

                    // Remove the children and closing paren from the list
                    if (closeIndex >= i + 1) {
                        list.subList(i + 1, closeIndex + 1).clear();
                    }
                }
            }
        }

    }

    /**
     * Find the index of the matching closing parenthesis in a list
     */
    private int findMatchingCloseParenInList(List<Expression> list, int groupIndex) {
        int depth = 1;
        for (int i = groupIndex + 1; i < list.size(); i++) {
            Expression expr = list.get(i);

            if (expr instanceof Character) {
                Character group = (Character) expr;
                if (group.getCharacter() == ')') {
                    depth--;
                    if (depth == 0) {
                        return i;
                    }
                } else if (group.getCharacter() == '(') {
                    depth++;
                }
            }
        }
        return -1;
    }

    /**
     * Wire up Length and Or operators in a list of expressions (used for group children)
     */
    private void wireUpOperatorsInList(List<Expression> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Expression expr = list.get(i);

            if (expr instanceof Length) {
                // Length operates on the previous expression
                if (i > 0) {
                    Expression target = list.get(i - 1);
                    ((Length) expr).setTarget(target);
                    list.remove(i - 1);
                    i--; // Adjust index after removal
                }
            } else if (expr instanceof Or) {
                // Or operates on expressions before and after it
                if (i > 0 && i < list.size() - 1) {
                    Expression left = list.get(i - 1);
                    Expression right = list.get(i + 1);
                    ((Or) expr).setOperands(left, right);
                    list.remove(i + 1); // Remove right first (higher index)
                    list.remove(i - 1); // Then remove left
                    i--; // Adjust index after removals
                }
            }
        }

    }

    private void reParseExpressionList(Builder buildAction) {
        for (int i = 0; i < expressionList.size(); i++) {
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
            String newUnParsed = unParsed.substring(buildAction.getEnd());
            expressionList.add(new UnParsed(newUnParsed));
        }
    }

    public boolean canGenerate(FieldProperty property) {
        return StringUtils.isNotBlank(property.getRegex());
    }
}
