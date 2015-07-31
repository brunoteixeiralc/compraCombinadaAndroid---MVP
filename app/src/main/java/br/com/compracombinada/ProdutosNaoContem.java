package br.com.compracombinada;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterCotacoesUsuario;
import br.com.compracombinada.adpater.ListAdapterEventos;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaPesquisaCotacoes;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaPesquisaProdutosNaoContem;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class ProdutosNaoContem extends Fragment {

    private View view;
    private Fragment fragment;
    private ListView listView;
    private Usuario usuario;
    private ListAdapterEventos listAdapterEventos;
    private List<Evento> eventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.produtos_nao_contem,container,false);

        usuario = (Usuario) getArguments().get("usuario");

        new AsyncTaskCompraColetivaPesquisaProdutosNaoContem(ProdutosNaoContem.this).execute(usuario.getId());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putInt("eventoId", ((Evento) listAdapterEventos.getItem(i)).getId());
                fragment = new ProdutosNaoContemListaDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

            }
        });


        return view;
    }

    public void retornoAsyncTaskCompraCombinadaPesquisaCotacao(String jsonString){

        convertJsonStringCotacao(jsonString);

        listAdapterEventos = new ListAdapterEventos(this.getActivity(), eventos);

        listView.setAdapter(listAdapterEventos);

    }

    public void convertJsonStringCotacao(String jsonString){

        Gson gson = new Gson();
        eventos = new ArrayList<Evento>();
        eventos = gson.fromJson(jsonString, new TypeToken<List<Evento>>(){}.getType());

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(11);
        super.onAttach(activity);
    }

}
