/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package me.levansj01.verus.util.pastebin;

import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AccountCredentials {
    private /* synthetic */ Optional userName;
    private /* synthetic */ Optional userKey;
    private /* synthetic */ String devKey;
    private /* synthetic */ Optional password;

    public Optional getUserName() {
        return this.userName;
    }

    public void setUserKey(Optional optional) {
        this.userKey = optional;
    }

    public Optional getUserSessionKey() {
        return this.userKey;
    }

    public AccountCredentials(@NotNull String string) {
        this(string, null, null);
    }

    public Optional getPassword() {
        return this.password;
    }

    public AccountCredentials(@NotNull String string, @Nullable String string2, @Nullable String string3) {
        Objects.requireNonNull(string, " The dev key cannot be null!");
        this.devKey = string;
        this.password = Optional.ofNullable(string3);
        this.userName = Optional.ofNullable(string2);
        this.userKey = Optional.empty();
    }

    public String getDevKey() {
        return this.devKey;
    }
}

