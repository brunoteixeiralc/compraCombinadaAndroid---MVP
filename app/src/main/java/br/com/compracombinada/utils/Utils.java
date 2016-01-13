package br.com.compracombinada.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 13/10/14.
 */
public class Utils {

    //prod
    //public static String ip = "http://ec2-52-7-44-186.compute-1.amazonaws.com:8080/CompraCombinada";

    //local
   public static String ip = "http://192.168.25.8:8080/CompraCombinada";


    public static Object convertJsonStringToObject(String jsonString) {

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonObject objectJson = (JsonObject) parser.parse(jsonString);

        return gson.fromJson(objectJson, Usuario.class);

    }

    public static String encrypt(String senha)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        return new BigInteger(1, m.digest()).toString(16);
    }

    public static String formatData(String data){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        String dateFmt = null;

        try {

            date = format.parse(data);
            dateFmt = format2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFmt;
    }

}
