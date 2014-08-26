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
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class Listas extends android.support.v4.app.Fragment {

    private View view;
    private Usuario usuario;
    private ListView listView;
    private List<Lista> listLista;
    private ListAdapterLista listAdapterLista;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list,container,false);

        usuario = (Usuario) getArguments().get("usuario");

        listLista = new ArrayList<Lista>();
        listLista.addAll(usuario.getListas());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                  fragment = new ListaDetalhe();
                  Bundle bundle = new Bundle();
                  bundle.putSerializable("listaDetalhe", (Lista)listAdapterLista.getItem(i));
                  fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).addToBackStack(null)
                            .commit();
            }
        });

        listAdapterLista = new ListAdapterLista(this.getActivity(), listLista);
        listView.setAdapter(listAdapterLista);

        return view;
    }
}
