package com.softkit.database;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserEmployments {

    @Id
    @Column( unique = true )
    private long telegramId;

  //  @Type( type = "int-array" )
  //  @Column( columnDefinition = "integer[]" )
  //  private int [] arrayEmployments = null;

    public UserEmployments() {
    }

    public UserEmployments(long telegramId ) {
        this.telegramId = telegramId;
    }

    public long getTelegramId() {
        return telegramId;
    }
/*
    public int[] getArrayEmployments() {
        return arrayEmployments;
    }

    public void setArrayEmployments(int[] arrayEmployments) {
        this.arrayEmployments = arrayEmployments;
    }
*/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEmployments)) return false;
        UserEmployments that = (UserEmployments) o;
        return telegramId == that.telegramId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId);
    }

}
