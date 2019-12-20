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
public class EnglishLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer englishLevelId;

    private String englishLevelDescription;

    private Timestamp dateAdded;

    public EnglishLevel( String englishLevelDescription )
    {
        this.englishLevelDescription = englishLevelDescription;
        this.dateAdded = new Timestamp(System.currentTimeMillis());
    }
}