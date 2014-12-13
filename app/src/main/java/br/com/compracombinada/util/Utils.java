package br.com.compracombinada.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Objects;

import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 13/10/14.
 */
public class Utils {

    public static Object convertJsonStringToObject(String jsonString) {

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject objectJson = (JsonObject) parser.parse(jsonString);

        return gson.fromJson(objectJson, Usuario.class);

    }

    // public static String ip = "http://192.168.25.3:8080/CompraCombinada";
    public static String ip = "http://54.94.142.250:18083/CompraCombinada";
}
