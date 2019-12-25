package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum City {

    KIEV("Киев"),
    LVIV("Львов"),
    KHARKIV("Харьков"),
    ODESA("Одесса"),
    DNIPRO("Днепр"),
    ZAPORIZHZHYA("Запорожье");

    @Getter
    private final String description;

    public static boolean hasEnumWithName(String data) {
        try {
            City.valueOf( data );
            return true;
        } catch ( IllegalArgumentException ignored) {}
        return false;
    }
}
