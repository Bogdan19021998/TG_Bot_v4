package com.softkit.database;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import com.softkit.vo.Step;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Data
public class User {

    @Id
    @Column( unique = true )
    @Setter(value = AccessLevel.PRIVATE)
    private int userId;

    private int statusId;
    private String candidate;

    private Step currentStep;

    private int age;
    private int experience;
    private int englishLevel;
    private int city;
    private int salaryFrom;
    private int salaryUpTo;
    private String phone;
    private String resumeFileId;
    private String referralLink;
    private Timestamp registrationTimestamp;
    private Timestamp dataEditTimestamp;

    public User() {
        this.statusId = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
