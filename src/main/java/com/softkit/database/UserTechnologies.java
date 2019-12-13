package com.softkit.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class UserTechnologies {

    @Id
    @Column( unique = true )
    private int telegramId;

    private String technologies;

    private Timestamp dateAdded;

    public UserTechnologies() {
    }

    public UserTechnologies( String technologies, long dateAdded ) {
        this.technologies = technologies;
        this.dateAdded = new Timestamp( dateAdded );
    }

    public int getTelegramId() {
        return telegramId;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTechnologies)) return false;
        UserTechnologies that = (UserTechnologies) o;
        return telegramId == that.telegramId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId);
    }
}
