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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserInfoApi {

        private final Context context;
        private final String ENDPOINT = "https://the-coffee-house-server.herokuapp.com/user-info/";

        public UserInfoApi(Context context) {
            this.context = context;
        }

        public void getUserInfo(String id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ENDPOINT + id, null, listener, errorListener) {
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
