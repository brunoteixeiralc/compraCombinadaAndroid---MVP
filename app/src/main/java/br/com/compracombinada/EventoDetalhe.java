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

import br.com.compracombinada.adpater.ListAdapterAmizade;
import br.com.compracombinada.adpater.ListAdapterLista;
import br.com.compracombinada.adpater.ListAdapterLocal;
import br.com.compracombinada.adpater.ListAdapterProdutos;
import br.com.compracombinada.adpater.ListAdapterUsuario;
import br.com.compracombinada.model.Amizade;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Local;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Usuario;

/**
 * Created by bruno on 21/08/14.
 */
public class EventoDetalhe extends Fragment {

    private View view;
    private Evento evento;
    private ListView listViewConvidados;
    private ListView listViewLista;
    private ListView listViewLocal;
    private List<Usuario> listUsuario;
    private List<Lista> listLista;
    private List<Local> listLocais;
    private ListAdapterUsuario listAdapterUsuario;
    private ListAdapterLista listAdapterLista;
    private ListAdapterLocal listAdapterLocal;
    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list_evento_detalhe,container,false);

        evento = new Evento();
        evento = (Evento) getArguments().get("eventoDetalhe");

        listUsuario = new ArrayList<Usuario>();
        listUsuario.addAll(evento.getUsuarioConvidados());

        listLista = new ArrayList<Lista>();
        listLista.addAll(evento.getListas());

        listLocais = new ArrayList<Local>();
        listLocais.addAll(evento.getLocais());

        listViewConvidados = (ListView) view.findViewById(R.id.list_convidados);
        listViewConvidados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("convidado",(Usuario)listAdapterUsuario.getItem(i));
                fragment = new ConvidadoDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();


            }
        });

        listViewLista = (ListView) view.findViewById(R.id.list_lista);
        listViewLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                fragment = new ListaDetalhe();
                bundle.putSerializable("listaDetalhe", (Lista)listAdapterLista.getItem(i));
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();


            }
        });

        listViewLocal = (ListView) view.findViewById(R.id.list_locais);
        listViewLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("local",(Local)listAdapterLocal.getItem(i));
                fragment = new LocalDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();


            }
        });


        listAdapterUsuario = new ListAdapterUsuario(this.getActivity(), listUsuario);
        listViewConvidados.setAdapter(listAdapterUsuario);

        listAdapterLista = new ListAdapterLista(this.getActivity(), listLista);
        listViewLista.setAdapter(listAdapterLista);

        listAdapterLocal = new ListAdapterLocal(this.getActivity(), listLocais);
        listViewLocal.setAdapter(listAdapterLocal);

        return view;
    }
}
