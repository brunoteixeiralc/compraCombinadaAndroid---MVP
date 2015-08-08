package br.com.compracombinada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterProdutos;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 21/08/14.
 */
public class ListaDetalhe extends android.support.v4.app.Fragment {

    private View view;
    private Lista lista;
    private ListView listView;
    private List<Produtos> listProdutos;
    private ListAdapterProdutos listAdapterProdutos;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        lista = new Lista();
        lista = (Lista) getArguments().get("listaDetalhe");

        listProdutos = new ArrayList<Produtos>();
        listProdutos.addAll(lista.getProdutos());

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

        listAdapterProdutos = new ListAdapterProdutos(this.getActivity(), listProdutos);
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
}
