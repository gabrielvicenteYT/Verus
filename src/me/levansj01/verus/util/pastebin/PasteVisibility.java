/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.xml.bind.annotation.XmlEnum
 */
package me.levansj01.verus.util.pastebin;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum PasteVisibility {
    PUBLIC("0"),
    UNLISTED("1"),
    PRIVATE("2");

    private final String visibility;

    public String toString() {
        return this.visibility;
    }

    PasteVisibility(String visibility) {
        this.visibility = visibility;
    }
}

