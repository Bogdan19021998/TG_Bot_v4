package com.softkit.database;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="city_table")
@Data
@NoArgsConstructor
@NonNull
@EqualsAndHashCode
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Setter( value = AccessLevel.NONE )
    @EqualsAndHashCode.Include
    private long cityId;

    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }
}
