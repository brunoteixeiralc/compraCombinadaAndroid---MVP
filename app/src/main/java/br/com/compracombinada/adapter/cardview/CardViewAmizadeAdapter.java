package br.com.compracombinada.adapter.cardview;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.utils.Utils;

public class CardViewAmizadeAdapter extends RecyclerView.Adapter<CardViewAmizadeAdapter.CardViewHolder>{

	private Usuario usuario;

	public static class CardViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		TextView nome;
		TextView endereco;
		TextView dtNascimento;
		TextView sexo;


		CardViewHolder(View itemView) {
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			nome = (TextView) itemView.findViewById(R.id.nome);
			endereco = (TextView) itemView.findViewById(R.id.endereco);
			dtNascimento = (TextView) itemView.findViewById(R.id.dataNascimento);
			sexo = (TextView) itemView.findViewById(R.id.sexo);
		}
	}

	public CardViewAmizadeAdapter(Usuario usuario){
		this.usuario = usuario;
	}

	@Override
	public CardViewAmizadeAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_amizade, parent, false);
		CardViewHolder pvh = new CardViewHolder(v);
		return pvh;
	}

	@Override
	public void onBindViewHolder(CardViewAmizadeAdapter.CardViewHolder holder, int position) {

		holder.nome.setText(usuario.getNome().toString());
		holder.endereco.setText(usuario.getEndereco().toString());
		holder.dtNascimento.setText(Utils.formatData(usuario.getDataNascimento().toString()));
		holder.sexo.setText(usuario.getSexo().toString() == "M" ? "Masculino" : "Feminino");
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
