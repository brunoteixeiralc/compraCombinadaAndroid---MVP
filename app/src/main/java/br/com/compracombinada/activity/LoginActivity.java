package br.com.compracombinada.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.compracombinada.R;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaLogin;

/**
 * Created by bruno on 13/08/14.
 */
public class LoginActivity extends Activity {

    private Button btnEntrar;
    private SharedPreferences prefs;
    private EditText usuario;
    private EditText senha;
    private Button btnCadastrar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        usuario = (EditText) this.findViewById(R.id.editUsuario);
        senha = (EditText) this.findViewById(R.id.editSenha);

        btnEntrar = (Button) this.findViewById(R.id.button);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTaskCompraColetivaLogin(LoginActivity.this).execute(usuario.getText().toString(), senha.getText().toString());
            }
        });

        btnCadastrar = (Button) this.findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(LoginActivity.this, WebViewCadastrar.class);
                startActivity(intent);
            }
        });



    }

    public void retornoAsyncTaskCompraCombinada(String jsonString){

        intent = new Intent(LoginActivity.this, MainActivity.class);

        if(jsonString != null){

            prefs = LoginActivity.this.getSharedPreferences("settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = prefs.edit();
            e.putString("jsonString", jsonString);
            e.commit();

            LoginActivity.this.startActivity(intent);

        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Usuário ou senha estão incorretos!")
                    .setTitle("Alerta Compra Combinada");
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }
}
