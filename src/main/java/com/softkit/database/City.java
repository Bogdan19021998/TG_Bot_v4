package com.softkit.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity(name="cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long cityId;

    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }
}
