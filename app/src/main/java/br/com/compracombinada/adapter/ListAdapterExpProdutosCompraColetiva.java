package br.com.compracombinada.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.compracombinada.fragment.ListaDetalheCompraColetiva;
import br.com.compracombinada.R;
import br.com.compracombinada.model.Produtos;

public class ListAdapterExpProdutosCompraColetiva extends BaseExpandableListAdapter {

    private static LayoutInflater inflater = null;
    private View view;
    private Map<String,List<Produtos>> arraylist;
    private double precoDouble;
    private android.support.v4.app.Fragment fragment;
    private List<String> key;
    private boolean isDonoEvento;

    public ListAdapterExpProdutosCompraColetiva(android.support.v4.app.Fragment fragment, Map<String,List<Produtos>>  lista, boolean isDonoEvento) {
        inflater = (LayoutInflater) fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new HashMap<String, List<Produtos>>();
        this.arraylist = lista;
        this.fragment = fragment;
        this.key = new ArrayList<String>(arraylist.keySet());
        this.isDonoEvento = isDonoEvento;

    }

    static class ViewHolder {

        TextView produtoNome;
        TextView produtoQuant;
        TextView produtoPreco;
        TextView usuarioLista;
        TextView naoContem;
        ImageView deletarItem;
        TextView produtoPreferencia;
        TextView txtKg;
    }

    @Override
    public int getGroupCount() {
        return arraylist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return arraylist.get(key.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return key.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return arraylist.get(key.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1,null);
        }
        TextView txt = (TextView) view.findViewById(android.R.id.text1);
        txt.setTextColor(Color.WHITE);
        txt.setBackgroundColor(Color.GRAY);
        txt.setText(key.get(i));
        txt.setPadding(100, 10, 10, 10);

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        this.view = view;

       // if (view == null) {

            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_row_lista_detalhe, null);
           // view.setTag(holder);

            holder.produtoNome = (TextView) view.findViewById(R.id.lista_nome_produto);
            holder.produtoQuant = (TextView) view.findViewById(R.id.lista_produto_quant);
            holder.produtoPreco = (TextView) view.findViewById(R.id.lista_produto_preco);
            holder.usuarioLista = (TextView) view.findViewById(R.id.lista_usuario_lista);
            holder.deletarItem = (ImageView) view.findViewById(R.id.img_delete);
            holder.naoContem = (TextView) view.findViewById(R.id.nao_contem_produto);
            holder.produtoPreferencia = (TextView) view.findViewById(R.id.produto_pref_escolhido);
            holder.txtKg = (TextView) view.findViewById(R.id.txt_kg);
            holder.usuarioLista.setVisibility(view.VISIBLE);
            holder.produtoPreco.setVisibility(view.VISIBLE);
            holder.naoContem.setVisibility(view.GONE);
            holder.produtoPreferencia.setVisibility(View.GONE);

            if(fragment instanceof ListaDetalheCompraColetiva){
                if(isDonoEvento){
                    holder.deletarItem.setVisibility(view.VISIBLE);
                }else {
                    holder.deletarItem.setVisibility(view.GONE);
                }
            }else{
                holder.deletarItem.setVisibility(view.GONE);
            }

            final ViewHolder finalHolder = holder;

            holder.deletarItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    deletarClick(finalHolder, arraylist.get(key.get(i)).get(i1));

                }
            });


//        } else {
//
//            holder = (ViewHolder) view.getTag();
//        }

        verificaNaoContem(holder,arraylist.get(key.get(i)).get(i1), view);

        verificaPreferencia(holder, arraylist.get(key.get(i)).get(i1));

        verificarDeletar(holder,arraylist.get(key.get(i)).get(i1));

        holder.produtoNome.setText(arraylist.get(key.get(i)).get(i1).getProduto().getNome());
        holder.produtoQuant.setText(arraylist.get(key.get(i)).get(i1).getProduto().getFamilia().getMedida().equalsIgnoreCase("unidade") ? "Quant: " + arraylist.get(key.get(i)).get(i1).getQuantidade() :
                arraylist.get(key.get(i)).get(i1).getQuantidade() + " gramas");
        holder.usuarioLista.setText("Lista de " + arraylist.get(key.get(i)).get(i1).getUsuarioNome());


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


    private void verificaPreferencia(ViewHolder holder,Produtos produtos){

        if (produtos.getProduto().getPreferencia() == 1){

            produtos.getProduto().setNome(produtos.getProduto().getNome().contains("*") ? produtos.getProduto().getNome() :
                    produtos.getProduto().getNome() + " *");

            if (produtos.getProdutoTempPref() != null) {
                holder.naoContem.setVisibility(view.GONE);
                holder.produtoPreferencia.setVisibility(View.VISIBLE);
                holder.produtoPreferencia.setText("Produto escolhido: " + produtos.getProdutoTempPref().getNome());
            }

        }

    }

    private void verificaNaoContem(ViewHolder holder, Produtos produtos, View view){

        DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));

        if (produtos.isNaoContem()) {

            holder.naoContem.setVisibility(view.VISIBLE);
            holder.produtoPreco.setVisibility(view.GONE);
            holder.produtoPreferencia.setVisibility(View.GONE);

            produtos.setNaoContem(true);
            produtos.setPreco(null);
            view.setBackgroundColor(Color.parseColor("#ADD8E6"));

        } else {

            if(produtos.getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")){

                if (produtos.getPrecoKG() != null && produtos.getPrecoKG().contains(".")) {
                    precoDouble = Double.parseDouble(produtos.getPrecoKG());
                    holder.produtoPreco.setText(produtos.getPrecoKG() == null ? "R$ 00,00" : "R$ " + precoFinal.format(precoDouble));
                } else {
                    holder.produtoPreco.setText(produtos.getPrecoKG() == null ? "R$ 00,00" : "R$ " + produtos.getPrecoKG());
                }

            }else{

                if (produtos.getPreco() != null && produtos.getPreco().contains(".")) {
                    precoDouble = Double.parseDouble(produtos.getPreco());
                    holder.produtoPreco.setText(produtos.getPreco() == null ? "R$ 00,00" : "R$ " + precoFinal.format(precoDouble));
                } else {
                    holder.produtoPreco.setText(produtos.getPreco() == null ? "R$ 00,00" : "R$ " + produtos.getPreco());
                }
            }

            holder.txtKg.setVisibility(produtos.getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo") ? View.VISIBLE : View.GONE);

            if(!holder.produtoPreco.getText().toString().equalsIgnoreCase("R$ 00,00")){
                view.setBackgroundColor(Color.parseColor("#ADD8E6"));
            }

            holder.produtoPreco.setVisibility(view.VISIBLE);
            holder.naoContem.setVisibility(view.GONE);
        }

    }

    private void deletarClick(ViewHolder finalHolder,Produtos produtos){

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

    private void verificarDeletar(ViewHolder finalHolder,Produtos produtos){

        if(!produtos.isDeletou()) {
            finalHolder.produtoNome.setPaintFlags(0);
            finalHolder.produtoQuant.setPaintFlags(0);
            finalHolder.produtoPreco.setPaintFlags(0);
            finalHolder.usuarioLista.setPaintFlags(0);
        }else{
            finalHolder.produtoNome.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            finalHolder.produtoQuant.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            finalHolder.produtoPreco.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            finalHolder.usuarioLista.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        }
    }

}