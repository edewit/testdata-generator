package ch.nerdin.generators.testdata.framework.integration;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

/**
 * @author edewit
 */
@Entity
public class Manager {
    @GeneratedValue
    private Long id;

    private String name;

    @Column(length = 20)
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long inId) {
        id = inId;
    }

    public String getName() {
        return name;
    }

    public void setName(String inName) {
        name = inName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String inMemo) {
        memo = inMemo;
    }
}
