package br.com.compracombinada;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.compracombinada.adpater.ListAdapterProdutosCompraColetiva;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddCotacao;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaValidadeCotacao;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.util.DialogFragment;
import br.com.compracombinada.util.Utils;

/**
 * Created by bruno on 21/08/14.
 */
public class ListaDetalheCompraColetiva extends Fragment {

    private View view;
    private ListView listView;
    private List<Produtos> listProdutos;
    private ListAdapterProdutosCompraColetiva listAdapterProdutos;
    private Fragment fragment;
    private DialogFragment dialogFragment;
    private Cotacao cotacao;
    private SharedPreferences prefs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        setHasOptionsMenu(true);

        cotacao = (Cotacao) getArguments().get("cotacao");

        listProdutos = new ArrayList<Produtos>();
        listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", (Produtos) listAdapterProdutos.getItem(i));
                dialogFragment = DialogFragment.newInstance();
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(ListaDetalheCompraColetiva.this, 1);
                dialogFragment.show(ListaDetalheCompraColetiva.this.getActivity().getSupportFragmentManager(), "dialog");

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", (Produtos) listAdapterProdutos.getItem(i));
                fragment = new ProdutoDetalhe();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

                return true;
            }
        });


        listAdapterProdutos = new ListAdapterProdutosCompraColetiva(this.getActivity(), listProdutos);
        listView.setAdapter(listAdapterProdutos);

        Collections.sort(listProdutos, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Produtos p1 = (Produtos) o1;
                Produtos p2 = (Produtos) o2;
                return p1.getProduto().getNome().compareToIgnoreCase(p2.getProduto().getNome());
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:

                if (resultCode == Activity.RESULT_OK) {

                    dialogFragment.dismiss();

                    Produtos p = (Produtos) dialogFragment.getArguments().get("produto");

                    for (int i = 0; i < listProdutos.size(); i++) {
                        if (p.equals(listProdutos.get(i))) {

                            listProdutos.get(i).setNaoContem(data.getBooleanExtra("naoContem", false));
                            listProdutos.get(i).setPreco(data.getStringExtra("preco"));
                            if (listProdutos.get(i).getPreco().isEmpty())
                                listProdutos.get(i).setPreco(null);

                            break;
                        }
                    }

                    listAdapterProdutos = new ListAdapterProdutosCompraColetiva(this.getActivity(), listProdutos);
                    listView.setAdapter(listAdapterProdutos);


                } else if (resultCode == Activity.RESULT_CANCELED) {

                    dialogFragment.dismiss();
                }

                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        for (Produtos p : cotacao.getListaCotacao().getProdutos()) {

            if ((p.getPreco() != null && !p.isNaoContem()) || (p.getPreco() == null && p.isNaoContem())) {

                if (p.getPreco() != null)
                    p.setPreco(p.getPreco().replace(",", "."));

            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListaDetalheCompraColetiva.this.getActivity());
                builder.setMessage("Cotar todos os produtos são obrigatórios")
                        .setTitle("Alerta Compra Combinada");
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }

        }

        new AsyncTaskCompraColetivaAddCotacao(ListaDetalheCompraColetiva.this).execute(cotacao);

        return super.onOptionsItemSelected(item);
    }

    public void retornoAsyncTaskCompraCombinadaCotacao(String retorno) {

        AlertDialog.Builder builder = new AlertDialog.Builder(ListaDetalheCompraColetiva.this.getActivity());
        builder.setMessage("Após a finalização do evento, os produtos mais baratos irá aparecer em Suas Cotações!!")
                .setTitle("Compra Combinada");
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
