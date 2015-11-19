package br.com.compracombinada.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.compracombinada.model.Divisao;
import br.com.compracombinada.model.Familia;
import br.com.compracombinada.model.Marca;
import br.com.compracombinada.model.ProdutoPreferencia;
import br.com.compracombinada.utils.SpinnerEnum;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
	
	private final List<Object> data;
	private Context context;
	private SpinnerEnum spinnerENUM;
	
	
	public SpinnerAdapter(List<Object> data,Context ctx,SpinnerEnum spinnerENUM){
		this.data = data;
		this.context = ctx;
		this.spinnerENUM = spinnerENUM;
	}
	
	@Override
	public int getCount() {

		if(spinnerENUM == SpinnerEnum.DIVISAO){
			return 1;
		}else if(spinnerENUM == SpinnerEnum.FAMILIA){
			return 2;
		}
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView textView = new TextView(context);
		textView.setTextSize(12);
		textView.setPadding(10, 10, 10, 10);

		
		switch (spinnerENUM) {

			case MARCA:
			textView.setText(((Marca) data.get(position)).getNome());
			break;

			case FAMILIA:
			textView.setText(((Familia) data.get(position)).getNome());
			break;

			case DIVISAO:
			textView.setText(((Divisao) data.get(position)).getNome());
			break;

			case PREFERENCIA:
			textView.setText(((ProdutoPreferencia) data.get(position)).getProduto().getNome());
			break;
		}

		return textView;
	}

}
