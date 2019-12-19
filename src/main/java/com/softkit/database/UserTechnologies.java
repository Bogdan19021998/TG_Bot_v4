package com.softkit.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode
public class UserTechnologies {

    @Id
    @Column( unique = true )
    @EqualsAndHashCode.Include
    private Integer id;

    private String technology;
}
