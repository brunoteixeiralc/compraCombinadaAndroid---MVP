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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.R;
import br.com.compracombinada.activity.MainActivity;
import br.com.compracombinada.adapter.ListAdapterEventos;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaEventos;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class Eventos extends android.support.v4.app.Fragment {

    private List<Evento> listEvento;
    private ListView listView;
    private Usuario usuario;
    private View view;
    private ListAdapterEventos listAdapterEventos;
    private Fragment fragment;
    private List<Evento> eventos;
    private TextView msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        msg = (TextView) view.findViewById(R.id.msg);

        usuario = (Usuario) getArguments().get("usuario");



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

        if(eventos == null){
            new AsyncTaskCompraColetivaEventos(Eventos.this).execute(usuario.getId());
        }else{
            listAdapterEventos = new ListAdapterEventos(Eventos.this.getActivity(), eventos);
            listView.setAdapter(listAdapterEventos);
        }

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaEventos(String jsonString) {

        listEvento = new ArrayList<Evento>();

        convertJsonStringEventos(jsonString);

        if (eventos.size() > 0) {

            msg.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            listAdapterEventos = new ListAdapterEventos(Eventos.this.getActivity(), eventos);

            listView.setAdapter(listAdapterEventos);

        }else{

            msg.setVisibility(View.VISIBLE);
            msg.setText("Nenhum eventos");
            listView.setVisibility(View.GONE);
        }

    }

    public void convertJsonStringEventos(String jsonString) {

        Gson gson = new Gson();
        eventos = gson.fromJson(jsonString, new TypeToken<List<Evento>>() {
        }.getType());

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(2);
        super.onAttach(activity);
    }


}

