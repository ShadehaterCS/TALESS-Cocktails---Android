package com.authandroid_smartcookies.smartcookie;

public class Parser {
    //todo needs to wait for ingredients to be taken from db, fuck
    public static String[] parseSteps(String steps){
        return steps.split("([1-9])\\.");
    }
}
