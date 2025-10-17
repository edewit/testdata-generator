package ch.nerdin.generators.testdata.framework;

import ch.nerdin.generators.testdata.framework.expressions.*;
import ch.nerdin.generators.testdata.framework.expressions.Character;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author edewit
 */
public class ReverseRegularExpressionParserTest {
    final RegularExpressionGenerator generator = new RegularExpressionGenerator();

    @Test
    public void isIndexSet() {
        //given
        final Range.RangeBuilder action = new Range.RangeBuilder();
        action.with("aaa[a-z]");

        //when
        final boolean found = action.containsExpression();

        //then
        assertTrue(found);
        assertEquals(3, action.getStart());
    }

    @Test
    public void testCharacterBuilder() {
        //given
        Character.CharacterBuilder builder = new Character.CharacterBuilder();
        builder.with("\\.a");

        //when
        boolean found = builder.containsExpression();

        //then
        assertTrue(found);
        assertEquals(new Character('.', false).toString(), builder.getExpression().toString());

        //when
        found = builder.containsExpression();

        //then
        assertTrue(found);
        assertEquals(new Character('a', false).toString(), builder.getExpression().toString());
        assertFalse(builder.containsExpression());
    }

    @Test
    public void shouldParseSimpleCharExpression() {

        //when
        final List<Expression> expressions = generator.parse("ab");

        //then
        assertNotNull(expressions);
        assertEquals(2, expressions.size());
        assertEquals("[Character[a], Character[b]]", expressions.toString());
    }

    @Test
    public void shouldParseRangeExpressions() {

        //when
        final List<Expression> expressions = generator.parse("[a-z][A-Z]");

        //then
        assertNotNull(expressions);
        assertEquals(2, expressions.size());
        assertEquals("[Range[[a,z]], Range[[A,Z]]]", expressions.toString());
    }

    @Test
    public void shouldParseRangeExpressionComplex() {

        //when
        final List<Expression> expressions = generator.parse("[a-z\\-]{2,3}");

        //then
        assertNotNull(expressions);
        assertEquals(1, expressions.size());
        assertEquals("[Length[2, 3, Range[[a,z, -,-]]]]", expressions.toString());
    }

    @Test
    public void shouldParseRangeExpressionEval() {

        //when
        final List<Expression> expressions = generator.parse("[a-z\\-]");
        final StringBuilder builder = new StringBuilder();
        expressions.get(0).eval(builder);

        //then
        assertTrue(builder.toString().length() == 1);
        System.out.println("builder = " + builder);
    }

    @Test
    public void shouldParseLengthExpressions() {
        //when
        final List<Expression> expressions = generator.parse("{3}");

        //then
        assertNotNull(expressions);
        assertEquals(1, expressions.size());
        assertEquals("[Length[3, null, null]]", expressions.toString());
    }

    @Test
    public void shouldParseLengthRangeExpressions() {
        //when
        final List<Expression> expressions = generator.parse("{3,5}");

        //then
        assertNotNull(expressions);
        assertEquals(1, expressions.size());
        assertEquals("[Length[3, 5, null]]", expressions.toString());
    }

    @Test
    public void shouldParseCharacterExpressions() {
        //when
        final List<Expression> expressions = generator.parse("a");

        //then
        assertNotNull(expressions);
        assertEquals(1, expressions.size());
        assertEquals("[Character[a]]", expressions.toString());
    }

    @Test
    public void shouldParseMultipleExpressions() {
        final List<Expression> expressions = generator.parse("aa\\(a[a-g]{2}[h-z]{3}");

        //then
        assertNotNull(expressions);
        assertEquals(6, expressions.size());
        assertEquals("[Character[a], Character[a], Character[(], Character[a], Length[2, null, Range[[a,g]]], " +
                "Length[3, null, Range[[h,z]]]]", expressions.toString());
    }

    @Test
    public void shouldParseGreedyExpression() {
        final List<Expression> expressions = generator.parse("a*");

        //then
        assertNotNull(expressions);
        assertEquals(1, expressions.size());
        assertEquals("[Length[0, 10, Character[a]]]", expressions.toString());
    }
}
