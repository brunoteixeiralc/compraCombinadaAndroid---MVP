package br.com.compracombinada.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.R;
import br.com.compracombinada.activity.MainActivity;
import br.com.compracombinada.adapter.ListAdapterLista;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaListas;
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
    private List<Lista> listas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        usuario = (Usuario) getArguments().get("usuario");

        new AsyncTaskCompraColetivaListas(Listas.this).execute(usuario.getId());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                fragment = new ListaDetalhe();
                Bundle bundle = new Bundle();
                bundle.putSerializable("listaDetalhe", (Lista) listAdapterLista.getItem(i));
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaListas(String jsonString) {

        listLista = new ArrayList<Lista>();

        convertJsonStringListas(jsonString);

        listAdapterLista = new ListAdapterLista(Listas.this.getActivity(), listas);

        listView.setAdapter(listAdapterLista);

    }

    public void convertJsonStringListas(String jsonString) {

        Gson gson = new Gson();
        listas = gson.fromJson(jsonString, new TypeToken<List<Lista>>() {
        }.getType());

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(4);
        super.onAttach(activity);
    }
}
