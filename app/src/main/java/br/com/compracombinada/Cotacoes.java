package br.com.compracombinada;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterAmizade;
import br.com.compracombinada.adpater.ListAdapterCotacoesUsuario;
import br.com.compracombinada.adpater.ListAdapterEventos;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaLogin;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaPesquisaCotacoes;
import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class Cotacoes extends Fragment {

    private View view;
    private Fragment fragment;
    private Button btnAtualizar;
    private List<Evento> listEvento;
    private ListView listView;
    private Usuario usuario;
    private ListAdapterCotacoesUsuario listAdapterCotacoesUsuario;
    private List<Cotacao> cotacoesUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cotacao,container,false);

        usuario = (Usuario) getArguments().get("usuario");

        new AsyncTaskCompraColetivaPesquisaCotacoes(Cotacoes.this).execute(usuario.getId());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("cotacao",(Cotacao)listAdapterCotacoesUsuario.getItem(i));
                fragment = new CotacoesListaDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();



            }
        });


        btnAtualizar = (Button)view.findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTaskCompraColetivaPesquisaCotacoes(Cotacoes.this).execute(usuario.getId());
            }
        });

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaPesquisaCotacao(String jsonString){

        convertJsonStringCotacao(jsonString);

        listAdapterCotacoesUsuario = new ListAdapterCotacoesUsuario(this.getActivity(), cotacoesUsuario);

        listView.setAdapter(listAdapterCotacoesUsuario);

    }

    public void convertJsonStringCotacao(String jsonString){

        Gson gson = new Gson();
        cotacoesUsuario = new ArrayList<Cotacao>();
        cotacoesUsuario = gson.fromJson(jsonString, new TypeToken<List<Cotacao>>(){}.getType());

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(6);
        super.onAttach(activity);
    }

}
