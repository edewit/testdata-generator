package ch.nerdin.generators.testdata.framework.integration;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 *
 * @author edewit
 */
@Table(name = "PREFIX_EMPLOYEE")
public class Employee {
    public static final String LAST_NAME_PATTERN = "[A-Z][a-z]{5}";

    @Length(max = 40)
    private String firstName;
    @Pattern(regexp = LAST_NAME_PATTERN)
    private String lastName;
    public Address address;
    @Lob
    private String comment;
    private List<Manager> managers;
    private EmployeeType employeeType;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String inComment) {
        comment = inComment;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> inManagers) {
        managers = inManagers;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType inEmployeeType) {
        employeeType = inEmployeeType;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
