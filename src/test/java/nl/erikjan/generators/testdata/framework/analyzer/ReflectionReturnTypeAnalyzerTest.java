package nl.erikjan.generators.testdata.framework.analyzer;

import static org.junit.Assert.*;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import nl.erikjan.generators.testdata.framework.integration.Employee;
import org.junit.Test;

/**
 *
 */
@SuppressWarnings({"SameReturnValue"})
public class ReflectionReturnTypeAnalyzerTest {

    private final ReflectionReturnTypeAnalyzer victim = new ReflectionReturnTypeAnalyzer();
    private final Class<?>[][] expectedResults = new Class<?>[][]{{Employee.class},{String.class}, {Employee.class},
            new Class<?>[]{String.class, Employee.class}, {void.class}};
    private final String[] methodNames = new String[]{"findEmployeeById", "findSomeCodeOrWhatever", "findAllEmployees",
            "findMapOfEmployee", "findNothing"};

    @Test
    public void shouldFindTheReturnTypeOfMethods() throws Exception {
        for (int i = 0; i < methodNames.length; i++) {
            test(methodNames[i], i);
        }
    }

    private void test(String methodName, int index) throws Exception {
        Method method = getClass().getMethod(methodName);
        Class<?>[] foundClass = victim.findClass(method);
        assertArrayEquals(expectedResults[index], foundClass);
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

    public Map<String, Employee> findMapOfEmployee() {
        //test method using reflection
        return null;
    }

    public void findNothing() {
        //test method using reflection
    }
}
