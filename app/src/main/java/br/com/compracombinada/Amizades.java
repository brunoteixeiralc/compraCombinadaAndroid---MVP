package br.com.compracombinada;

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

import br.com.compracombinada.adpater.ListAdapterAmizade;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAmizades;
import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class Amizades extends android.support.v4.app.Fragment {

    private ListView listView;
    private View view;
    private ListAdapterAmizade listAdapterAmizade;
    private Usuario usuario;
    private List<Amizade> listAmizade;
    private Fragment fragment;
    private List<Amizade> amizades;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        usuario = (Usuario) getArguments().get("usuario");

        new AsyncTaskCompraColetivaAmizades(Amizades.this).execute(usuario.getId());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("amizade", (Amizade) listAdapterAmizade.getItem(i));
                fragment = new AmizadeDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaAmizades(String jsonString) {

        listAmizade = new ArrayList<Amizade>();

        convertJsonStringAmizades(jsonString);

        listAdapterAmizade = new ListAdapterAmizade(Amizades.this.getActivity(), amizades);

        listView.setAdapter(listAdapterAmizade);

    }

    public void convertJsonStringAmizades(String jsonString) {

        Gson gson = new Gson();
        amizades = gson.fromJson(jsonString, new TypeToken<List<Amizade>>() {
        }.getType());

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(5);
        super.onAttach(activity);
    }
}
