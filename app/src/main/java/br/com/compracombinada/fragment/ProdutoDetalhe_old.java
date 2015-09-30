package br.com.compracombinada.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 21/08/14.
 */
public class ProdutoDetalhe_old extends android.support.v4.app.Fragment {

    private View view;
    private Produtos produto;
    private TextView nome;
    private TextView familia;
    private TextView divisao;
    private TextView grupo;
    private TextView quantidade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.produto_detalhe_old, container, false);

        produto = new Produtos();
        produto = (Produtos) this.getArguments().get("produto");

        nome = (TextView)view.findViewById(R.id.produto_nome);
        familia = (TextView)view.findViewById(R.id.produto_familia);
        //divisao = (TextView)view.findViewById(R.id.produto_divisao);
        //grupo = (TextView)view.findViewById(R.id.produto_grupo);
        quantidade = (TextView)view.findViewById(R.id.produto_quantidade);

        nome.setText(produto.getProduto().getNome());
        familia.setText(produto.getProduto().getFamilia().getNome());
        //divisao.setText(produto.getProduto().getFamilia().getGrupo().getDivisao().getNome());
        //grupo.setText(produto.getProduto().getFamilia().getGrupo().getNome());
        quantidade.setText(String.valueOf(produto.getQuantidade()));

        return view;
    }
}
