package br.com.compracombinada;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.compracombinada.adpater.cardview.CardViewAdapter;
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

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.card_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(SobreMim.this.getActivity());
        rv.setLayoutManager(llm);

        CardViewAdapter adapter = new CardViewAdapter(usuario);
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(1);
        super.onAttach(activity);
    }
}
