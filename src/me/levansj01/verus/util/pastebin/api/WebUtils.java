package me.levansj01.verus.util.pastebin.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class WebUtils {
    private static final String USER_AGENT;

    public static Optional get(String string, Map<?, ?> map) {
        return WebUtils.get(string, map, false);
    }

    static String doRequest(String string, HttpURLConnection httpURLConnection, String string2, boolean bl) throws IOException {
        int n;
        Object object;
        if (StringUtils.isNotNullNorEmpty(string2)) {
            httpURLConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            object = null;
            try {
                dataOutputStream.writeBytes(string2);
            }
            catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            }
            finally {
                if (object != null) {
                    try {
                        dataOutputStream.close();
                    }
                    catch (Throwable throwable) {
                        ((Throwable)object).addSuppressed(throwable);
                    }
                } else {
                    dataOutputStream.close();
                }
            }
        }
        if ((n = httpURLConnection.getResponseCode()) != 200) {
            throw new RuntimeException(String.format("Error posting to %s using the params: %s", string, string2));
        }
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), Charset.forName("UTF-8")));){
            object = bufferedReader.lines().collect(Collectors.joining(bl ? "\n" : ""));
        }
        return object.toString();
    }

    public static Optional get(String string, Map map, boolean bl) {
        try {
            URL uRL = new URL(String.valueOf(new StringBuilder().append(string).append("?").append(WebUtils.getParams(map))));
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.addRequestProperty("Accept", Locale.getDefault().getLanguage());
            httpURLConnection.setConnectTimeout(30000);
            String string2 = WebUtils.doRequest(string, httpURLConnection, null, bl);
            return Optional.of(string2);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    WebUtils() {
    }

    //TODO: Make this not return null
    static String getParams(Map map) {
        return null;
    }

    public static Optional<String> post(String string, Map map) {
        try {
            URL uRL = new URL(string);
            HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
            httpURLConnection.addRequestProperty("Accept", Locale.getDefault().getLanguage());
            httpURLConnection.setConnectTimeout(30000);
            String string2 = WebUtils.getParams(map);
            String string3 = WebUtils.doRequest(string, httpURLConnection, string2, false);
            return Optional.of(string3);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    static String encodeUTF8(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return "";
        }
    }

    static {
        USER_AGENT = "Mozilla/5.0";
    }
}

