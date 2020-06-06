package com.example.diappetes;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest<R> extends Request<String> {

    private final static String LOG_TAG = "GsonRequest";

    private final Gson gson;
    private final Map<String, String> headers;
    private final R requestBody;
    private final Response.Listener<String> responseListener;

    public GsonRequest(String url, Map<String, String> headers, R requestBody, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        super(Method.PUT, url, errorListener);

        this.gson = new Gson();
        this.headers = headers;
        this.requestBody = requestBody;
        this.responseListener = responseListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return gson.toJson(requestBody).getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    protected void deliverResponse(String response) {
        if (responseListener != null) {
            responseListener.onResponse(response);
        } else {
            Log.d(LOG_TAG, "Response received: " + response);
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    json,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
