package com.softkit.database;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserSpecializations {

    @Id
    @Column( unique = true )
    private int telegramId;

//    @Type( type = "int-array" )
//    @Column(
//            columnDefinition = "integer[]"
//    )
//    private int[] specializations;

    public UserSpecializations() {
    }
/*
    public UserSpecializations( int [] specializations) {
        this.specializations = specializations;
    }*/

    public int getTelegramId() {
        return telegramId;
    }
//
//    public int[] getSpecializations() {
//        return specializations;
//    }
//
//    public void setSpecializations(int[] specializations) {
//        this.specializations = specializations;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSpecializations)) return false;
        UserSpecializations that = (UserSpecializations) o;
        return telegramId == that.telegramId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId);
    }
}
