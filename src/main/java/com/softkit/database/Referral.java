package com.softkit.database;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode
public class Referral {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer telegramId;

    private Integer referralId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp dateAdded;

}