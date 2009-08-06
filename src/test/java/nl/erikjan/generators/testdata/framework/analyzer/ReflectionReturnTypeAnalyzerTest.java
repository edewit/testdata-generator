package nl.erikjan.generators.testdata.framework.analyzer;

import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.util.Collection;
import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;

/**
 *
 */
public class ReflectionReturnTypeAnalyzerTest {

    private ReflectionReturnTypeAnalyzer victim = new ReflectionReturnTypeAnalyzer();
    private Class<?>[] expectedResults = new Class<?>[]{Employee.class, String.class, Employee.class, void.class};

    @Test
    public void shouldFindTheReturnTypeOfMethods() throws Exception {
        for (int i = 1; i <= 4; i++) {
            test(i);
        }
    }

    private void test(int methodIndex) throws SecurityException {
        Method method = getClass().getMethods()[methodIndex];
        Class<?> foundClass = victim.findClass(method);
        assertEquals(expectedResults[methodIndex - 1], foundClass);
    }

    public Employee findEmployeeById(Long id) {
        //test method using reflection
        return null;
    }

    public String findSomeCodeOrWhatever() {
        //test method using reflection
        return null;
    }

    public Collection<Employee> findAllEmployees() {
        //test method using reflection
        return null;
    }

    public void findNothing() {
        //test method using reflection
    }
}
