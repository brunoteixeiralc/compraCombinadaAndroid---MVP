package br.com.compracombinada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.compracombinada.asynctask.AsyncTaskCompraColetiva;

/**
 * Created by bruno on 13/08/14.
 */
public class LoginActivity extends Activity {

    private Button btnEntrar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        btnEntrar = (Button) this.findViewById(R.id.button);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTaskCompraColetiva(LoginActivity.this).execute();
            }
        });


    }

    public void retornoAsyncTaskCompraCombinada(String jsonString){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        if(jsonString != null){
            prefs = LoginActivity.this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = prefs.edit();
            e.putString("jsonString", jsonString);
            e.commit();
        }

        LoginActivity.this.startActivity(intent);

    }
}
