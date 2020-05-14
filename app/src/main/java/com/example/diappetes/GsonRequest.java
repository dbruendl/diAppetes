package com.example.diappetes;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.diappetes.sentilo.request.dto.LocationDTO;
import com.example.diappetes.sentilo.request.dto.ObservationDTO;
import com.example.diappetes.sentilo.request.dto.SentiloRequestDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class GsonRequest extends Request<String> {
    private final Gson gson;
    private final Map<String, String> headers;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url URL of the request to make
     * @param headers Map of request headers
     */
    public GsonRequest(String url, Map<String, String> headers, Response.ErrorListener errorListener) {
        super(Method.PUT, url, errorListener);

        this.gson = new Gson();
        this.headers = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ObservationDTO observationDTO = new ObservationDTO("56", "41.22157865201759 1.7300500669616392");
        SentiloRequestDTO sentiloRequestDTO = new SentiloRequestDTO(observationDTO);

        return new Gson().toJson(sentiloRequestDTO).getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    @Override
    protected void deliverResponse(String response) {
        return;
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
