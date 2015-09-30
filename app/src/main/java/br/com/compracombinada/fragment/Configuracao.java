package br.com.compracombinada.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.compracombinada.R;
import br.com.compracombinada.activity.MainActivity;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaConfiguracaoAtualizar;

/**
 * Created by bruno on 21/08/14.
 */
public class Configuracao extends android.support.v4.app.Fragment {

    private View view;
    private EditText servidor;
    private String jsonServidor;
    private br.com.compracombinada.model.Configuracao configuracao;
    private SharedPreferences prefs;
    private Button btnAtualizar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.configuracao, container, false);

        servidor = (EditText) view.findViewById(R.id.editServidor);
        btnAtualizar = (Button) view.findViewById(R.id.btnAtualizar);

        prefs = Configuracao.this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);

        if(!prefs.getString("servidor","").equalsIgnoreCase("")){

            servidor.setText(prefs.getString("servidor","").toString());
        }

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                br.com.compracombinada.model.Configuracao configuracao = new br.com.compracombinada.model.Configuracao();
                configuracao.setServidor(servidor.getText().toString());

                new AsyncTaskCompraColetivaConfiguracaoAtualizar(Configuracao.this).execute(configuracao);

            }
        });

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaConfiguracaoAtualizar(String servidor){

        prefs = Configuracao.this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putString("servidor", servidor);
        e.commit();

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(8);
        super.onAttach(activity);
    }
}
