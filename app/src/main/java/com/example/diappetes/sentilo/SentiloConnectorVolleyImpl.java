package com.example.diappetes.sentilo;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.diappetes.GsonRequest;
import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;

import java.util.HashMap;
import java.util.Map;

public class SentiloConnectorVolleyImpl implements SentiloConnector {

    private static final String HEADER_KEY_SENTILO_IDENTITY_KEY = "IDENTITY_KEY";
    private static final String SENTILO_URL = "https://api-sentilo.diba.cat/data/vilanovailageltru@hygieia/hygieia_1_battery/";
    private static final Map<String, String> requestHeaders = new HashMap<>();

    private final Response.Listener<String> responseListener;
    private final Response.ErrorListener errorListener;
    private final RequestQueue requestQueue;

    /**
     * @param identityKey used to authenticate against the sentilo API
     */
    public SentiloConnectorVolleyImpl(RequestQueue requestQueue, String identityKey) {
        this(requestQueue, identityKey, null);
    }

    private SentiloConnectorVolleyImpl(RequestQueue requestQueue, String identityKey, Response.Listener<String> responseListener) {
        this(requestQueue, identityKey, responseListener, null);
    }

    private SentiloConnectorVolleyImpl(RequestQueue requestQueue, String identityKey, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        requestHeaders.put(HEADER_KEY_SENTILO_IDENTITY_KEY, identityKey);

        this.requestQueue = requestQueue;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
    }

    @Override
    public void updateBatteryLocation(SentiloRequestDTO sentiloRequestDTO) {
        requestQueue.add(new GsonRequest<>(SENTILO_URL, requestHeaders, sentiloRequestDTO, responseListener, errorListener));
    }
}
