package com.softkit.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserEnglishLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int englishLevelId;

    private String englishLevelDescription;

    public UserEnglishLevel() {
    }

    public UserEnglishLevel( String englishLevelDescription ) {
        this.englishLevelDescription = englishLevelDescription;
    }


    public int getEnglishLevelId() {
        return englishLevelId;
    }

    public String getEnglishLevelDescription() {
        return englishLevelDescription;
    }

    public void setEnglishLevelDescription(String englishLevelDescription) {
        this.englishLevelDescription = englishLevelDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEnglishLevel)) return false;
        UserEnglishLevel that = (UserEnglishLevel) o;
        return englishLevelId == that.englishLevelId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishLevelId);
    }
}
