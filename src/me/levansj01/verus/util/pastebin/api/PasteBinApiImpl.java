package me.levansj01.verus.util.pastebin.api;

import java.util.*;
import java.util.function.Function;
import me.levansj01.verus.util.pastebin.AccountCredentials;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.UserInformation;

//TODO: Fix this. I removed everything temporarily.

public class PasteBinApiImpl implements PasteBinApi {

    private Function postFunc;

    @Override
    public List listUserPastes(AccountCredentials accountCredentials, int n) {
        return null;
    }

    @Override
    public boolean deletePaste(AccountCredentials accountCredentials, String string) {
        return false;
    }

    public PasteBinApiImpl() {
        this.postFunc = map -> WebUtils.post("https://pastebin.com/api/api_post.php", null);
    }

    @Override
    public List listTrends(AccountCredentials accountCredentials) {
        return null;
    }

    @Override
    public String createPaste(AccountCredentials accountCredentials, Paste paste) {
        return null;
    }

    @Override
    public String fetchUserKey(AccountCredentials accountCredentials) {
        return null;
    }

    public void updateUserSessionKey(AccountCredentials accountCredentials) {
        if (!accountCredentials.getUserSessionKey().isPresent()) {
            accountCredentials.setUserKey(Optional.ofNullable(this.fetchUserKey(accountCredentials)));
        }
    }

    private Object doPost(AccountCredentials accountCredentials, PasteBinApiOptions pasteBinApiOptions, Function function) {
        return null;
    }

    @Override
    public String getPasteContent(AccountCredentials accountCredentials, Paste paste) {
        return null;
    }

    @Override
    public String getPasteContent(Paste paste) {
        return null;
    }

    @Override
    public UserInformation fetchUserInformation(AccountCredentials accountCredentials) {
        return null;
    }

    @Override
    public List listUserPastes(AccountCredentials accountCredentials) {
        return this.listUserPastes(accountCredentials, 50);
    }

    @Override
    public boolean deletePaste(AccountCredentials accountCredentials, Paste paste) {
        Objects.requireNonNull(paste);
        Objects.requireNonNull(paste.getKey(), "The key to the paste being deleted can't be null!");
        return this.deletePaste(accountCredentials, paste.getKey());
    }
}

