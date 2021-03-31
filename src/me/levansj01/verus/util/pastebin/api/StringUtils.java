package me.levansj01.verus.util.pastebin.api;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isNotNullNorEmpty(String string) {
        return string != null && string.length() > 0;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }
}

