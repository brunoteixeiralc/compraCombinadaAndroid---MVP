package br.com.compracombinada.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import android.util.Log;

import com.google.gson.Gson;

public class WebServiceCompraCombinada {

    public final String[] get(String url) {

        String[] result = new String[2];
        HttpGet httpget = new HttpGet(url);
        HttpResponse response;

        try {
            response = HttpClientSingleton.getHttpClientInstace().execute(httpget);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result[0] = String.valueOf(response.getStatusLine().getStatusCode());
                InputStream instream = entity.getContent();
                result[1] = toString(instream);
                instream.close();
                Log.i("get", "Result from post JsonPost : " + result[0] + " : " + result[1]);
            }
        } catch (Exception e) {
            Log.e("NGVL", "Falha ao acessar Web service", e);
            result[0] = "0";
            result[1] = "Falha de rede!";
        }
        return result;
    }

    public final String[] post(String url, Object o) {

        String[] result = new String[2];
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response;
        String jsonString = "";

        try {

            Gson gson = new Gson();
            jsonString = gson.toJson(o);
            StringEntity stringEntity = new StringEntity(jsonString);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json;charset=ISO-8859-1");

            response = HttpClientSingleton.getHttpClientInstace().execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result[0] = String.valueOf(response.getStatusLine().getStatusCode());
                InputStream instream = entity.getContent();
                result[1] = toString(instream);
                instream.close();
                Log.i("get", "Result from post JsonPost : " + result[0] + " : " + result[1]);
            }
        } catch (Exception e) {
            Log.e("NGVL", "Falha ao acessar Web service", e);
            result[0] = "0";
            result[1] = "Falha de rede!";
        }
        return result;
    }

    private String toString(InputStream is) throws IOException {

        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0) {
            baos.write(bytes, 0, lidos);
        }
        return new String(baos.toByteArray());
    }
}
