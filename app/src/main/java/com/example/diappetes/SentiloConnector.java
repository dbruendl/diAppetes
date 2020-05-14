package com.example.diappetes;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.diappetes.sentilo.request.dto.BatteryObservationDTO;
import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;

import java.util.HashMap;
import java.util.Map;

public class SentiloConnector {

    private final String LOG_TAG = getClass().getSimpleName();

    public SentiloConnector(Context c) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        String url ="https://api-sentilo.diba.cat/data/vilanovailageltru@hygieia/hygieia_1_battery/";
        Map<String,String> headers = new HashMap<>();

        headers.put("IDENTITY_KEY", "c7337d3fc4ec28d0dddc81478808a8b6b82beb83110fcb00157cc0a711956475");

        BatteryObservationDTO batteryObservationDTO = new BatteryObservationDTO("56", "41.22157865201759 1.7300500669616392");
        SentiloRequestDTO sentiloRequestDTO = new SentiloRequestDTO(batteryObservationDTO);

        GsonRequest<SentiloRequestDTO> gsonRequest = new GsonRequest<>(url, headers, sentiloRequestDTO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOG_TAG, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, error.getMessage());
            }
        });

        queue.add(gsonRequest);
    }
}
