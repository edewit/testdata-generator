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
    private String[] methodNames = new String[]{"findEmployeeById", "findSomeCodeOrWhatever", "findAllEmployees",
        "findNothing"};

    @Test
    public void shouldFindTheReturnTypeOfMethods() throws Exception {
        for (int i = 0; i < methodNames.length; i++) {
            test(methodNames[i], i);
        }
    }

    private void test(String methodName, int index) throws Exception {
        Method method = getClass().getMethod(methodName);
        Class<?> foundClass = victim.findClass(method);
        assertEquals(expectedResults[index], foundClass);
    }

    public Employee findEmployeeById() {
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
