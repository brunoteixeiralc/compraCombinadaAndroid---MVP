package br.com.compracombinada;

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
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterExpProdutosCompraColetiva;
import br.com.compracombinada.adpater.ListAdapterProdutosCompraColetiva;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.ProdutoPreferencia;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.util.DialogFragmentCotacao;
import br.com.compracombinada.util.DialogFragmentGramas;

/**
 * Created by bruno on 21/08/14.
 */
public class CotacoesListaDetalhe extends Fragment {

    private View view;
    private ListView listView;
    private List<Produtos> listProdutos;
    private ListAdapterProdutosCompraColetiva listAdapterProdutos;
    private Fragment fragment;
    private Cotacao cotacao;
    private DialogFragmentGramas dialogFragmentGramas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        setHasOptionsMenu(true);

        cotacao = (Cotacao) getArguments().get("cotacao");

        listProdutos = new ArrayList<Produtos>();

        if(cotacao != null) {

            for (Produtos produto : cotacao.getListaCotacao().getProdutos()) {
                if (Float.valueOf(produto.getPreco()) > 0) {
                    for (Lista l : cotacao.getEvento().getListas()) {
                        for (Produtos p : l.getProdutos()) {
                            if (p.getProduto().getNome().equalsIgnoreCase(produto.getProduto().getNome())) {
                                produto.setUsuarioNome(l.getUsuario().getNome());
                                break;
                            }
                        }

                        break;
                    }
                    listProdutos.add(produto);
                }
            }
        }else{

            listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));
        }

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(((Produtos)listAdapterProdutos.getItem(i)).getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")){

                    Produtos ps = ((Produtos) listAdapterProdutos.getItem(i));

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produto", ps);
                    dialogFragmentGramas = DialogFragmentGramas.newInstance();
                    dialogFragmentGramas.setArguments(bundle);
                    dialogFragmentGramas.setTargetFragment(CotacoesListaDetalhe.this, 1);
                    dialogFragmentGramas.show(CotacoesListaDetalhe.this.getActivity().getSupportFragmentManager(), "dialog_cotacao_gramas");

                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", (Produtos) listAdapterProdutos.getItem(i));
                fragment = new ProdutoDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

                return true;
            }
        });

        listAdapterProdutos = new ListAdapterProdutosCompraColetiva(this, listProdutos);
        listView.setAdapter(listAdapterProdutos);

        Collections.sort(listProdutos, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Produtos p1 = (Produtos) o1;
                Produtos p2 = (Produtos) o2;
                return p1.getProduto().getNome().compareToIgnoreCase(p2.getProduto().getNome());
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.add:

                Bundle bundle = new Bundle();
                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
                bundle.putString("fragment", "cotacoesListaDetalhe");
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
                            break;
                        }
                    }

                    listAdapterProdutos = new ListAdapterProdutosCompraColetiva(this, listProdutos);
                    listView.setAdapter(listAdapterProdutos);

                    AlertDialog.Builder builder = new AlertDialog.Builder(CotacoesListaDetalhe.this.getActivity());
                    builder.setMessage("Medida e preço do produto atualizado com sucesso")
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

}
