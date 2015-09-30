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
import br.com.compracombinada.model.Usuario;

public class ListAdapterUsuario extends BaseAdapter {

	private List<Usuario> lista;
	private static LayoutInflater inflater = null;
	private View view;
	private ArrayList<Usuario> arraylist;

	public ListAdapterUsuario(Context context, List<Usuario> lista) {
		this.lista = lista;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.arraylist = new ArrayList<Usuario>();
        this.arraylist.addAll(lista);
		
	}
	
	static class ViewHolder{

  		TextView usuarioNome;
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
        	 
        	 view = inflater.inflate(R.layout.list_row_usuario, null);
        	 view.setTag(holder);

             holder.usuarioNome = (TextView)view.findViewById(R.id.usuario_nome);
             
        }else{
        	
        	holder = (ViewHolder) view.getTag();
        }
       
		 holder.usuarioNome.setText(lista.get(position).getNome());
		 
        return view;
		
	}
    
   
}