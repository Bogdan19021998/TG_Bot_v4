package com.softkit.database;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created
 * 10.12.2019
 *
 */


@Entity
@Data
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statusId;

    private String statusDescription;
    private String botMessage;
    private String userMistakeResponse;
    private Timestamp dataAdded;

    public UserStatus() {
    }

    public UserStatus(String statusDescription, String botMessage, String userMistakeResponse) {
        this.statusDescription = statusDescription;
        this.botMessage = botMessage;
        this.userMistakeResponse = userMistakeResponse;
        // year-mm-dd hh:mm:ss
        dataAdded = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStatus that = (UserStatus) o;
        return statusId == that.statusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId);
    }
}
