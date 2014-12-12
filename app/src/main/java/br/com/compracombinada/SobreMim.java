package br.com.compracombinada;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class SobreMim extends android.support.v4.app.Fragment {

    private View view;
    private Usuario usuario;
    private TextView nome;
    private TextView endereco;
    private TextView dtNascimento;
    private TextView sexo;
    private TextView login;
    private TextView senha;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sobremim, container, false);

        usuario = new Usuario();
        usuario = (Usuario) this.getArguments().get("usuario");

        nome = (TextView) view.findViewById(R.id.nome);
        endereco = (TextView) view.findViewById(R.id.endereco);
        dtNascimento = (TextView) view.findViewById(R.id.dtNascimento);
        sexo = (TextView) view.findViewById(R.id.sexo);
        login = (TextView) view.findViewById(R.id.login);
        senha = (TextView) view.findViewById(R.id.senha);

        nome.setText(usuario.getNome());
        endereco.setText(usuario.getEndereco());
        login.setText(usuario.getLogin());
        senha.setText(usuario.getSenha());
        sexo.setText(usuario.getSexo().equalsIgnoreCase("M") ? "Masculino" : "Feminino");

        dtNascimento.setText(usuario.getDataNascimento());

        return view;
    }
}
