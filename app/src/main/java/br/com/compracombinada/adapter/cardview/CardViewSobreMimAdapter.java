package br.com.compracombinada.adapter.cardview;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.compracombinada.R;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.utils.Utils;

public class CardViewSobreMimAdapter extends RecyclerView.Adapter<CardViewSobreMimAdapter.CardViewHolder>{

	private Usuario usuario;

	public static class CardViewHolder extends RecyclerView.ViewHolder {
		CardView cv;
		TextView nome;
		TextView endereco;
		TextView dtNascimento;
		TextView sexo;
        ImageView foto;


		CardViewHolder(View itemView) {
			super(itemView);
			cv = (CardView)itemView.findViewById(R.id.cv);
			nome = (TextView) itemView.findViewById(R.id.nome);
			endereco = (TextView) itemView.findViewById(R.id.endereco);
			dtNascimento = (TextView) itemView.findViewById(R.id.dataNascimento);
			sexo = (TextView) itemView.findViewById(R.id.sexo);
            foto = (ImageView) itemView.findViewById(R.id.foto);
		}
	}

	public CardViewSobreMimAdapter(Usuario usuario){
		this.usuario = usuario;
	}

	@Override
	public CardViewSobreMimAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_sobremim, parent, false);
		CardViewHolder pvh = new CardViewHolder(v);
		return pvh;
	}

	@Override
	public void onBindViewHolder(CardViewSobreMimAdapter.CardViewHolder holder, int position) {

		holder.nome.setText(usuario.getNome().toString());
		holder.endereco.setText(usuario.getEndereco().toString());
		holder.dtNascimento.setText(Utils.formatData(usuario.getDataNascimento().toString()));
		if(usuario.getSexo().equalsIgnoreCase("M")){
			holder.sexo.setText("Masculino");
		}else{
			holder.sexo.setText("Feminino");
		}

        if(usuario.getFoto() != null){
            holder.foto.setImageBitmap(convertBase64Image(usuario.getFoto()));
        }

	}

	@Override
	public int getItemCount() {
		return 1;
	}

	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
	}

    private Bitmap convertBase64Image(String strFoto){

        byte[] decodedString = Base64.decode(strFoto, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }
}
