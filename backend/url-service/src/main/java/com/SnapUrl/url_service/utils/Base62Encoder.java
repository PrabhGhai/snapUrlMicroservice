package com.SnapUrl.url_service.utils;

public class Base62Encoder {

    private static final String BASE62 =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String encode(long value) {

        if (value == 0) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        while (value != 0) {
            int remainder = Math.floorMod(value, 62);
            sb.append(BASE62.charAt(remainder));
            value = value / 62;
        }

        return sb.reverse().toString();
    }
}

