package br.com.compracombinada.adpater.cardview;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.util.Utils;

public class CardViewProdutoDetalheAdapter extends RecyclerView.Adapter<CardViewProdutoDetalheAdapter.CardViewHolder>{

	private Produtos produtos;

	public static class CardViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		TextView nome;
		TextView quantidade;
		TextView familia;
		TextView divisao;


		CardViewHolder(View itemView) {
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			nome = (TextView) itemView.findViewById(R.id.nome);
			quantidade = (TextView) itemView.findViewById(R.id.quantidade);
			familia = (TextView) itemView.findViewById(R.id.familia);
			divisao = (TextView) itemView.findViewById(R.id.divisao);
		}
	}

	public CardViewProdutoDetalheAdapter(Produtos produtos){
		this.produtos = produtos;
	}

	@Override
	public CardViewProdutoDetalheAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_produto_detalhe, parent, false);
		CardViewHolder pvh = new CardViewHolder(v);
		return pvh;
	}

	@Override
	public void onBindViewHolder(CardViewProdutoDetalheAdapter.CardViewHolder holder, int position) {

		holder.nome.setText(produtos.getProduto().getNome());
		holder.quantidade.setText(String.valueOf(produtos.getQuantidade()) + " unidade(s)");
		holder.familia.setText(produtos.getProduto().getFamilia().getNome());
		holder.divisao.setText(produtos.getProduto().getDivisao().getNome());
	}

	@Override
	public int getItemCount() {
		return 1;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}
}
