package me.levansj01.verus.util.pastebin;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import me.levansj01.verus.util.pastebin.api.BasePaste;
import me.levansj01.verus.util.pastebin.api.PasteBinApiFactory;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlRootElement
public class Paste
extends BasePaste {
    private PasteExpiration expiration;
    @XmlElement(name="paste_format_short")
    private PasteHighLight highLight;
    @XmlElement(name="paste_hits")
    private long hits;
    @XmlElement(name="paste_private")
    private PasteVisibility visibility;
    private String content;
    @XmlElement(name="paste_key")
    private String key;
    @XmlElement(name="paste_size")
    private long size;
    @XmlElement(name="paste_title")
    private String title;
    @XmlElement(name="paste_url")
    private String url;

    public ZonedDateTime getLocalExpirationDate() {
        return this.localExpirationDate;
    }

    public void setContent(String string) {
        this.content = string;
    }

    public long getHits() {
        return this.hits;
    }

    public void setHighLight(PasteHighLight pasteHighLight) {
        this.highLight = pasteHighLight;
    }

    public String getKey() {
        return this.key;
    }

    public Paste() {
    }

    public long getSize() {
        return this.size;
    }

    public void setKey(String string) {
        this.key = string;
    }

    public void setVisibility(PasteVisibility pasteVisibility) {
        this.visibility = pasteVisibility;
    }

    public String paste(PasteBin pasteBin) {
        return pasteBin.createPaste(this);
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public PasteHighLight getHighLight() {
        return this.highLight;
    }

    public Paste(String string, String string2, PasteHighLight pasteHighLight, PasteExpiration pasteExpiration, PasteVisibility pasteVisibility) {
        Objects.requireNonNull(string2);
        this.title = string;
        this.content = string2;
        this.expiration = pasteExpiration;
        this.visibility = pasteVisibility;
        this.highLight = pasteHighLight;
    }

    private Paste(Builder builder) {
        this.setTitle(builder.title);
        this.setContent(builder.content);
        this.setHighLight(builder.highLight);
        this.setExpiration(builder.expiration);
        this.setVisibility(builder.visibility);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Paste{");
        stringBuilder.append("key='").append(this.key).append('\'');
        stringBuilder.append(", title='").append(this.title).append('\'');
        stringBuilder.append(", highLight=").append((Object)this.highLight);
        stringBuilder.append(", visibility=").append((Object)this.visibility);
        stringBuilder.append(", expiration=").append((Object)this.expiration);
        stringBuilder.append(", localExpirationDate=").append(this.getLocalExpirationDate());
        stringBuilder.append(", localPasteDate=").append(this.getLocalPasteDate());
        stringBuilder.append(", url='").append(this.url).append('\'');
        stringBuilder.append(", size=").append(this.size);
        stringBuilder.append(", hits=").append(this.hits);
        stringBuilder.append('}');
        return String.valueOf(stringBuilder);
    }

    public LocalDateTime getPasteDate() {
        return this.pasteDate;
    }

    public void setExpiration(PasteExpiration pasteExpiration) {
        this.expiration = pasteExpiration;
    }

    public String getUrl() {
        return this.url;
    }

    public String getTitle() {
        return this.title;
    }

    public PasteVisibility getVisibility() {
        return this.visibility;
    }

    public String fetchContent() {
        this.content = PasteBinApiFactory.createDefaultImplementation().getPasteContent(this);
        return this.content;
    }

    public String paste(AccountCredentials accountCredentials) {
        return PasteBinApiFactory.createDefaultImplementation().createPaste(accountCredentials, this);
    }

    public ZonedDateTime getLocalPasteDate() {
        return this.localPasteDate;
    }

    public LocalDateTime getExpirationDate() {
        return this.expirationDate;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public PasteExpiration getExpiration() {
        return this.expiration;
    }

    public Paste(String string) {
        Objects.requireNonNull(string);
        this.key = string;
    }

    public String getContent() {
        return this.content;
    }

    public void setUrl(String string) {
        this.url = string;
    }

    public static Builder newBuilder(Paste paste) {
        Builder builder = new Builder();
        builder.title = paste.title;
        builder.content = paste.content;
        builder.highLight = paste.highLight;
        builder.expiration = paste.expiration;
        builder.visibility = paste.visibility;
        return builder;
    }

    public static final class Builder {
        private PasteVisibility visibility;
        private String title;
        private PasteExpiration expiration;
        private String content;
        private PasteHighLight highLight;

        public Builder withTitle(String string) {
            this.title = string;
            return this;
        }

        public Builder withVisibility(PasteVisibility pasteVisibility) {
            this.visibility = pasteVisibility;
            return this;
        }

        public Builder withHighLight(PasteHighLight pasteHighLight) {
            this.highLight = pasteHighLight;
            return this;
        }

        public Builder withContent(String string) {
            this.content = string;
            return this;
        }

        private Builder() {
        }

        public Builder withExpiration(PasteExpiration pasteExpiration) {
            this.expiration = pasteExpiration;
            return this;
        }

        public Paste build() {
            return new Paste(this);
        }
    }
}

