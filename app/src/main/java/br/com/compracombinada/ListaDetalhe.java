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
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterLista;
import br.com.compracombinada.adpater.ListAdapterProdutos;
import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Usuario;

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

        view = inflater.inflate(R.layout.list,container,false);

        lista = new Lista();
        lista = (Lista) getArguments().get("listaDetalhe");

        listProdutos = new ArrayList<Produtos>();
        listProdutos.addAll(lista.getProdutos());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto",(Produtos)listAdapterProdutos.getItem(i));
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

        return view;
    }
}
