package br.com.compracombinada.adpater;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Amizade;

public class ListAdapterAmizade extends BaseAdapter {

	private List<Amizade> lista;
	private static LayoutInflater inflater = null;
	private View view;
	private ArrayList<Amizade> arraylist;
	
	public ListAdapterAmizade(Context context, List<Amizade> lista) {
		this.lista = lista;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.arraylist = new ArrayList<Amizade>();
        this.arraylist.addAll(lista);
		
	}
	
	static class ViewHolder{

  		TextView amizadeNome;
  	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		view = convertView;
		
		 if(view==null){
	        	
        	 holder = new ViewHolder();
        	 
        	 view = inflater.inflate(R.layout.list_row_amizade, null);
        	 view.setTag(holder);

             holder.amizadeNome = (TextView)view.findViewById(R.id.amizade_nome);
             
        }else{
        	
        	holder = (ViewHolder) view.getTag();
        }
       
		 holder.amizadeNome.setText(lista.get(position).getUsuarioConvidado().getNome());
		 
        return view;
		
	}
    
   
}