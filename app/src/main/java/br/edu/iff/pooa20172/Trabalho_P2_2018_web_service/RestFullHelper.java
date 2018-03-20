package br.edu.iff.pooa20172.Trabalho_P2_2018_web_service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RestFullHelper {

    public static String POST = "POST";
    public static String PUT = "PUT";
    public static String DELETAR = "DELETE";
    public static String GET = "GET";
    public static String GETALL = "GETALL";

    public final int TIMEOUT_MILLIS = 15000;
    private final String TAG = this.getClass().getSimpleName();
    public boolean LOG_ON = false;

    private String contentType;
    private String charsetToEncode;


    public JSONArray doGet(String url) {
        return doGet(url, StandardCharsets.UTF_8);
    }

    public JSONArray doGet(String url, Charset charset) {


        if (LOG_ON) {
            Log.w(TAG, ">> Http.doGet: " + url);
        }

        return getJSON(url, GET);
    }


    public JSONArray getJSON(String url, String method) {
        return getJSON(url, method, null, StandardCharsets.UTF_8);
    }

    public JSONArray getJSON(String url, String method, JSONObject params) {
        return getJSON(url, method, params, StandardCharsets.UTF_8);
    }

    public JSONArray getJSON(String url, String method, JSONObject params, Charset charset) {

        HttpURLConnection conn = null;

        String json = null;
        JSONObject jObj = null;
        JSONArray jObja = new JSONArray();

        try {
            conn = (HttpURLConnection) getConnection(url, method, "application/json");


            if (params != null) {

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(params.toString());
                out.flush();
                out.close();

            }

            InputStream in = null;

            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.w(TAG, "Error code HTTP_BAD_REQUEST : " + status + " - URL: " + url + " - " + method);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(in, charset));

            json = buffering(reader);

            if (LOG_ON) {
                Log.w(TAG, "JSON << Http.do" + method + ": " + json);
                //}else{
                System.out.println("<< Http.Method >> " + method + ": " + json);
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            //System.out.println("JSON: " + json);
            //jObj = new JSONObject(json);
            jObja = new JSONArray(json);
            //System.out.println("<< Http.Method >> " + method + ": " + jObja.length() + " "+jObja);
            //jObj = (JSONObject) jObja.get(0);
            //System.out.println("<< Json manr >> " + jObj.);


        } catch (JSONException e) {
            e.printStackTrace();
            jObj = null;

        }

        return jObja;
    }

    public String buffering(BufferedReader reader) {
        StringBuilder sb = new StringBuilder();
        try {

            String line = null;
            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");
            }

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return sb.toString();
    }

    public HttpURLConnection getConnection(String endPoint, String method) {
        return getConnection(endPoint, method, null);
    }

    public HttpURLConnection getConnection(String endPoint, String method, String contentType) {

        HttpURLConnection conn = null;

        try {
            URL url = new URL(endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(method);
            if (!method.equalsIgnoreCase(RestFullHelper.GET))
                conn.setDoOutput(true);
            if (contentType != null)
                conn.setRequestProperty("Content-Type", contentType);
            else
                conn.setRequestProperty("Content-Type", "text/plain");

            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
