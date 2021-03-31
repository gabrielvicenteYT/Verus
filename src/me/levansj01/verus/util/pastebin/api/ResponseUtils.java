package me.levansj01.verus.util.pastebin.api;

import java.util.Optional;

class ResponseUtils {
    private ResponseUtils() {
    }

    public static Optional<?> requiresValidResponse(Optional<?> optional) {
        ResponseUtils.validateResponse(optional);
        return optional;
    }

    public static void validateResponse(Optional optional) {
        if (optional == null || !optional.isPresent()) {
            throw new RuntimeException("Empty response");
        }
        String string = (String)optional.get();
        if (string.toLowerCase().contains("bad api request,"))
            throw new RuntimeException("Error: " + string);
    }
}

