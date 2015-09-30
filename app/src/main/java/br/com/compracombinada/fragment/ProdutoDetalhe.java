package br.com.compracombinada.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.compracombinada.R;
import br.com.compracombinada.adapter.cardview.CardViewProdutoDetalheAdapter;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 21/08/14.
 */
public class ProdutoDetalhe extends android.support.v4.app.Fragment {

    private View view;
    private Produtos produtos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.produto_detalhe, container, false);

        produtos = new Produtos();
        produtos = (Produtos) this.getArguments().get("produto");

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.card_view);
        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(ProdutoDetalhe.this.getActivity());
        rv.setLayoutManager(llm);

        CardViewProdutoDetalheAdapter adapter = new CardViewProdutoDetalheAdapter(produtos);
        rv.setAdapter(adapter);

        return view;
    }
}
