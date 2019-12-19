package com.softkit.database;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserEmployments {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Integer telegramId;

    // Set<UserEmployment> employments;

    public UserEmployments( Integer id )
    {
        this.telegramId = id;
    }
}
