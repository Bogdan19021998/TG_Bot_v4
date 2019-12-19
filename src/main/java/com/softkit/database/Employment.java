package com.softkit.database;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NotNull
@EqualsAndHashCode
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer employmentId;

    private String employmentDescription;

    private Timestamp dateAdded;

    public Employment(String employmentDescription) {

        this.employmentDescription = employmentDescription;
    }

}
