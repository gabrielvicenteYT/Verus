package me.levansj01.verus.util.pastebin.api;

import java.util.List;
import me.levansj01.verus.util.pastebin.AccountCredentials;
import me.levansj01.verus.util.pastebin.Paste;
import me.levansj01.verus.util.pastebin.UserInformation;

public interface PasteBinApi {
    UserInformation fetchUserInformation(AccountCredentials var1);

    boolean deletePaste(AccountCredentials var1, String var2);

    String createPaste(AccountCredentials var1, Paste var2);

    List listUserPastes(AccountCredentials var1);

    String getPasteContent(Paste var1);

    List listTrends(AccountCredentials var1);

    boolean deletePaste(AccountCredentials var1, Paste var2);

    List listUserPastes(AccountCredentials var1, int var2);

    String getPasteContent(AccountCredentials var1, Paste var2);

    String fetchUserKey(AccountCredentials var1);
}

