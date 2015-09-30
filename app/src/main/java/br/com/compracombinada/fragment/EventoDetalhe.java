package br.com.compracombinada.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.R;
import br.com.compracombinada.adapter.ListAdapterLista;
import br.com.compracombinada.adapter.ListAdapterLocal;
import br.com.compracombinada.adapter.ListAdapterUsuario;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Local;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.utils.Utils;

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
    private Button btnCompraColetiva;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list_evento_detalhe, container, false);

        evento = new Evento();
        evento = (Evento) getArguments().get("eventoDetalhe");


        for (Lista l : evento.getListas()) {
            for (Produtos p : l.getProdutos()) {
                p.setUsuarioNome(l.getUsuario().getNome());
            }
        }

        listLista = new ArrayList<Lista>();
        listLista.addAll(evento.getListas());

        listUsuario = new ArrayList<Usuario>();
        listUsuario.addAll(evento.getUsuarioConvidados());

        listLocais = new ArrayList<Local>();
        listLocais.addAll(evento.getLocais());

        listViewConvidados = (ListView) view.findViewById(R.id.list_convidados);
        listViewConvidados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("convidado", (Usuario) listAdapterUsuario.getItem(i));
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
                bundle.putSerializable("listaDetalhe", (Lista) listAdapterLista.getItem(i));
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

        btnCompraColetiva = (Button) view.findViewById(R.id.button);
        if (evento.isTemCotacao())
            btnCompraColetiva.setVisibility(View.GONE);

        btnCompraColetiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Produtos> listProdutosCompraColetiva = new ArrayList<Produtos>();

                for (int i = 0; i < listLista.size(); i++) {
                    listProdutosCompraColetiva.addAll(listLista.get(i).getProdutos());
                }

                Bundle bundle = new Bundle();

                Cotacao cotacao = new Cotacao();
                cotacao.setEvento(evento);
                Lista lista = new Lista();
                lista.setProdutos(listProdutosCompraColetiva);
                cotacao.setListaCotacao(lista);
                prefs = EventoDetalhe.this.getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                cotacao.setUsuario((Usuario) Utils.convertJsonStringToObject(prefs.getString("jsonString", null)));
                lista.setNome("Cotacão " + cotacao.getUsuario().getNome());

                bundle.putSerializable("cotacao", cotacao);
                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutosCompraColetiva);
                bundle.putSerializable("listLocaisEvento", (ArrayList<Local>) listLocais);
                fragment = new EventoEscolherLocal();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
