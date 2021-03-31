package me.levansj01.verus.util.pastebin.api;

enum PasteBinApiParams {
    DEV_KEY("api_dev_key"),
    PASTE_CODE("api_paste_code"),
    PASTE_PRIVATE("api_paste_private"),
    PASTE_NAME("api_paste_name"),
    PASTE_EXPIRE_DATE("api_paste_expire_date"),
    PASTE_FORMAT("api_paste_format"),
    USER_KEY("api_user_key"),
    OPTION("api_option"),
    USER_NAME("api_user_name"),
    USER_PASSWORD("api_user_password"),
    LIST_RESULT_LIMIT("api_results_limit"),
    UNIQUE_PASTE_KEY("api_paste_key");

    private final String param;

    PasteBinApiParams(String param) {
        this.param = param;
    }

    public String toString() {
        return this.param;
    }
}

