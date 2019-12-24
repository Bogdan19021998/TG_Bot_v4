package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum City {

    KIEV("Киев"),
    LVIV("Львов"),
    KHARKIV("Харьков"),
    ODESA("Одесса"),
    DNIPR("Днепр"),
    ZAPOROZHE("Запорожье");

    @Getter
    private final String description;
}
