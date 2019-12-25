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
@NotNull
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer technologyId;

    private String technologyDescription;

    private Timestamp dateAdded;

    public Technology(String technologyDescription ) {
        this.technologyDescription = technologyDescription;
        this.dateAdded = new Timestamp(System.currentTimeMillis());
    }
}