package br.com.compracombinada;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaConfiguracao;
import br.com.compracombinada.model.*;


/**
 * Created by bruno on 13/08/14.
 */
public class SplashScreen extends Activity {


    private SharedPreferences prefs;
    private Handler myhandler;
    private static final int SPLASH_DURATION = 4000;
    private br.com.compracombinada.model.Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);

        myhandler = new Handler();

        myhandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                new AsyncTaskCompraColetivaConfiguracao(SplashScreen.this).execute();
            }

        }, SPLASH_DURATION);

    }

    public void retornoAsyncTaskCompraCombinada(String jsonString){

        if(!jsonString.equalsIgnoreCase("")){

            Gson gson = new Gson();
            configuracao = new br.com.compracombinada.model.Configuracao();
            JsonParser parser = new JsonParser();
            JsonObject configuracaoJSON = (JsonObject) parser.parse(jsonString);
            configuracao = gson.fromJson(configuracaoJSON, br.com.compracombinada.model.Configuracao.class);

            prefs = SplashScreen.this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = prefs.edit();
            e.putString("servidor", configuracao.getServidor());
            e.commit();
        }

        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        SplashScreen.this.startActivity(intent);

    }

}
