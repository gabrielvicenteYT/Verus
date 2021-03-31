/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlElement
 */
package me.levansj01.verus.util.pastebin.api;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlElement;

public abstract class BasePaste {
    protected ZonedDateTime localPasteDate;
    protected LocalDateTime pasteDate;
    protected LocalDateTime expirationDate;
    @XmlElement(name="paste_date")
    private long dateTimestamp;
    @XmlElement(name="paste_expire_date")
    private long expireDateTimestamp;
    protected ZonedDateTime localExpirationDate;

    long getDateTimestamp() {
        return this.dateTimestamp;
    }

    void setExpireDateTimestamp(long l) {
        this.expireDateTimestamp = l;
    }

    void setLocalExpirationDate(ZonedDateTime zonedDateTime) {
        this.localExpirationDate = zonedDateTime;
    }

    void setLocalPasteDate(ZonedDateTime zonedDateTime) {
        this.localPasteDate = zonedDateTime;
    }

    void setPasteDate(LocalDateTime localDateTime) {
        this.pasteDate = localDateTime;
    }

    long getExpireDateTimestamp() {
        return this.expireDateTimestamp;
    }

    void setExpirationDate(LocalDateTime localDateTime) {
        this.expirationDate = localDateTime;
    }
}

