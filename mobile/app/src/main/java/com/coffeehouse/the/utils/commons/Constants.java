package com.coffeehouse.the.utils.commons;

import java.text.Format;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class Constants {
    public static String SERVER_ENDPOINT = "https://the-coffee-house-server.herokuapp.com/";
    public static Map<String, String> BASE_HEADERS = new HashMap<String, String>() {{
        put("Authorization", "the-coffee-house");
    }};
    public static final int DELIVERY_LEVIED = 3000;

    public static Format currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
}
