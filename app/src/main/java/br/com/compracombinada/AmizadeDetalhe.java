package br.com.compracombinada;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.compracombinada.adpater.cardview.CardViewAmizadeAdapter;
import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class AmizadeDetalhe extends android.support.v4.app.Fragment {

    private View view;
    private Usuario usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.amizade_detalhe, container, false);

        usuario = new Usuario();
        usuario = ((Amizade) this.getArguments().get("amizade")).getUsuarioConvidado();

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.card_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(AmizadeDetalhe.this.getActivity());
        rv.setLayoutManager(llm);

        CardViewAmizadeAdapter adapter = new CardViewAmizadeAdapter(usuario);
        rv.setAdapter(adapter);

        return view;
    }
}
