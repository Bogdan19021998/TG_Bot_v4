package com.softkit.database;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class UserSpecialization {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    private Specialization specialization;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserSpecialization(User user, Specialization specialization) {
        this.user = user;
        this.specialization = specialization;
    }
}