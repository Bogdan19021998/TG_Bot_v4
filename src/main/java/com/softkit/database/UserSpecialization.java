package com.softkit.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer specializationId;

    private String specializationDescription;

    public UserSpecialization() {
    }

    public UserSpecialization( String specializationDescription ) {
        this.specializationDescription = specializationDescription;
    }

    public Integer getSpecializationId() {
        return specializationId;
    }

    public String getSpecializationDescription() {
        return specializationDescription;
    }

    public void setSpecializationDescription(String specializationDescription) {
        this.specializationDescription = specializationDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSpecialization)) return false;
        UserSpecialization that = (UserSpecialization) o;
        return specializationId == that.specializationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(specializationId);
    }
}
