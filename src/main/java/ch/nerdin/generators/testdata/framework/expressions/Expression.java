package ch.nerdin.generators.testdata.framework.expressions;

import ch.nerdin.generators.testdata.framework.RandomUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author edewit
 */
public abstract class Expression {
    protected RandomUtil random = new RandomUtil();

    public abstract void eval(StringBuilder builder);

    public static interface Builder {
        void with(String unParsed);

        boolean containsExpression();

        int getStart();

        int getEnd();

        Expression getExpression();
    }

    public static abstract class RegexBuilder implements Builder {
        private int start;
        private int end;
        Matcher match;

        public abstract Pattern getPattern();

        public void with(String unParsed) {
            match = getPattern().matcher(unParsed);
        }

        public boolean containsExpression() {
            boolean result = match.find();
            if (result) {
              this.start = match.start();
              this.end = match.end();
            }
            return result;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public abstract Expression getExpression();
    }
}
