package com.coffeehouse.the.utils;

import java.util.HashMap;
import java.util.Map;

public final class Constants {
    public static String SERVER_ENDPOINT = "https://the-coffee-house-server.herokuapp.com/";
    public static Map<String, String> BASE_HEADERS = new HashMap<String, String>() {{
        put("Authorization", "the-coffee-house");
    }};
}
