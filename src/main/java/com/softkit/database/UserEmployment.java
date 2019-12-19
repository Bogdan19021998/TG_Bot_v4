package com.softkit.database;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
public class UserEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer employmentId;

    private String employmentDescription;

    public UserEmployment() {
    }

    public UserEmployment(String employmentDescription) {
        this.employmentDescription = employmentDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEmployment)) return false;
        UserEmployment that = (UserEmployment) o;
        return employmentId == that.employmentId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(employmentId);
    }
}
