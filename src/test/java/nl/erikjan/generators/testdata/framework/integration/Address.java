package nl.erikjan.generators.testdata.framework.integration;

import javax.persistence.Column;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Range;

/**
 *
 */
public class Address {

    @NotNull
    private String street;
    @Range(min = 1, max = 200, message = "Out of range")
    private int houseNumber;
    @Email
    private String email;
    @Column(length = 30)
    private String postalCode;

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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
