package br.com.compracombinada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterLocal;
import br.com.compracombinada.adpater.ListAdapterProdutos;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Local;
import br.com.compracombinada.model.Produtos;

/**
 * Created by bruno on 21/08/14.
 */
public class EventoEscolherLocal extends android.support.v4.app.Fragment {

    private View view;
    private ListView listView;
    private List<Local> listLocais;
    private ListAdapterLocal listAdapterLocal;
    private Fragment fragment;
    private List<Produtos> listProdutos;
    private Cotacao cotacao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list_local_escolher, container, false);

        listLocais = new ArrayList<Local>();
        listLocais.addAll((Collection<? extends Local>) getArguments().get("listLocaisEvento"));

        listProdutos = new ArrayList<Produtos>();
        listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));

        cotacao = (Cotacao) getArguments().get("cotacao");

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cotacao.setLocalEscolhido((Local) listAdapterLocal.getItem(i));

                Bundle bundle = new Bundle();
                bundle.putSerializable("cotacao", cotacao);
                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);

                fragment = new ListaDetalheCompraColetiva();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();


            }
        });

        listAdapterLocal = new ListAdapterLocal(this.getActivity(), listLocais);
        listView.setAdapter(listAdapterLocal);

        Collections.sort(listLocais, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Local l1 = (Local) o1;
                Local l2 = (Local) o2;
                return l1.getNome().compareToIgnoreCase(l2.getNome());
            }
        });

        return view;
    }
}
