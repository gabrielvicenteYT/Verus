/*
 * Decompiled with CFR 0.150.
 */
package me.levansj01.verus.util.hastebin;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import me.levansj01.verus.util.hastebin.HTTPRequest;
import me.levansj01.verus.util.java.SafeReflection;

public class HasteBin {
    public static final String NEWENDPOINT;
    private static final Object jsonParser;
    public static final String ENDPOINT;
    public static final String[] ENDPOINTS;
    public static final String PASTIE;
    private static final Class<?> jsonParserClass;
    private static final Method parseMethod;

    public static String getPaste(String string, String string2) throws IOException {
        return (String)new HTTPRequest(String.valueOf(new StringBuilder().append(string2).append("raw/").append(string))).open().doOutput(true).disconnectAndReturn(hTTPRequest -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(hTTPRequest.getHttpURLConnection().getInputStream()));
            String str = "";
            while (bufferedReader.ready()) {
                String read = bufferedReader.readLine();
                if (read.contains("package")) continue;
                if (str.equals("")) {
                    str = read;
                    continue;
                }
                str = str + "\n" + read;
            }
            return str;
        });
    }

    public static String paste(String string, String string2) throws IOException {
        return (String)new HTTPRequest(String.valueOf(string2 + "documents")).open().requestMethod("POST").setConnectTimeout(750).setReadTimeout(750).setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0").setRequestProperty("Content-Type", "text/plain").doOutput(true).outputStream(outputStream -> new DataOutputStream((OutputStream)outputStream).writeBytes(string)).disconnectAndReturn(hTTPRequest -> String.valueOf(new StringBuilder().append(string2).append(HasteBin.parseAndGet(HasteBin.toRaw(hTTPRequest.getHttpURLConnection().getInputStream()), "key"))));
    }

    private static String toRaw(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);){
            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
        }
        return String.valueOf(stringBuilder);
    }

    static {
        NEWENDPOINT = "https://hasteb.in/";
        ENDPOINT = "https://hastebin.com/";
        PASTIE = "https://pastie.io/";
        ENDPOINTS = new String[]{"https://pastie.io/", "https://hasteb.in/", "https://hastebin.com/"};
        jsonParserClass = SafeReflection.findClass("com.google.gson.JsonParser", "org.bukkit.craftbukkit.libs.com.google.gson.JsonParser", "net.minecraft.util.com.google.gson.JsonParser", "us.myles.viaversion.libs.gson.JsonParser");
        jsonParser = SafeReflection.fetchConstructor(SafeReflection.constructor(jsonParserClass, new Class[0]), new Object[0]);
        parseMethod = SafeReflection.access(jsonParserClass, "parse", String.class);
    }

    private static String parseAndGet(String string, String string2) {
        Object t = SafeReflection.execute(parseMethod, jsonParser, string);
        Method method = SafeReflection.access(t.getClass(), "get", String.class);
        t = SafeReflection.execute(method, t, string2);
        method = SafeReflection.access(t.getClass(), "getAsString", new Class[0]);
        t = SafeReflection.execute(method, t, new Object[0]);
        return (String)t;
    }
}

