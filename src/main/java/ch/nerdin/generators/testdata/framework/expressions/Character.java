package ch.nerdin.generators.testdata.framework.expressions;

/**
 * @author edewit
 */
public class Character extends Expression {

    private final char character;

    public Character(char character) {
        this.character = character;
    }

    @Override
    public void eval(StringBuilder builder) {
        builder.append(character);
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return String.format("Character[%s]", character);
    }

    public static class CharacterBuilder implements Builder {
        private char[] chars;
        private int index;
        private char foundChar;

        public void with(String unParsed) {
            index = 0;
            chars = unParsed.toCharArray();
        }

        public boolean containsExpression() {
            if (index + 1 < chars.length && chars[index] == '\\') {
                foundChar = chars[++index];
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
            return new Character(foundChar);
        }
    }
}
