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
public class Experience {
//    todo remove this

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer experienceId;

    private String experienceDescription;

    private Timestamp dateAdded;

    public Experience(String experienceDescription ) {
        this.experienceDescription = experienceDescription;
        this.dateAdded = new Timestamp(System.currentTimeMillis());
    }

}