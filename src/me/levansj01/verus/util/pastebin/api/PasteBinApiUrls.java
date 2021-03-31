/*
 * Decompiled with CFR 0.150.
 */
package me.levansj01.verus.util.pastebin.api;

class PasteBinApiUrls {
    public static final /* synthetic */ String PASTE_RAW_URL;
    public static final /* synthetic */ String API_POST_URL;
    public static final /* synthetic */ String PASTEBIN_RESULT_URL;
    public static final /* synthetic */ String API_PASTE_CONTENT_URL;
    public static final /* synthetic */ String API_LOGIN_URL;

    private PasteBinApiUrls() {
    }

    static {
        API_LOGIN_URL = "https://pastebin.com/api/api_login.php";
        PASTEBIN_RESULT_URL = "https://pastebin.com/";
        PASTE_RAW_URL = "https://pastebin.com/raw.php";
        API_PASTE_CONTENT_URL = "https://pastebin.com/api/api_raw.php";
        API_POST_URL = "https://pastebin.com/api/api_post.php";
    }
}

