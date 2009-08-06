package nl.erikjan.generators.testdata.framework.integration;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

/**
 *
 */
public class Adres {

    @NotNull
    private String steet;
    @Range(min = 1, max = 200, message = "Out of range")
    private int houseNumber;
    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getSteet() {
        return steet;
    }

    public void setSteet(String steet) {
        this.steet = steet;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }

}
