package br.com.compracombinada;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bruno on 21/08/14.
 */
public class SobreMim extends android.support.v4.app.Fragment {

    private View view;

    private TextView nome;
    private TextView endereco;
    private TextView dtNascimento;
    private TextView sexo;
    private TextView login;
    private TextView senha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sobremim, container, false);

        nome = (TextView)view.findViewById(R.id.nome);
        endereco = (TextView)view.findViewById(R.id.endereco);
        dtNascimento = (TextView)view.findViewById(R.id.dtNascimento);
        sexo = (TextView)view.findViewById(R.id.sexo);
        login = (TextView)view.findViewById(R.id.login);
        senha = (TextView)view.findViewById(R.id.senha);

        return view;
    }
}
