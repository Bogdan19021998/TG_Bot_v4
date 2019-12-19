package com.softkit.database;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode
public class UserInvitedUsers {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer telegramId;

//    Set<Integer> listInvitedUsers = new HashSet<>();

    private Timestamp dateAdded;
}