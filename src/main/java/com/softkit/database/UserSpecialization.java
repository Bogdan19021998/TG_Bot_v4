package com.softkit.database;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class UserSpecialization {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @GeneratedValue
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSpecialization that = (UserSpecialization) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}