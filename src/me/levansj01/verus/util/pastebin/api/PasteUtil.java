package me.levansj01.verus.util.pastebin.api;

class PasteUtil {
    public static String getPasteKeyFromUrl(String string) {
        if (string == null) {
            return null;
        }
        if (!string.contains("https://pastebin.com/")) {
            throw new IllegalArgumentException("Not a valid paste bin url!");
        }
        return string.substring(string.indexOf("https://pastebin.com/") + "https://pastebin.com/".length());
    }

    private PasteUtil() {
    }
}

