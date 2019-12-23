package com.softkit.database;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name="city_table")
@Data
@NoArgsConstructor
@NonNull
@EqualsAndHashCode
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private Long cityId;

    @NotNull
    private String cityName;

    private Timestamp dateAdded;

    public City(String cityName) {
        this.cityName = cityName;
        this.dateAdded = new Timestamp(System.currentTimeMillis());
    }
}