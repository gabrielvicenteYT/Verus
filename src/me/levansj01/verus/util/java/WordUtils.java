package me.levansj01.verus.util.java;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;

public class WordUtils {

    public static String initials(String string) {
        return WordUtils.initials(string, null);
    }

    public static String abbreviate(String string, int n, int n2, String string2) {
        if (string == null) {
            return null;
        }
        if (string.length() == 0) {
            return "";
        }
        if (n > string.length()) {
            n = string.length();
        }
        if (n2 == -1 || n2 > string.length()) {
            n2 = string.length();
        }
        if (n2 < n) {
            n2 = n;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int n3 = StringUtils.indexOf((String)string, (String)" ", (int)n);
        if (n3 == -1) {
            stringBuffer.append(string.substring(0, n2));
            if (n2 != string.length()) {
                stringBuffer.append(StringUtils.defaultString((String)string2));
            }
        } else if (n3 > n2) {
            stringBuffer.append(string.substring(0, n2));
            stringBuffer.append(StringUtils.defaultString((String)string2));
        } else {
            stringBuffer.append(string.substring(0, n3));
            stringBuffer.append(StringUtils.defaultString((String)string2));
        }
        return stringBuffer.toString();
    }

    public static String capitalizeFully(String string) {
        return WordUtils.capitalizeFully(string, null);
    }

    public static String wrap(String string, int n) {
        return WordUtils.wrap(string, n, null, false);
    }

    public static String swapCase(String string) {
        int n;
        if (string == null || (n = string.length()) == 0) {
            return string;
        }
        StringBuffer stringBuffer = new StringBuffer(n);
        boolean bl = true;
        char c = '\u0000';
        char c2 = '\u0000';
        for (int i = 0; i < n; ++i) {
            c = string.charAt(i);
            c2 = Character.isUpperCase(c) ? Character.toLowerCase(c) : (Character.isTitleCase(c) ? Character.toLowerCase(c) : (Character.isLowerCase(c) ? (bl ? Character.toTitleCase(c) : Character.toUpperCase(c)) : c));
            stringBuffer.append(c2);
            bl = Character.isWhitespace(c);
        }
        return stringBuffer.toString();
    }

    public static String capitalize(String string, char[] arrc) {
        int n;
        int n2 = n = arrc == null ? -1 : arrc.length;
        if (string == null || string.length() == 0 || n == 0) {
            return string;
        }
        int n3 = string.length();
        StringBuffer stringBuffer = new StringBuffer(n3);
        boolean bl = true;
        for (int i = 0; i < n3; ++i) {
            char c = string.charAt(i);
            if (WordUtils.isDelimiter(c, arrc)) {
                stringBuffer.append(c);
                bl = true;
                continue;
            }
            if (bl) {
                stringBuffer.append(Character.toTitleCase(c));
                bl = false;
                continue;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public static String initials(String string, char[] arrc) {
        if (string == null || string.length() == 0) {
            return string;
        }
        if (arrc != null && arrc.length == 0) {
            return "";
        }
        int n = string.length();
        char[] arrc2 = new char[n / 2 + 1];
        int n2 = 0;
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (WordUtils.isDelimiter(c, arrc)) {
                bl = true;
                continue;
            }
            if (!bl) continue;
            arrc2[n2++] = c;
            bl = false;
        }
        return new String(arrc2, 0, n2);
    }

    public static String wrap(String string, int n, String string2, boolean bl) {
        if (string == null) {
            return null;
        }
        if (string2 == null) {
            string2 = SystemUtils.LINE_SEPARATOR;
        }
        if (n < 1) {
            n = 1;
        }
        int n2 = string.length();
        int n3 = 0;
        StringBuffer stringBuffer = new StringBuffer(n2 + 32);
        while (n2 - n3 > n) {
            if (string.charAt(n3) == ' ') {
                ++n3;
                continue;
            }
            int n4 = string.lastIndexOf(32, n + n3);
            if (n4 >= n3) {
                stringBuffer.append(string.substring(n3, n4));
                stringBuffer.append(string2);
                n3 = n4 + 1;
                continue;
            }
            if (bl) {
                stringBuffer.append(string.substring(n3, n + n3));
                stringBuffer.append(string2);
                n3 += n;
                continue;
            }
            n4 = string.indexOf(32, n + n3);
            if (n4 >= 0) {
                stringBuffer.append(string.substring(n3, n4));
                stringBuffer.append(string2);
                n3 = n4 + 1;
                continue;
            }
            stringBuffer.append(string.substring(n3));
            n3 = n2;
        }
        stringBuffer.append(string.substring(n3));
        return stringBuffer.toString();
    }

    private static boolean isDelimiter(char c, char[] arrc) {
        if (arrc == null) {
            return Character.isWhitespace(c);
        }
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            if (c != arrc[i]) continue;
            return true;
        }
        return false;
    }

    public static String capitalizeFully(String string, char[] arrc) {
        int n;
        int n2 = n = arrc == null ? -1 : arrc.length;
        if (string == null || string.length() == 0 || n == 0) {
            return string;
        }
        string = string.toLowerCase();
        return WordUtils.capitalize(string, arrc);
    }

    public static String capitalize(String string) {
        return WordUtils.capitalize(string, null);
    }

    public static String uncapitalize(String string) {
        return WordUtils.uncapitalize(string, null);
    }

    public static String uncapitalize(String string, char[] arrc) {
        int n;
        int n2 = n = arrc == null ? -1 : arrc.length;
        if (string == null || string.length() == 0 || n == 0) {
            return string;
        }
        int n3 = string.length();
        StringBuffer stringBuffer = new StringBuffer(n3);
        boolean bl = true;
        for (int i = 0; i < n3; ++i) {
            char c = string.charAt(i);
            if (WordUtils.isDelimiter(c, arrc)) {
                stringBuffer.append(c);
                bl = true;
                continue;
            }
            if (bl) {
                stringBuffer.append(Character.toLowerCase(c));
                bl = false;
                continue;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }
}

