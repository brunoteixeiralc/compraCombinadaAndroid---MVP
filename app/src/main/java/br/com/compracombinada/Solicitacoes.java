package br.com.compracombinada;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Timestamp;

import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaConfiguracaoAtualizar;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaSolicitacoes;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.model.UsuarioSingleton;

/**
 * Created by bruno on 21/08/14.
 */
public class Solicitacoes extends android.support.v4.app.Fragment {

    private View view;
    private Button btnEnviar;
    private EditText sugestoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.solicitacoes, container, false);

        sugestoes = (EditText) view.findViewById(R.id.sugestoes);

        btnEnviar = (Button) view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                br.com.compracombinada.model.Solicitacoes solicitacoes = new br.com.compracombinada.model.Solicitacoes();
                solicitacoes.setNomeApp("Mobile");
                solicitacoes.setSugestoes(sugestoes.getText().toString());
                solicitacoes.setUsuario(UsuarioSingleton.getInstance().getUsuario());
                java.util.Date date= new java.util.Date();
                //solicitacoes.setIntegracao(new Timestamp(date.getTime()));

                new AsyncTaskCompraColetivaSolicitacoes(Solicitacoes.this).execute(solicitacoes);

            }
        });

        return view;
    }


    public void retornoAsyncTaskCompraCombinadaSolicitacoes(String msg){

        sugestoes.getText().clear();

        AlertDialog.Builder builder = new AlertDialog.Builder(Solicitacoes.this.getActivity());
        builder.setMessage(msg)
                .setTitle("Alerta Compra Combinada");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(10);
        super.onAttach(activity);
    }
}
