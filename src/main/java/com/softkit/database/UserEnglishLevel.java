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
public class UserEnglishLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer englishLevelId;

    private String englishLevelDescription;

    public UserEnglishLevel( String englishLevelDescription )
    {
        this.englishLevelDescription = englishLevelDescription;
    }
}
