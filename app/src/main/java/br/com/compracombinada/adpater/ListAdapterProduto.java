package br.com.compracombinada.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;

public class ListAdapterProduto extends BaseAdapter {

    private List<Produto> lista;
    private static LayoutInflater inflater = null;
    private View view;
    private ArrayList<Produto> arraylist;

    public ListAdapterProduto(Context context, List<Produto> lista) {
        this.lista = lista;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Produto>();
        this.arraylist.addAll(lista);

    }

    static class ViewHolder {

        TextView produtoNome;
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

        if (view == null) {

            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_row_lista_busca_produto, null);
            view.setTag(holder);

            holder.produtoNome = (TextView) view.findViewById(R.id.lista_nome_produto);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        holder.produtoNome.setText(lista.get(position).getNome());

        return view;

    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        lista.clear();
        if (charText.length() == 0) {
            lista.addAll(arraylist);
        }
        else
        {
            for (Produto p : arraylist)
            {
                if (p.getNome().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    lista.add(p);
                }
            }
        }
        notifyDataSetChanged();
    }



}