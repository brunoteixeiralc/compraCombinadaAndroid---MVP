package br.com.compracombinada;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Local;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class LocalDetalhe extends android.support.v4.app.Fragment {

    private View view;
    private Local local;
    private TextView localNome;
    private TextView localEndereco;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.local_detalhe, container, false);

        local = new Local();
        local = ((Local) this.getArguments().get("local"));

        localNome = (TextView)view.findViewById(R.id.local_nome);
        localEndereco = (TextView)view.findViewById(R.id.local_endereco);


        localNome.setText(local.getNome());
        localEndereco.setText(local.getEndereco());

        return view;
    }
}
