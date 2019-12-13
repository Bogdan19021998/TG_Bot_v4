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
@AllArgsConstructor
@Entity
public class Cities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cityId;

    private String cityName;

}
