package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Emplyment {

    ONLY_OFFICE("Только офис в моем городе"),
    OFFICE_RELOCATION("Переезд для работы в офисе"),
    ONLY_REMOUTE("Только удаленка");

    @Getter
    private final String description;
}
