package com.softkit.database;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NotNull
public class UserEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Setter( value = AccessLevel.NONE )
    private Integer employmentId;

    private String employmentDescription;

    private Timestamp dateAdded;

    public UserEmployment(String employmentDescription) {

        this.employmentDescription = employmentDescription;
    }

}
