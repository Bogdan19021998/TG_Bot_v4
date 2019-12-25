package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Experience {

    WITHOUT_EXPERIENCE("Без опыта"),
    LESS_YEAR("Меньше года"),
    YEAR("Минимум год"),
    TWO_YEARS("2+ года"),
    THREE_YEARS("3+ года"),
    FIVE_YEARS("5+ лет");

    @Getter
    private final String description;

    public static boolean hasEnumWithName(String data) {
        return true;
    }
}
