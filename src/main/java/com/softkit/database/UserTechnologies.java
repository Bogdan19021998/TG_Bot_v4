package com.softkit.database;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
public class UserTechnologies {

    @Id
    @Column( unique = true )
    private Integer id;

    private String technologies;


//    private UserProfile userProfile;

    public UserTechnologies() {
    }

    public UserTechnologies( String technologies, long dateAdded ) {
        this.technologies = technologies;
//        this.dateAdded = new Timestamp( dateAdded );
    }

}
