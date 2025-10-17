package ch.nerdin.generators.testdata.framework.expressions;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author edewit
 */
public class Range extends Expression {

    List<InnerRange> ranges;

    public Range(String range) {
        ranges = new ArrayList<InnerRange>(range.length());
        final char[] chars = range.toCharArray();

        for (int i = 1, charsLength = chars.length; i < charsLength; i++) {
            char prefChar = chars[i - 1];
            char c = chars[i];
            if (c == '-' && prefChar != '\\') {
                ranges.add(new InnerRange(prefChar, chars[++i]));
                i++;
            } else if (prefChar == '\\') {
                ranges.add(new InnerRange(c, c));
            } else {
                ranges.add(new InnerRange(prefChar, prefChar));
            }
        }
    }

    @Override
    public void eval(StringBuilder builder) {
        InnerRange range = ranges.get(random.randomBetween(0, ranges.size()));
        builder.append((char) random.randomBetween(range.low, range.high));
    }

    private static class InnerRange {
        char low;
        char high;

        public InnerRange(char low, char high) {
            this.low = low;
            this.high = high;
        }

        @Override
        public String toString() {
            return String.format("%s,%s", low, high);
        }

    }

    @Override
    public String toString() {
        return String.format("Range[%s]", ranges.toString());
    }

    public static class RangeBuilder extends RegexBuilder {

        private final Pattern rangeExpression = Pattern.compile("\\[(.+?)]");

        @Override
        public Pattern getPattern() {
            return rangeExpression;
        }

        @Override
        public Expression getExpression() {
            return new Range(match.group(1));
        }
    }
}
