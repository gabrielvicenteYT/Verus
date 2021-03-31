package me.levansj01.verus.util.pastebin.api;

enum PasteBinApiOptions {
    PASTE("paste"),
    LIST("list"),
    TRENDS("trends"),
    DELETE("delete"),
    USER_DETAILS("userdetails"),
    SHOW_PASTE("show_paste");

    private final String option;

    public String toString() {
        return this.option;
    }

    PasteBinApiOptions(String option) {
        this.option = option;
    }
}

