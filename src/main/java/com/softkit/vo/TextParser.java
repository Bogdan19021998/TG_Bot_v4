package com.softkit.vo;

public class TextParser {

    public static int wordCount(String text) {
        text = text.replaceAll(" {2,}", " "); // проверка на несколько пробелов
        return text.trim().split(" ").length;
    }

    public static boolean isLetterText(String text) {
        for (int i = 0; i < text.length(); i++) {
            if ( !Character.isLetter(text.charAt(i)) || text.charAt(i) != ' ' ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigitsText(String text) {
        for (int i = 0; i < text.length(); i++) {
            if ( !Character.isDigit(text.charAt(i)) ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEnglishText(String text) {
        return false;
    }
}
