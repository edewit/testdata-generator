package ch.nerdin.generators.testdata.framework.expressions;

/**
 * @author edewit
 */
public class Character extends Expression {

    private final char character;
    private final boolean escaped;

    public Character(char character, boolean escaped) {
        this.character = character;
        this.escaped = escaped;
    }

    @Override
    public void eval(StringBuilder builder) {
        builder.append(character == '.' && !escaped ? random.randomChar() : character);
    }

    public char getCharacter() {
        return character;
    }

    public boolean isEscaped() {
        return escaped;
    }

    @Override
    public String toString() {
        return String.format("Character[%s]", character);
    }

    public static class CharacterBuilder implements Builder {
        private char[] chars;
        private int index;
        private char foundChar;
        private boolean escaped;

        public void with(String unParsed) {
            index = 0;
            chars = unParsed.toCharArray();
        }

        public boolean containsExpression() {
            if (index + 1 < chars.length && chars[index] == '\\') {
                foundChar = chars[++index];
                escaped = true;
                index++;
                return true;
            } else if (index < chars.length) {
                foundChar = chars[index++];
                return true;
            }
            return false;
        }

        public int getStart() {
            return 0;
        }

        public int getEnd() {
            return index;
        }

        public Expression getExpression() {
            return new Character(foundChar, escaped);
        }
    }
}
