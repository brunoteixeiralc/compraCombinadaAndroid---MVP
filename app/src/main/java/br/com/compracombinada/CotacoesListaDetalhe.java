package br.com.compracombinada;

import android.app.AlertDialog;
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
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterProdutosCompraColetiva;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddCotacao;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.util.DialogFragmentCotacao;

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

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", (Produtos) listAdapterProdutos.getItem(i));
                fragment = new ProdutoDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

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

}
