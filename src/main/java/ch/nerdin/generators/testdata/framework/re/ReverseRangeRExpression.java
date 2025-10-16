package ch.nerdin.generators.testdata.framework.re;

import java.util.Set;

/**
 * @author gerard
 *
 */
public class ReverseRangeRExpression extends ReverseRExpression {
    private Character[] characterRange;

    /**
     * creates a reverse range expression
     *
     * @param rangeSet
     */
    public ReverseRangeRExpression(Set<Character> rangeSet) {
        super(RANGE, rangeSet);
    }

    /**
     * @return array of wrapped chars
     */
    public Character[] getRangeChars() {
        if (characterRange == null) {
            Set<Character> rangeSet = (Set<Character>) generationInstruction;
            characterRange = rangeSet.toArray(new Character[rangeSet.size()]);
        }
        return characterRange;
    }

    @Override
    public String toString() {
        StringBuilder r = new StringBuilder(super.toString());
        r.append(System.lineSeparator());
        r.append(" allowed chars={");
        for (int i = 0; i < getRangeChars().length; i++) {
            r.append(getRangeChars()[i]);
            if (i < getRangeChars().length - 1) {
                r.append(',');
            }
        }
        r.append('}');
        return r.toString();
    }

}