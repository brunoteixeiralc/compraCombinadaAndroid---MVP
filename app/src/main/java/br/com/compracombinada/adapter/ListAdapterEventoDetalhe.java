package br.com.compracombinada.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Lista;

public class ListAdapterEventoDetalhe extends BaseAdapter {

	private List<Lista> lista;
	private static LayoutInflater inflater = null;
	private View view;
	private ArrayList<Lista> arraylist;
    private int i = 1;

	public ListAdapterEventoDetalhe(Context context, List<Lista> lista) {
		this.lista = lista;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.arraylist = new ArrayList<Lista>();
        this.arraylist.addAll(lista);
		
	}
	
	static class ViewHolder{

  		TextView  listaNome;
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
        	 
        	 view = inflater.inflate(R.layout.list_row_lista, null);
        	 view.setTag(holder);

             holder.listaNome = (TextView)view.findViewById(R.id.lista_nome);
             
        }else{
        	
        	holder = (ViewHolder) view.getTag();
        }

         holder.listaNome.setText("Lista" + i);
         i++;
		// holder.listaNome.setText(lista.get(position).getNome());
		 
        return view;
		
	}
    
   
}