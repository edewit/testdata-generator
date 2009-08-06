package nl.erikjan.generators.testdata.framework.integration;

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
    private Adres adres;

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
