package com.softkit.utils;

import org.springframework.lang.NonNull;

import java.util.Base64;

public class TextParser {

    public static int wordCount(String text) {
        text = fixSpacing(text);
        return text.trim().split(" ").length;
    }

    public static String fixSpacing(String text) {
        return text.replaceAll(" {2,}", " ");
    }


    public static String  encryptingText( String text )
    {
        return new String( Base64.getEncoder().encode( text.getBytes() ));
    }

    public static String decryptingText( String encryptingText )
    {
        return new String( Base64.getDecoder().decode( encryptingText ));
    }

    public static String createReferralLink( Integer userId ) {
        // todo you must take name bot with other class.

        String nameBot = "SK_assistent_bot";
        return "https://telegram.me/" + nameBot + "/?start=" + encryptingText( userId + "" );
    }

    public static boolean isLetterText(@NonNull String text) {
        for (int i = 0; i < text.length(); i++) {
            if ( !Character.isLetter(text.charAt(i)) && text.charAt(i) != ' ' ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEngLetterDigitsSpecialSymbolsText(@NonNull String text) {
        for (int i = 0; i < text.length(); i++) {
            if ((int) text.charAt(i) < 32 || (int) text.charAt(i) > 126) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIntegerText(@NonNull String text) {
        for (int i = 0; i < text.length(); i++) {
            if ( !Character.isDigit(text.charAt(i)) ) {
                return false;
            }
        }
        return text.length() < 11 && Long.parseLong(text) <= Integer.MAX_VALUE;
    }

}
