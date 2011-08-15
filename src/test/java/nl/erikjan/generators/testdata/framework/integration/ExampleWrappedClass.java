package nl.erikjan.generators.testdata.framework.integration;

import java.util.LinkedList;
import java.util.List;

import nl.erikjan.generators.testdata.framework.annotation.CreateTestData;

/**
 * @author edewit
 */
@SuppressWarnings({"ALL"})
public class ExampleWrappedClass {

    @CreateTestData(min = 3, max = 5, collectionType = LinkedList.class)
    public List<Employee> findByName(String firstName) {
        return null;
    }
}
