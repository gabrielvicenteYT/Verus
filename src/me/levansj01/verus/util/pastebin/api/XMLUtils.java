package me.levansj01.verus.util.pastebin.api;

import java.io.StringReader;
import javax.xml.bind.JAXB;

class XMLUtils {
    public static Object unMarshal(String string, Class<?> clazz) {
        return JAXB.unmarshal(new StringReader(string), clazz);
    }

    XMLUtils() {
    }
}

