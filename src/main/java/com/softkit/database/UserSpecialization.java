package com.softkit.database;

import com.softkit.vo.Specialization;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserSpecialization extends AbstractEntity {

    @Enumerated(EnumType.ORDINAL)
    private Specialization specialization;
}