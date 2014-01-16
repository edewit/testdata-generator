package ch.nerdin.generators.testdata;

import ch.nerdin.generators.testdata.framework.integration.Employee;

import java.util.List;

/**
* @author edewit
*/
public class FinalFieldModel {
    private final String fieldName;
    private final String fName;
    private List<Employee> employees;

    public FinalFieldModel(String fieldName, String inName) {
        this.fieldName = fieldName;
        this.fName = inName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getName() {
        return fName;
    }

    public List<Employee> getEmployees() {

        return employees;
    }
}
