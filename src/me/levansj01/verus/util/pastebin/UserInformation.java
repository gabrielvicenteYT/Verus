package me.levansj01.verus.util.pastebin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import me.levansj01.verus.util.pastebin.AccountType;
import me.levansj01.verus.util.pastebin.PasteExpiration;
import me.levansj01.verus.util.pastebin.PasteHighLight;
import me.levansj01.verus.util.pastebin.PasteVisibility;

@XmlRootElement
@XmlAccessorType(value=XmlAccessType.FIELD)
public class UserInformation {
    @XmlElement(name="user_website")
    private String websiteUrl;
    @XmlElement(name="user_expiration")
    private PasteExpiration defaultPasteExpiration;
    @XmlElement(name="user_email")
    private String email;
    @XmlElement(name="user_format_short")
    private PasteHighLight defaultHighLight;
    @XmlElement(name="user_private")
    private PasteVisibility defaultPasteVisility;
    @XmlElement(name="user_account_type")
    private AccountType accountType;
    @XmlElement(name="user_name")
    private String username;
    @XmlElement(name="user_location")
    private String location;
    @XmlElement(name="user_avatar_url")
    private String userAvatarUrl;

    public PasteHighLight getDefaultHighLight() {
        return this.defaultHighLight;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public PasteVisibility getDefaultPasteVisility() {
        return this.defaultPasteVisility;
    }

    public String getWebsiteUrl() {
        return this.websiteUrl;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public PasteExpiration getDefaultPasteExpiration() {
        return this.defaultPasteExpiration;
    }

    public String getUserAvatarUrl() {
        return this.userAvatarUrl;
    }

    public String getLocation() {
        return this.location;
    }
}

