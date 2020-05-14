package com.example.diappetes;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SentiloConnector {

    public SentiloConnector(Context c) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        String url ="https://api-sentilo.diba.cat/data/vilanovailageltru@hygieia/hygieia_1_battery/";
        Map<String,String> headers = new HashMap<>();

        headers.put("IDENTITY_KEY", "c7337d3fc4ec28d0dddc81478808a8b6b82beb83110fcb00157cc0a711956475");

        GsonRequest gsonRequest = new GsonRequest(url, headers, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                return;
            }
        });

        queue.add(gsonRequest);
    }
}
