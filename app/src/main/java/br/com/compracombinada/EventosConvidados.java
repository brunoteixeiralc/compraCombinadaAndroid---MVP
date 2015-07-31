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

import br.com.compracombinada.adpater.ListAdapterCotacoesUsuario;
import br.com.compracombinada.adpater.ListAdapterEventos;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaEventosConvidados;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.EventoConvidado;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class EventosConvidados extends Fragment {

    private List<Evento> listEvento;
    private ListView listView;
    private Usuario usuario;
    private View view;
    private ListAdapterEventos listAdapterEventos;
    private Fragment fragment;
    private List<EventoConvidado> eventosConvidados;
    private List<Evento> eventos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        usuario = (Usuario) getArguments().get("usuario");

        new AsyncTaskCompraColetivaEventosConvidados(EventosConvidados.this).execute(usuario.getId());

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("eventoDetalhe", (Evento) listAdapterEventos.getItem(i));
                fragment = new EventoDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();


            }
        });

        return view;
    }


    public void retornoAsyncTaskCompraCombinadaEventosConvidados(String jsonString) {

        listEvento = new ArrayList<Evento>();

        convertJsonStringEventosConvidado(jsonString);

        eventos = new ArrayList<Evento>();
        for (EventoConvidado eventoC : eventosConvidados) {
            eventos.add(eventoC.getEvento());
        }

        listAdapterEventos = new ListAdapterEventos(this.getActivity(), eventos);

        listView.setAdapter(listAdapterEventos);

    }

    public void convertJsonStringEventosConvidado(String jsonString) {

        Gson gson = new Gson();
        eventosConvidados = gson.fromJson(jsonString, new TypeToken<List<EventoConvidado>>() {
        }.getType());

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(3);
        super.onAttach(activity);
    }


}

