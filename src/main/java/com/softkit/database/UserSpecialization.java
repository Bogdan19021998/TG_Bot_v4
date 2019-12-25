package com.softkit.database;

import com.softkit.vo.Specialization;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserSpecialization {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private Specialization specialization;

    private Integer userId;

    public UserSpecialization(Integer userId, Specialization specialization) {
        this.userId = userId;
        this.specialization = specialization;
    }
}