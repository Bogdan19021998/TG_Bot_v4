package com.softkit.database;

import com.softkit.vo.Employment;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserEmployment {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private Employment employment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}