package com.softkit.database;

import com.softkit.vo.Specialization;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NotNull
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserSpecialization extends AbstractEntity {

    private Integer userId;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;
}