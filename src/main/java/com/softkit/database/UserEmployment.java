package com.softkit.database;

import com.softkit.vo.Employment;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserEmployment {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    private Integer userId;

    @Enumerated(EnumType.ORDINAL)
    private Employment employment;

    public UserEmployment(Integer userId, Employment employment) {
        this.userId = userId;
        this.employment = employment;
    }
}