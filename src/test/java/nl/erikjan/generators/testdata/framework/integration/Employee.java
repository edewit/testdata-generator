package nl.erikjan.generators.testdata.framework.integration;

import javax.persistence.Lob;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.Length;
import org.hibernate.validator.Pattern;

/**
 *
 * @author edewit
 */
public class Employee {

    @Length(max = 40)
    private String firstName;
    @Pattern(regex = "[A-Z]{1}[a-z]*")
    private String lastName;
    public Address address;
    @Lob
    private String comment;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address adres) {
        this.address = adres;
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

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
