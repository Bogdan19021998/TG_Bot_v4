package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EnglishLevel {

    BEGINNER_OR_ELEMENTARY("Beginner/Elementary"),
    PRE_INTERMEDIATE("Pre-Intermediate"),
    INTERMEDIATE("Intermediate"),
    UPPER_INTERMEDIATE("Upper Intermediate"),
    ADVANCED_OR_FLUENT("Advanced/Fluent");

    @Getter
    private final String description;
}
