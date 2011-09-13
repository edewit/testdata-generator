package ch.nerdin.generators.testdata.framework;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Erik Jan de Wit
 */
public class ReverseRegularExpressionGeneratorTest {

    @Test
    public void shouldGenerateAStringMatchingSimpleExpression() {
        setupExpressionTest("ab");
    }

    @Test
    public void shouldGenerateAStringUsingARangeAndALengthExpression() {
        setupExpressionTest("[0-9]{2}");
    }

    @Test
    public void shouldGenerateAStringUsingOrExpression() {
        setupExpressionTest("a|b");
    }

    @Test
    public void shouldGenreateAStringWithGrouping() {
        setupExpressionTest("(a|b)");
    }

    @Test
    public void shouldGenerateAStringWithGroupsInGroups() {
        setupExpressionTest("((a)|(b))");
    }

    @Test
    public void shoulGenerateAStringWithManyOrExpressions() {
        setupExpressionTest("(addfa){3}");
    }

    @Test
    public void shouldGenerateAStringWithGreedyExpressions() {
        setupExpressionTest("a*");
        setupExpressionTest("ab?");
        setupExpressionTest("a.*");
    }

    @Test
    public void shouldGenerateAStringUsingRealExampleExpression() {
        setupExpressionTest("(([0-9]{3}-?[0-9]{7})|([0-9]{4}-?[0-9]{6}))");
    }

//    @Test
//    public void shouldGenrateAValidEmailAdress() {
//        setupExpressionTest("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
//    }

    @Test
    public void shouldGenerateValidCreditCardNumbers() {
        setupExpressionTest("5[1-5][0-9]{14}");
    }

    private void setupExpressionTest(String expression) {
        FieldProperty property = new FieldProperty();
        property.setRegex(expression);
        ReverseRegularExpressionGenerator instance = new ReverseRegularExpressionGenerator(new RandomUtil());
        String result = instance.generate(property);
        assertNotNull(result);
        System.out.println("result from expression '" + expression + "' = " + result);
        assertTrue(result.matches(expression));
    }
}
