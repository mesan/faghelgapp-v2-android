package no.mesan.util;

public class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || "".equalsIgnoreCase(s);
    }
}