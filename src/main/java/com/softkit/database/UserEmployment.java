package com.softkit.database;

import com.softkit.vo.Employment;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEmployment {

    @Id
    @Column( unique = true )
    @Setter( value = AccessLevel.NONE )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Integer id;

    private Integer userId;

    @Enumerated(EnumType.ORDINAL)
    private Employment employment;

    public UserEmployment(Integer userId, Employment employment) {
        this.userId = userId;
        this.employment = employment;
    }
}