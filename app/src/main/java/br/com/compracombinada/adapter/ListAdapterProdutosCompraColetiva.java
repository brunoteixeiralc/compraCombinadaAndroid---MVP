package br.com.compracombinada.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.compracombinada.fragment.ListaDetalheCompraColetiva;
import br.com.compracombinada.R;
import br.com.compracombinada.model.Produtos;

public class ListAdapterProdutosCompraColetiva extends BaseAdapter {

    private List<Produtos> lista;
    private static LayoutInflater inflater = null;
    private View view;
    private ArrayList<Produtos> arraylist;
    private double precoDouble;
    private android.support.v4.app.Fragment fragment;

    public ListAdapterProdutosCompraColetiva(android.support.v4.app.Fragment fragment, List<Produtos> lista) {
        this.lista = lista;
        inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<Produtos>();
        this.arraylist.addAll(lista);
        this.fragment = fragment;

    }

    static class ViewHolder {

        TextView produtoNome;
        TextView produtoQuant;
        TextView produtoPreco;
        TextView usuarioLista;
        TextView naoContem;
        ImageView deletarItem;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

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
            holder.deletarItem = (ImageView) view.findViewById(R.id.img_delete);
            holder.naoContem = (TextView) view.findViewById(R.id.nao_contem_produto);
            holder.usuarioLista.setVisibility(view.VISIBLE);
            holder.produtoPreco.setVisibility(view.VISIBLE);
            holder.naoContem.setVisibility(view.GONE);

            if(fragment instanceof ListaDetalheCompraColetiva){
                holder.deletarItem.setVisibility(view.VISIBLE);
            }else{
                holder.deletarItem.setVisibility(view.GONE);
            }

            final ViewHolder finalHolder = holder;

            holder.deletarItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

               verificarDeletar(finalHolder, lista.get(position));

                }
            });

        } else {

            holder = (ViewHolder) view.getTag();
        }

        verificaNaoContem(holder,lista.get(position));

        verificaPreferencia(lista.get(position));

        holder.produtoNome.setText(lista.get(position).getProduto().getNome());
        holder.produtoQuant.setText("Quant: " + lista.get(position).getQuantidade());
        holder.produtoQuant.setText(lista.get(position).getProduto().getFamilia().getMedida().equalsIgnoreCase("unidade") ? "Quant: " + lista.get(position).getQuantidade() :
                lista.get(position).getQuantidade() + " gramas");
        holder.usuarioLista.setText("Lista de " + lista.get(position).getUsuarioNome());

        return view;

    }



    private void verificaPreferencia(Produtos produtos){

        if (produtos.getProduto().getPreferencia() == 1){
            produtos.getProduto().setNome(produtos.getProduto().getNome() + "*");
        }

    }

    private void verificaNaoContem(ViewHolder holder, Produtos produtos){

        DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));

        if (produtos.isNaoContem()) {

            holder.naoContem.setVisibility(view.VISIBLE);
            holder.produtoPreco.setVisibility(view.GONE);

            produtos.setNaoContem(true);
            produtos.setPreco(null);
            view.setBackgroundColor(Color.parseColor("#ADD8E6"));

        } else {

            if (produtos.getPreco() != null && produtos.getPreco().contains(".")) {
                precoDouble = Double.parseDouble(produtos.getPreco());
                holder.produtoPreco.setText(produtos.getPreco() == null ? "R$ 00,00" : "R$ " + precoFinal.format(precoDouble));
            } else {
                holder.produtoPreco.setText(produtos.getPreco() == null ? "R$ 00,00" : "R$ " + produtos.getPreco());
            }

            if(!holder.produtoPreco.getText().toString().equalsIgnoreCase("R$ 00,00")){
                view.setBackgroundColor(Color.parseColor("#ADD8E6"));
            }

            holder.produtoPreco.setVisibility(view.VISIBLE);
            holder.naoContem.setVisibility(view.GONE);
        }

    }

    private void verificarDeletar(ViewHolder finalHolder,Produtos produtos){

        if(!produtos.isDeletou()) {
            produtos.setDeletou(true);
            produtos.setPreco(null);
            finalHolder.produtoNome.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            finalHolder.produtoQuant.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            finalHolder.produtoPreco.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            finalHolder.usuarioLista.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            produtos.setDeletou(false);
            finalHolder.produtoNome.setPaintFlags(0);
            finalHolder.produtoQuant.setPaintFlags(0);
            finalHolder.produtoPreco.setPaintFlags(0);
            finalHolder.usuarioLista.setPaintFlags(0);
        }
    }

}