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
import br.com.compracombinada.model.Produtos;

public class ListAdapterProdutos extends BaseAdapter {

    private List<Produtos> lista;
    private static LayoutInflater inflater = null;
    private View view;
    private ArrayList<Produtos> arraylist;

    public ListAdapterProdutos(Context context, List<Produtos> lista) {
        this.lista = lista;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Produtos>();
        this.arraylist.addAll(lista);

    }

    static class ViewHolder {

        TextView produtoNome;
        TextView produtoQuant;
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

            view = inflater.inflate(R.layout.list_row_lista_detalhe, null);
            view.setTag(holder);

            holder.produtoNome = (TextView) view.findViewById(R.id.lista_nome_produto);
            holder.produtoQuant = (TextView) view.findViewById(R.id.lista_produto_quant);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        holder.produtoNome.setText(lista.get(position).getProduto().getNome());
        holder.produtoQuant.setText("Quant: " + lista.get(position).getQuantidade());

        return view;

    }


}