package com.softkit.database;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@NotNull
@EqualsAndHashCode
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer specializationId;

    private String specializationDescription;

    private Timestamp dateAdded;

    public Specialization(String specializationDescription )
    {
        this.specializationDescription = specializationDescription;
        this.dateAdded = new Timestamp(System.currentTimeMillis());
    }
}