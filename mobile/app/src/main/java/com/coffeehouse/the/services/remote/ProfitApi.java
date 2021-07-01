package com.coffeehouse.the.services.remote;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ProfitApi {
    private final Context context;
    private final String ENDPOINT = "https://the-coffee-house-server.herokuapp.com/profit/";

    public ProfitApi(Context context) {
        this.context = context;
    }

    public void getProfit(Date fromDate, Date toDate, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String query = "?fromDate=" + new SimpleDateFormat("yyyy-MM-dd").format(fromDate) + "&toDate=" + new SimpleDateFormat("yyyy-MM-dd").format(toDate);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ENDPOINT + query, null, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                return new HashMap<String, String>() {{
                    put("Authorization", "the-coffee-house");
                }};
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                return super.parseNetworkError(volleyError);
            }

            @Override
            protected void deliverResponse(JSONObject response) {
                listener.onResponse(response);
            }
        };
        queue.add(request);
    }

}
