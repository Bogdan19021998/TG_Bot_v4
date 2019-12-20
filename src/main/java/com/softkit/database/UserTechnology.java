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
public class UserTechnology {

    @Id
    @Column( unique = true )
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    private Technology technology;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}