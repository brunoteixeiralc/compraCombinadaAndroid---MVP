package br.com.compracombinada.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.compracombinada.R;
import br.com.compracombinada.model.Produtos;

public class ListAdapterProdutosCompraColetiva extends BaseAdapter {

    private List<Produtos> lista;
    private static LayoutInflater inflater = null;
    private View view;
    private ArrayList<Produtos> arraylist;
    private double precoDouble;

    public ListAdapterProdutosCompraColetiva(Context context, List<Produtos> lista) {
        this.lista = lista;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Produtos>();
        this.arraylist.addAll(lista);

    }

    static class ViewHolder {

        TextView produtoNome;
        TextView produtoQuant;
        TextView produtoPreco;
        TextView usuarioLista;
        TextView naoContem;
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
            holder.produtoPreco = (TextView) view.findViewById(R.id.lista_produto_preco);
            holder.usuarioLista = (TextView) view.findViewById(R.id.lista_usuario_lista);
            holder.naoContem = (TextView) view.findViewById(R.id.nao_contem_produto);
            holder.usuarioLista.setVisibility(view.VISIBLE);
            holder.produtoPreco.setVisibility(view.VISIBLE);
            holder.naoContem.setVisibility(view.GONE);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        Locale locale = new Locale("pt", "BR");
        NumberFormat nf = NumberFormat.getNumberInstance(locale);

        if (lista.get(position).isNaoContem()) {

            holder.naoContem.setVisibility(view.VISIBLE);
            holder.produtoPreco.setVisibility(view.GONE);

            lista.get(position).setNaoContem(true);
            lista.get(position).setPreco(null);

        } else {

            if (lista.get(position).getPreco() != null && lista.get(position).getPreco().contains(".")) {
                precoDouble = Double.parseDouble(lista.get(position).getPreco());
                holder.produtoPreco.setText(lista.get(position).getPreco() == null ? "R$ 00,00" : "R$ " + nf.format(precoDouble));
            } else {
                holder.produtoPreco.setText(lista.get(position).getPreco() == null ? "R$ 00,00" : "R$ " + lista.get(position).getPreco());
            }
            holder.produtoPreco.setVisibility(view.VISIBLE);
            holder.naoContem.setVisibility(view.GONE);

        }

        holder.produtoNome.setText(lista.get(position).getProduto().getNome());
        holder.produtoQuant.setText("Quant: " + lista.get(position).getQuantidade());
        holder.usuarioLista.setText("Lista de " + lista.get(position).getUsuarioNome());


        return view;

    }


}