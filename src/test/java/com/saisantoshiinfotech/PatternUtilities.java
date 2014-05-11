package com.saisantoshiinfotech;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatternUtilities {


    public static Pattern getPattern(String pattern) {
        return Pattern.compile(pattern, Pattern.DOTALL);
    }

    public static Boolean findMatch(String source, Pattern pattern) {
        Matcher matcher = pattern.matcher(source);
        return matcher.find();
    }

    public static Boolean ifListContainsPattern(List<String> list, Pattern pattern) {

        for (String value : list) {
            System.out.println("For debugging: The list values :" + value);
        }

        Boolean flag = false;
        for (String value : list) {
            Matcher matcher = pattern.matcher(value);

            if (matcher.find()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static int countOfPatternInList(List<String> list, Pattern pattern) {

        for (String value : list) {
            System.out.println("For debugging: The list values :" + value);
        }

        int counter = 0;

        for (String value : list) {
            Matcher matcher = pattern.matcher(value);

            if (matcher.find()) {
                counter++;
            }
        }
        return counter;
    }

}

