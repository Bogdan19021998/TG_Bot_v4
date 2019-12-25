package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Employment {
    ONLY_OFFICE("Только офис в моем городе"),
    OFFICE_RELOCATION("Переезд для работы в офисе"),
    ONLY_REMOTE("Только удаленка");

    @Getter
    private final String description;

    public static boolean hasEnumWithName(String data) {
        return true;
    }
}