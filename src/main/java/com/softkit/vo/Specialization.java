package com.softkit.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Specialization {

    NET(".NET"),
    ANDROID("Android"),
    C_OR_C_PLUS_PLUS("C / C++"),
    GOLANG("Golang"),
    IOS("iOS"),
    JAVA("Java"),
    FRONT_END_OR_JS("Front-End / JS"),
    NODE_JS("Node.js"),
    PHP("PHP"),
    PYTHON("Python"),
    RUBY_OR_RAILS("Ruby / Rails"),
    SCALA("Scala");

    @Getter
    private final String description;

    public static boolean hasEnumWithName(String data) {
        return true;
    }
}
