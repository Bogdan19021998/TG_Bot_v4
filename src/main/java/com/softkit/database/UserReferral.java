package com.softkit.database;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserReferral extends AbstractEntity{

    private Integer referralId;
}