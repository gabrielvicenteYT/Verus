/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package me.levansj01.verus.util.pastebin;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import me.levansj01.verus.util.pastebin.AccountCredentials;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.UserInformation;
import me.levansj01.verus.util.pastebin.api.PasteBinApi;
import me.levansj01.verus.util.pastebin.api.PasteBinApiFactory;
import org.jetbrains.annotations.NotNull;

public class PasteBin {
    private final /* synthetic */ AccountCredentials accountCredentials;
    private /* synthetic */ Optional user;
    private /* synthetic */ PasteBinApi pasteBinApi;

    public PasteBin(@NotNull AccountCredentials accountCredentials, @NotNull PasteBinApi pasteBinApi) {
        Objects.requireNonNull(accountCredentials);
        Objects.requireNonNull(pasteBinApi);
        this.accountCredentials = accountCredentials;
        this.pasteBinApi = pasteBinApi;
    }

    public UserInformation fetchUserInformation() {
        this.user = Optional.ofNullable(this.pasteBinApi.fetchUserInformation(this.accountCredentials));
        return (UserInformation)this.user.orElseThrow(() -> new RuntimeException("Couldn't fetch the user information :("));
    }

    public AccountCredentials getAccountCredentials() {
        return this.accountCredentials;
    }

    @NotNull
    public List listUserPastes(int n) {
        return this.pasteBinApi.listUserPastes(this.accountCredentials, n);
    }

    public String getPasteContent(@NotNull Paste paste) {
        return this.pasteBinApi.getPasteContent(this.accountCredentials, paste);
    }

    public List listTrendingPastes() {
        return this.pasteBinApi.listTrends(this.accountCredentials);
    }

    public boolean deletePaste(@NotNull Paste paste) {
        return this.pasteBinApi.deletePaste(this.accountCredentials, paste);
    }

    public String createPaste(@NotNull Paste paste) {
        return this.pasteBinApi.createPaste(this.accountCredentials, paste);
    }

    public PasteBin(@NotNull AccountCredentials accountCredentials) {
        this(accountCredentials, PasteBinApiFactory.createDefaultImplementation());
    }

    @NotNull
    public List listUserPastes() {
        return this.pasteBinApi.listUserPastes(this.accountCredentials);
    }

    public Optional getUserInformation() {
        return this.user;
    }
}

