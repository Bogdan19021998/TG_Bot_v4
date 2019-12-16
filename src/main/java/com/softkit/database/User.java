package com.softkit.database;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@NonNull
//@Table(name="user")
public class User {

    @Id
    @Column( unique = true )
    @Setter(value = AccessLevel.PRIVATE)
    private int userId;

//    @OneToOne
//    @JoinColumn(name="STATUS_ID")
//    private UserStatus userStatus;

    private Integer userStatus;

    private String candidate;
    private int age;
    private int experience;
    private int englishLevel;

    // только одно из полей хранит значение
    private int city;
    private int userLocation;

    private int salaryFrom;
    private int salaryUpTo;
    private String phone;
    private String resumeFileId;
    private String referralLink;
    private Timestamp registrationTimestamp;
    private Timestamp dataEditTimestamp;

    public User() {
    }

    public User( int userId, UserStatus userStatus) {
        setUserId( userId );
       // setUserStatus( userStatus );
    }

    public User( int userId) {
        setUserId( userId );
        setUserStatus( 1 );
    }

    public Integer getCurrentStep() {
        return userStatus;
        //return userStatus.getStatusId();
    }
}
