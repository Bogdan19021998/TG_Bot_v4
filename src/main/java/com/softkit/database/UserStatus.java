package com.softkit.database;

import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created
 * 10.12.2019
 *
 */


@Entity
@Data
//@Table(name = "user_status")
public class UserStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int statusId;

    private String statusDescription;
    private String botMessage;
    private String userMistakeResponse;

    public UserStatus() {
    }

    public UserStatus(String statusDescription, String botMessage, String userMistakeResponse) {
        this.statusDescription = statusDescription;
        this.botMessage = botMessage;
        this.userMistakeResponse = userMistakeResponse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserStatus)) return false;
        UserStatus that = (UserStatus) o;
        return statusId == that.statusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId);
    }
}
