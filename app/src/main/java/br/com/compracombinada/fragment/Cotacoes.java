package br.com.compracombinada.fragment;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.R;
import br.com.compracombinada.activity.MainActivity;
import br.com.compracombinada.adapter.ListAdapterCotacoesUsuario;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaPesquisaCotacoes;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.model.UsuarioSingleton;

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
    private TextView msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cotacao, container, false);

        usuario = UsuarioSingleton.getInstance().getUsuario();

        msg = (TextView) view.findViewById(R.id.msg);

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

        if(cotacoesUsuario.size() > 0){

            msg.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            btnAtualizar.setVisibility(View.VISIBLE);

            listAdapterCotacoesUsuario = new ListAdapterCotacoesUsuario(this.getActivity(), cotacoesUsuario);

            listView.setAdapter(listAdapterCotacoesUsuario);

        }else{

            msg.setVisibility(View.VISIBLE);
            msg.setText("Nenhuma cotação");
            listView.setVisibility(View.GONE);
            btnAtualizar.setVisibility(View.GONE);

        }


    }

    public void convertJsonStringCotacao(String jsonString){

        Gson gson = new Gson();
        cotacoesUsuario = new ArrayList<Cotacao>();
        cotacoesUsuario = gson.fromJson(jsonString, new TypeToken<List<Cotacao>>(){}.getType());

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        ((MainActivity) activity).onSectionAttached(6);
        super.onAttach(activity);
    }

}
