package br.com.compracombinada.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.compracombinada.R;
import br.com.compracombinada.adapter.ListAdapterExpProdutosCompraColetiva;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.dialog.DialogFragmentGramas;
import br.com.compracombinada.model.UsuarioSingleton;

/**
 * Created by bruno on 21/08/14.
 */
public class CotacoesListaDetalhe extends Fragment {

    private View view;
    private ExpandableListView listView;
    private List<Produtos> listProdutos;
    private ListAdapterExpProdutosCompraColetiva listAdapterProdutos;
    private Fragment fragment;
    private Cotacao cotacao;
    private DialogFragmentGramas dialogFragmentGramas;
    private Map<String,List<Produtos>> expListProdutos;
    private List<String> keys;
    private TextView valorTotal;
    private Double valorTotalDouble = 0.0;
    private MenuItem item;
    private MenuItem itemAdd;
    private boolean[] groupExpandedArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.expandable_list, container, false);

        setHasOptionsMenu(true);

        expListProdutos = new HashMap<String,List<Produtos>>();
        listProdutos = new ArrayList<Produtos>();

        valorTotal = (TextView) view.findViewById(R.id.valorTotal);
        valorTotal.setVisibility(View.VISIBLE);
        valorTotal.setText("Valor total de R$ 00,00");

        cotacao = (Cotacao) getArguments().get("cotacao");

        if((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva") != null)
            listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));

        if(cotacao != null && listProdutos.size() == 0) {

            for (Produtos produto : cotacao.getListaCotacao().getProdutos()) {
                if (Float.valueOf(produto.getPreco()) > 0) {
                    for (Lista l : cotacao.getEvento().getListas()) {
                        for (Produtos p : l.getProdutos()) {
                            if (p.getProduto().getNome().equalsIgnoreCase(produto.getProduto().getNome())) {
                                produto.setUsuarioNome(l.getUsuario().getNome());
                                break;
                            }else if(produto.getProdutoGenerico() != null){
                                if(p.getProduto().getNome().equalsIgnoreCase(produto.getProdutoGenerico().getNome())){
                                    produto.setUsuarioNome(l.getUsuario().getNome());
                                    break;
                                }
                            }else{
                                //esse é somente caso o produto seja adicionado manualmente na cotacao.Pois após
                                //o mesmo , precisa colocar o nome da lista do dono.
                                produto.setUsuarioNome(UsuarioSingleton.getInstance().getUsuario().getNome());
                            }
                        }

                        break;
                    }
                    listProdutos.add(produto);
                }
            }
        }
//        else{
//
//            listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));
//        }

        listView = (ExpandableListView) view.findViewById(R.id.list);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                keys = new ArrayList<String>(expListProdutos.keySet());

                Produtos ps = expListProdutos.get(keys.get(i)).get(i1);

                if(ps.getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")){

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produto", ps);
                    dialogFragmentGramas = DialogFragmentGramas.newInstance();
                    dialogFragmentGramas.setArguments(bundle);
                    dialogFragmentGramas.setTargetFragment(CotacoesListaDetalhe.this, 1);
                    dialogFragmentGramas.show(CotacoesListaDetalhe.this.getActivity().getSupportFragmentManager(), "dialog_cotacao_gramas");

                    //zero o valor pois o adpater vai ser populado novamento.
                    valorTotalDouble = 0.0;

                    int numberOfGroups = listAdapterProdutos.getGroupCount();
                    groupExpandedArray = new boolean[numberOfGroups];
                    for (int j = 0; j < numberOfGroups; j++)
                        groupExpandedArray[j] = listView.isGroupExpanded(j);


                }

                return true;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (ExpandableListView.getPackedPositionType(l) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(l);
                    int childPosition = ExpandableListView.getPackedPositionChild(l);


                    keys = new ArrayList<String>(expListProdutos.keySet());

                    Produtos ps = expListProdutos.get(keys.get(groupPosition)).get(childPosition);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produto",ps);
                    fragment = new ProdutoDetalhe();
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).addToBackStack(null)
                            .commit();

                    //zero o valor pois o adpater vai ser populado novamento.
                    valorTotalDouble = 0.0;


                    return true;
                }

                return false;
            }
        });

        for (Produtos p : listProdutos){

            if(expListProdutos.containsKey(p.getProduto().getDivisao().getNome())){
                expListProdutos.get(p.getProduto().getDivisao().getNome()).add(p);
            }else{
                List<Produtos> lp = new ArrayList<Produtos>();
                lp.add(p);
                expListProdutos.put(p.getProduto().getDivisao().getNome(),lp);
            }

            //verificar se é em KG ou Quantidade
            if(p.getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")){

                valorTotalDouble = valorTotalDouble + Double.parseDouble(p.getPreco().replace(",","."));

            }else{

                valorTotalDouble = valorTotalDouble + calculoQuantidadePreco(Double.parseDouble(p.getPreco().replace(",", ".")), p.getQuantidade());
            }

        }

        //colocar em order alfabética a lista do grupo de alimentos
        for(Map.Entry<String,List<Produtos>> entry : expListProdutos.entrySet()) {

            Collections.sort(entry.getValue(), new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    Produtos p1 = (Produtos) o1;
                    Produtos p2 = (Produtos) o2;
                    return p1.getProduto().getNome().compareToIgnoreCase(p2.getProduto().getNome());
                }
            });

        }


        DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        String formatted = precoFinal.format(valorTotalDouble);

        valorTotal.setText("Valor total de R$ " + formatted);

        listAdapterProdutos = new ListAdapterExpProdutosCompraColetiva(this, expListProdutos,false);
        listView.setAdapter(listAdapterProdutos);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

        item = menu.findItem(R.id.add);

        item.setVisible(true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add:

                Bundle bundle = new Bundle();
                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
                bundle.putString("fragment", "cotacoesListaDetalhe");
                bundle.putDouble("valorTotal", valorTotalDouble);
                bundle.putSerializable("cotacao", cotacao);
                fragment = new BuscarProdutos();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:

                if (resultCode == Activity.RESULT_OK)
                {
                    Produtos p = (Produtos) dialogFragmentGramas.getArguments().get("produto");

                    for (int i = 0; i < listProdutos.size(); i++) {
                        if (p.equals(listProdutos.get(i))) {
                            listProdutos.get(i).setQuantidade(Integer.parseInt(data.getStringExtra("quantidade")));
                            listProdutos.get(i).setPreco(p.getPreco());
                        }

                        if(listProdutos.get(i).getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")){
                            valorTotalDouble = valorTotalDouble + Double.parseDouble(listProdutos.get(i).getPreco().replace(",","."));
                        }else{
                            valorTotalDouble = valorTotalDouble + calculoQuantidadePreco(Double.parseDouble(listProdutos.get(i).getPreco().replace(",", ".")), listProdutos.get(i).getQuantidade());
                        }
                    }

                    DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
                    String formatted = precoFinal.format(valorTotalDouble);

                    valorTotal.setText("Valor total de R$ " + formatted);

                    expListProdutos = new HashMap<String, List<Produtos>>();

                    for (Produtos prod : listProdutos){

                        if(expListProdutos.containsKey(prod.getProduto().getDivisao().getNome())){
                            expListProdutos.get(prod.getProduto().getDivisao().getNome()).add(prod);
                        }else{
                            List<Produtos> lp = new ArrayList<Produtos>();
                            lp.add(prod);
                            expListProdutos.put(prod.getProduto().getDivisao().getNome(),lp);
                        }
                    }

                    listAdapterProdutos = new ListAdapterExpProdutosCompraColetiva(this, expListProdutos,false);
                    listView.setAdapter(listAdapterProdutos);

                    for (int j=0;j<groupExpandedArray.length;j++)
                        if (groupExpandedArray[j] == true)
                            listView.expandGroup(j);

                    AlertDialog.Builder builder = new AlertDialog.Builder(CotacoesListaDetalhe.this.getActivity());
                    builder.setMessage("Medida e preço do produto atualizados com sucesso!")
                            .setTitle("Compra Combinada");
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    dialogFragmentGramas.dismiss();

                } else if (resultCode == Activity.RESULT_CANCELED) {

                    dialogFragmentGramas.dismiss();
                }

                break;
        }
    }

    private Double calculoQuantidadePreco(double preco, int quantidade){
        return preco * quantidade;
    }

}
