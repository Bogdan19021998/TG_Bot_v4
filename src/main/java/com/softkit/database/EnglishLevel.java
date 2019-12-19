package com.softkit.database;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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

    public EnglishLevel( String englishLevelDescription )
    {
        this.englishLevelDescription = englishLevelDescription;
    }
}