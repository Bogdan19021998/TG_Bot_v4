package com.softkit.vo;

public class TextParser {

    public static int wordCount(String text) {
        text = fixSpacing(text);
        return text.trim().split(" ").length;
    }

    public static String fixSpacing(String text) {
        return text.replaceAll(" {2,}", " ");
    }


    public static boolean isLetterText(String text) {
//        todo regexp
        for (int i = 0; i < text.length(); i++) {
            if ( !Character.isLetter(text.charAt(i)) && text.charAt(i) != ' ' ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDigitsText(String text) {
        //        todo regexp or lib
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
