/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.nerdin.generators.testdata.framework.re;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Parses a regular expression in separate expressions.
 * TODO add escaped char like \. \t \b and fix the or expression.
 * @author Erik Jan de Wit
 */
public class REParser {

    private final HashMap<Integer, ReverseRExpression> expressions = new HashMap<Integer, ReverseRExpression>();

    public Map<Integer, ReverseRExpression> parseRE(String expression) {
        char[] mask = expression.trim().toCharArray();
        int cl = 0; // mask.length;
        int start = 1;
        while (cl < mask.length) {
            int newcl = parseSubexpression(start, mask, cl);
            if (newcl <= cl) {
                break;
            }
            cl = newcl;
            start++;
        }

        return expressions;
    }

    /**
     * Method parseSubexpression.
     *
     * @param nr
     * @param mask
     * @param startIndex
     * @return int
     */
    private int parseSubexpression(int nr, char[] mask, int startIndex) {
        int r = startIndex;
        switch (mask[startIndex]) {
            case '[':
                r = createRangeExpresssion(nr, mask, startIndex + 1);
                break;
            case '{':
                r = parseLengthExpresssion(nr, mask, startIndex + 1);
                break;
            case '.':
                r = parseAnyExpression(nr, mask, startIndex);
                break;
            case '?':
                r = parseGreedyExpression(nr, mask, startIndex);
                break;
            case '+':
                r = parseGreedyExpression(nr, mask, startIndex);
                break;
            case '*':
                r = parseGreedyExpression(nr, mask, startIndex);
                break;
            case '|':
                r = parseOrExpression(nr, startIndex);
                break;
            case '(':
                r = parseGroupExpression(nr, startIndex, true);
                break;
            case ')':
                r = parseGroupExpression(nr, startIndex, false);
                break;
            default:
                r = parseCharExpression(nr, mask, startIndex);
        }
        return r;
    }

    private int parseGroupExpression(int nr, int startIndex, boolean start) {
        ReverseRExpression expression = new ReverseGroupExpression(start ? ReverseRExpression.GROUP_START : ReverseRExpression.GROUP_END);
        this.expressions.put(nr, expression);
        return startIndex + 1;
    }

    private int parseOrExpression(int nr, int startIndex) {
        ReverseRExpression expression = new ReverseOrExpression(ReverseRExpression.OR);
        this.expressions.put(nr, expression);
        return startIndex + 1;
    }

    private int parseCharExpression(int nr, char[] mask, int startIndex) {
        ReverseRExpression expression = new ReverseRExpression(ReverseRangeRExpression.CHAR);
        expression.generationInstruction = mask[startIndex];
        this.expressions.put(nr, expression);
        return startIndex + 1;
    }

    /**
     * Method parseAnyExpression.
     *
     * @param nr
     * @param mask
     * @param startIndex
     * @return
     */
    private int parseAnyExpression(int nr, char[] mask, int startIndex) {
        this.expressions.put(nr, ReverseRExpression.ANY_EXPR);
        return startIndex + 1;
    }

    /**
     * Method parseAnyExpression.
     *
     * @param nr
     * @param mask
     * @param startIndex
     * @return
     */
    private int parseGreedyExpression(int nr, char[] mask, int startIndex) {
        ReverseRExpression expression = new ReverseGreedyExpression(mask[startIndex]);
        this.expressions.put(nr, expression);
        return startIndex + 1;
    }

    /**
     * Method parseCountExpresssion.
     *
     * @param nr
     * @param mask
     * @param startIndex
     * @return
     */
    private int parseLengthExpresssion(int nr, char[] mask, int startIndex) {
        int currentIndex = startIndex;
        StringBuilder startLengthString = new StringBuilder();
        StringBuilder endLengthString = new StringBuilder();
        boolean startMode = true;
        while (mask[currentIndex] != '}' && currentIndex < mask.length) {
            if (Character.isDigit(mask[currentIndex])) {
                if (startMode) {
                    startLengthString.append(mask[currentIndex]);
                    currentIndex++;
                } else {
                    endLengthString.append(mask[currentIndex]);
                    currentIndex++;
                }
            } else if (mask[currentIndex] == ',' && startMode) {
                // between sign
                currentIndex++;
                // switch to end mode
                startMode = false;
            }
        }

        if (mask[currentIndex] == '}') {
            currentIndex++;
        }

        long startLength = Long.parseLong(startLengthString.toString());
        long endLength;
        // is there an end length defined?
        if (!startMode) {
            endLength = Long.parseLong(endLengthString.toString());
        } else {
            endLength = startLength;
        }

        this.expressions.put(nr, new ReverseLengthRExpression(startLength, endLength));
        return currentIndex;
    }

    /**
     * Method parseRangeExpresssion.
     *
     * @param nr
     * @param mask
     * @param startIndex
     * @return
     */
    private int createRangeExpresssion(int nr, char[] mask, int startIndex) {
        int currentIndex = startIndex;
        HashSet<Character> rangeSet = new HashSet<Character>();
        // sub
        while (mask[currentIndex] != ']' && currentIndex < mask.length) {
            char beginChar = mask[currentIndex];
            // TODO test for a nonalpanumeric
            if (currentIndex + 1 < mask.length) {
                // if -
                if (mask[currentIndex + 1] == '-' && currentIndex + 2 < mask.length) {
                    char endChar = mask[currentIndex + 2];
                    // TODO test for a nonalpanumeric
                    addCharacterRange(rangeSet, beginChar, endChar);
                    currentIndex += 3;
                } else {
                    // otherwise begin is a simple char add it to the set and loop!
                    currentIndex++;
                    rangeSet.add(beginChar);
                }
            } else {
                // otherwise begin is a simple char add it to the set and loop!
                currentIndex++;
                rangeSet.add(beginChar);
            }
        }

        if (mask[currentIndex] == ']') {
            currentIndex++;
        }
        this.expressions.put(nr, new ReverseRangeRExpression(rangeSet));
        return currentIndex;
    }

    /**
     * Method addCharacterRange.
     *
     * @param rangeSet
     * @param beginChar
     * @param endChar
     */
    private void addCharacterRange(HashSet<Character> rangeSet, char beginChar, char endChar) {
        // Numbers
        if (Character.isDigit(beginChar) && Character.isDigit(endChar)) {
            addRange(rangeSet, beginChar, endChar);
        } else if ((Character.isLetter(beginChar) && Character.isDigit(endChar)) || (Character.isDigit(beginChar) && Character.isLetter(endChar))) {
            // todo prio 2 exception when an illegal character range
        } // alphabetic characters taking in account case
        else if ((Character.isLowerCase(beginChar) && Character.isLowerCase(endChar)) || (Character.isUpperCase(beginChar) && Character.isUpperCase(endChar))) {
            addRange(rangeSet, beginChar, endChar);
        } else if (Character.isLowerCase(beginChar) && Character.isUpperCase(endChar)) {
            addRange(rangeSet, beginChar, 'z');
            addRange(rangeSet, 'A', endChar);
        } else if (Character.isUpperCase(beginChar) && Character.isLowerCase(endChar)) {
            addRange(rangeSet, beginChar, 'Z');
            addRange(rangeSet, 'a', endChar);
        } else {
            // todo prio 2 exception when an illegal character range
        }
    }

    private void addRange(HashSet<Character> rangeSet, char beginChar, char endChar) {
        for (char ci = beginChar; ci <= endChar; ci++) {
            rangeSet.add(ci);
        }
    }
}
