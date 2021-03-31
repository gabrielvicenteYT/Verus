/*
 * Decompiled with CFR 0.150.
 */
package me.levansj01.verus.util.hastebin;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.function.Function;

public class HTTPRequest {
    private HttpURLConnection httpURLConnection;
    private final URL url;

    public HTTPRequest doOutput(boolean bl) {
        this.httpURLConnection.setDoOutput(bl);
        return this;
    }

    public HTTPRequest open() throws IOException {
        this.httpURLConnection = (HttpURLConnection)this.url.openConnection();
        return this;
    }

    public Object disconnectAndReturn(HTTPRequestFunction hTTPRequestFunction) throws IOException {
        return hTTPRequestFunction.apply(this);
    }

    public HTTPRequest setRequestProperty(String string, String string2) {
        this.httpURLConnection.setRequestProperty(string, string2);
        return this;
    }

    public HTTPRequest setConnectTimeout(int n) {
        this.httpURLConnection.setConnectTimeout(n);
        return this;
    }

    public HTTPRequest requestMethod(String string) throws ProtocolException {
        this.httpURLConnection.setRequestMethod(string);
        return this;
    }

    public HTTPRequest setReadTimeout(int n) {
        this.httpURLConnection.setReadTimeout(n);
        return this;
    }

    public HTTPRequest useCaches(boolean bl) {
        this.httpURLConnection.setUseCaches(bl);
        return this;
    }

    public HTTPRequest inputStream(HTTPRequestConsumer hTTPRequestConsumer) throws IOException {
        hTTPRequestConsumer.accept(this.httpURLConnection.getInputStream());
        return this;
    }

    public HTTPRequest errorStream(Function function) throws IOException {
        function.apply(this.httpURLConnection.getErrorStream());
        return this;
    }

    public HttpURLConnection getHttpURLConnection() {
        return this.httpURLConnection;
    }

    public HTTPRequest outputStream(HTTPRequestConsumer hTTPRequestConsumer) throws IOException {
        hTTPRequestConsumer.accept(this.httpURLConnection.getOutputStream());
        return this;
    }

    public HTTPRequest disconnect() {
        this.httpURLConnection.disconnect();
        return this;
    }

    public HTTPRequest(String string) throws MalformedURLException {
        this.url = new URL(string);
    }

    public HTTPRequest doInput(boolean bl) {
        this.httpURLConnection.setDoInput(bl);
        return this;
    }

    @FunctionalInterface
    public static interface HTTPRequestConsumer {
        public void accept(Object var1) throws IOException;
    }

    @FunctionalInterface
    public static interface HTTPRequestFunction {
        public Object apply(HTTPRequest var1) throws IOException;
    }
}

