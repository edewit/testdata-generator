package ch.nerdin.generators.testdata.framework;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ch.nerdin.generators.testdata.framework.re.REParser;
import java.util.Map;
import ch.nerdin.generators.testdata.framework.re.ReverseRExpression;
import java.util.regex.Pattern;
import ch.nerdin.generators.testdata.framework.re.ReverseGroupExpression;
import ch.nerdin.generators.testdata.framework.re.ReverseLengthRExpression;
import ch.nerdin.generators.testdata.framework.re.ReverseOrExpression;
import ch.nerdin.generators.testdata.framework.re.ReverseRangeRExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Generate strings that will match the regular expression specified.
 * @author Erik Jan de Wit
 */
@Component
public class ReverseRegularExpressionGenerator implements Generator<String> {
    private final RandomUtil randomUtil;

    @Autowired
    public ReverseRegularExpressionGenerator(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }


    private List<ReverseRExpression> orderedExpressions;
    private final List<ReverseRExpression> orExpressions = new ArrayList<>();

    public String generate(FieldProperty property) {
        String regularExpression = property.getRegex();
        //noinspection ResultOfMethodCallIgnored validation of the expression
        Pattern.compile(regularExpression);
        Map<Integer, ReverseRExpression> uninterpretedExpressions = new REParser().parseRE(regularExpression);
        rearrangedExpressions(uninterpretedExpressions);

        return generateString();
    }

    private void parseOrExpressions(List<ReverseRExpression> orderedExpressions) {
        for (int i = 0; i < orderedExpressions.size(); i++) {
            ReverseRExpression expr = orderedExpressions.get(i);
            if (expr.getType() == ReverseRExpression.OR) {
                ((ReverseOrExpression) expr).setLeft(getPrimaryExpression(orderedExpressions, i - 1));
                ((ReverseOrExpression) expr).setRight(orderedExpressions.get(i + 1));
                orExpressions.add(expr);
                orderedExpressions.remove(expr);
            }
        }
    }

    private void rearrangedExpressions(Map<Integer, ReverseRExpression> uninterpretedExpressions) {
        int l = uninterpretedExpressions.size();
        // counts the number of (secondary) expressions that is to be inserted into primary expressions.
        // e.g. length expressions
        orderedExpressions = new LinkedList<>();
        int currentPrimExprCount = -1;
        for (int index = 1; index <= l; index++) {
            if (uninterpretedExpressions.containsKey(index)) {
                ReverseRExpression expr = uninterpretedExpressions.get(index);
                if (expr.isPrimary()) {
                    // add it to the ordered
                    orderedExpressions.add(expr);
                    currentPrimExprCount++;
                } else {
                    // secondary should be added to the previous (primary) expression
                    (orderedExpressions.get(currentPrimExprCount)).addSecundaryExpression(expr);
                }
            }
        }

        parseOrExpressions(orderedExpressions);
        parseGroup(null, orderedExpressions);
    }

    /**
     * For now add all the expressions until we find a group end expression.
     * @param groupExpression the groups
     * @param expressions the rest of the expression
     */
    private int parseGroup(ReverseGroupExpression groupExpression, List<ReverseRExpression> expressions) {
        List<ReverseRExpression> group = new ArrayList<>();
        int size = expressions.size();
        for (int index = 0; index < size; index++) {
            ReverseRExpression reverseRExpression = expressions.get(index);
            if (reverseRExpression.getType() == ReverseGroupExpression.GROUP_END) {
                expressions.remove(index);
                if (groupExpression != null) {
                    groupExpression.setGroupEnd((ReverseGroupExpression) reverseRExpression);
                }
                break;
            }

            if (reverseRExpression.getType() == ReverseGroupExpression.GROUP_START
                    && ((ReverseGroupExpression)reverseRExpression).getGroup() == null) {
                size = parseGroup((ReverseGroupExpression) reverseRExpression, expressions.subList(index + 1, size));
            } else if (groupExpression != null) {
                group.add(reverseRExpression);
                expressions.remove(index--);
                size--;
            }
        }

        if (groupExpression != null) {
            groupExpression.setGroup(group);
        }

        return size;
    }

    private ReverseRExpression getPrimaryExpression(List<ReverseRExpression> orderedExpressions, int index) {
        ReverseRExpression result = orderedExpressions.get(index);
        if (result instanceof ReverseGroupExpression) {
            do {
                result = orderedExpressions.get(--index);
            } while (result.getType() != ReverseRExpression.GROUP_START);
        }

        return result;
    }

    /**
     * Method generateString.
     *
     * @return Object
     */
    private String generateString() {
        StringBuilder generatedString = new StringBuilder();
        if (!orExpressions.isEmpty()) {
            ReverseOrExpression orExpression = (ReverseOrExpression) orExpressions.get(randomUtil.randomBetween(0, orExpressions.size()));
            ReverseRExpression expression = randomUtil.nextBoolean() ? orExpression.getLeft() : orExpression.getRight();
            generatedString.append(generate(expression));
        } else {
            for (ReverseRExpression expr : orderedExpressions) {
                generatedString.append(generate(expr));
            }
        }

        return generatedString.toString();
    }

    /**
     * Method generate.
     *
     * @param expr
     * @return Object
     */
    private String generate(ReverseRExpression expr) {
        short t = expr.getType();
        switch (t) {
            case ReverseRExpression.ANY:
                return generateCharString(expr, true);
            case ReverseRExpression.CHAR:
                return generateCharString(expr);
            case ReverseRExpression.RANGE:
                return generateRangeString((ReverseRangeRExpression) expr);
            case ReverseRExpression.GROUP_START:
                StringBuilder result = new StringBuilder();
                ReverseGroupExpression groupExpression = (ReverseGroupExpression) expr;
                List<ReverseRExpression> group = groupExpression.getGroup();
                long length = getRandomExpressionLength(groupExpression.getGroupEnd());

                for (int i = 0; i < length; i++) {
                    for (ReverseRExpression expression : group) {
                        result.append(generate(expression));
                    }
                }
                return result.toString();
            default:
                break;
        }
        return null;
    }

    private String generateCharString(ReverseRExpression expr, boolean any) {
        StringBuilder result = new StringBuilder();
        long length = getRandomExpressionLength(expr);

        for (int i = 0; i < length; i++) {
            if (any) {
                result.append(randomUtil.randomChar());
            } else {
                result.append(expr.getGenerationInstruction());
            }
        }

        return result.toString();
    }

    private String generateCharString(ReverseRExpression expr) {
        return generateCharString(expr, false);
    }

    private long getRandomExpressionLength(ReverseRExpression expr) {
        long startLength = 1;
        long endLength = 1;
        List<ReverseRExpression> secExpressions = expr.getSecundaryExpressions();
        if (secExpressions != null && secExpressions.size() == 1) {
            ReverseRExpression secExpr = secExpressions.get(0);
            if (secExpr instanceof ReverseLengthRExpression) {
                ReverseLengthRExpression secLengthExpr = (ReverseLengthRExpression) secExpr;
                startLength = secLengthExpr.getStartLength();
                endLength = secLengthExpr.getEndLength();
            }
        }

        long length = startLength;
        if (startLength != endLength) {
            length = randomUtil.randomBetween(startLength, endLength);
        }
        return length;
    }

    /**
     * Method generateRangeString. 24/10/2004 Only support secundary length expressions.
     *
     * @param expr
     * @return String
     */
    private String generateRangeString(ReverseRangeRExpression expr) {
        StringBuilder r = new StringBuilder();
        long length = getRandomExpressionLength(expr);
        if (expr.getRangeChars() != null && expr.getRangeChars().length > 0) {
            for (long g = 0; g < length; g++) {
                int c = 0;
                if (expr.getRangeChars().length > 1) {
                    c = randomUtil.randomBetween(1, expr.getRangeChars().length);

                }
                r.append(expr.getRangeChars()[c]);
            }
        }
        // else no range
        return r.toString();
    }

   public boolean canGenerate(FieldProperty property) {
      return StringUtils.isNotBlank(property.getRegex());
   }
}
