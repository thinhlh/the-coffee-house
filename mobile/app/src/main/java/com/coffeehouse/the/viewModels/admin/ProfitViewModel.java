package com.coffeehouse.the.viewModels.admin;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.android.volley.Response;
import com.coffeehouse.the.services.remote.ProfitApi;

import org.json.JSONObject;

import java.util.Date;

public class ProfitViewModel extends ViewModel {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void getProfit(Date fromDate, Date toDate, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        new ProfitApi(context).getProfit(fromDate, toDate, listener, errorListener);
    }
}
