package me.levansj01.verus.util.pastebin;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum PasteExpiration {
    NEVER("N", 0L),
    TEN_MINUTES("10M", 600L),
    ONE_HOUR("1H", 3600L),
    ONE_DAY("1D", 86400L),
    ONE_WEEK("1W", 604800L),
    TWO_WEEKS("2W", 1209600L),
    ONE_MONTH("1M", -1L);

    private final long seconds;
    private final String expiration;
    private static final Map cacheBySeconds;

    public static PasteExpiration getBySeconds(long l) {
        return (PasteExpiration) cacheBySeconds.getOrDefault(l, ONE_MONTH);
    }

    static {
        cacheBySeconds = new HashMap();
        for (PasteExpiration pasteExpiration : PasteExpiration.values()) {
            cacheBySeconds.put(pasteExpiration.seconds, pasteExpiration);
        }
    }

    public String toString() {
        return this.expiration;
    }

    PasteExpiration(String one, long two) {
        this.expiration = one;
        this.seconds = two;
    }
}

