package br.com.compracombinada;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import br.com.compracombinada.adpater.ListAdapterProduto;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddListaProdutoCotacao;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaProdutos;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.model.Lista;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.UsuarioSingleton;
import br.com.compracombinada.util.DialogFragmentBuscaProduto;
import android.support.v7.app.ActionBar.LayoutParams;

/**
 * Created by bruno on 21/08/14.
 */
public class BuscarProdutos extends Fragment {

    private View view;
    private ListView listView;
    private List<Produto> listProdutosBusca;
    private ListAdapterProduto listAdapterProduto;
    private DialogFragmentBuscaProduto dialogFragment;
    private Fragment fragment;
    private List<Produtos> listProdutos;
    private MenuItem item;
    private EditText editsearch;
    private Double valorTotalDouble;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.list, container, false);

        setHasOptionsMenu(true);

        listProdutos = new ArrayList<Produtos>();
        listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));

        valorTotalDouble = (Double) getArguments().get("valorTotal");

        new AsyncTaskCompraColetivaProdutos(BuscarProdutos.this).execute();

        listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", (Produto) listAdapterProduto.getItem(i));
                bundle.putString("fragment", (String) getArguments().get("fragment"));
                dialogFragment = DialogFragmentBuscaProduto.newInstance();
                dialogFragment.setArguments(bundle);
                dialogFragment.setTargetFragment(BuscarProdutos.this, 1);
                dialogFragment.show(BuscarProdutos.this.getActivity().getSupportFragmentManager(), "dialog_busca_produto");

            }
        });

        return view;
    }

    public void retornoAsyncTaskCompraCombinadaProdutos(String jsonString){

        listProdutosBusca = new ArrayList<Produto>();

        Gson gson = new Gson();
        listProdutosBusca = gson.fromJson(jsonString, new TypeToken<List<Produto>>() {
        }.getType());


        listAdapterProduto = new ListAdapterProduto(this.getActivity(), listProdutosBusca);
        listView.setAdapter(listAdapterProduto);

        Collections.sort(listProdutosBusca, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Produto p1 = (Produto) o1;
                Produto p2 = (Produto) o2;
                return p1.getNome().compareToIgnoreCase(p2.getNome());
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:

                if (resultCode == Activity.RESULT_OK) {

                    dialogFragment.dismiss();

                    Produto p = (Produto) dialogFragment.getArguments().get("produto");

                    Produtos ps = new Produtos();
                    ps.setProduto(p);
                    ps.setNaoContem(false);
                    ps.setPreco(data.getStringExtra("preco"));
                    if(ps.getPreco().isEmpty())
                        ps.setPreco(null);
                    ps.setUsuarioNome(UsuarioSingleton.getInstance().getUsuario().getNome());
                    ps.setQuantidade(Integer.parseInt(data.getStringExtra("quantidade")));
                    listProdutos.add(ps);

                    valorTotalDouble = valorTotalDouble + calculoQuantidadePreco(Double.parseDouble(data.getStringExtra("preco").toString().replace(",",".")),ps.getQuantidade());

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
                    bundle.putDouble("valorTotal", valorTotalDouble);
                    bundle.putSerializable("cotacao", getArguments().getSerializable("cotacao"));

                    if(data.getStringExtra("fragment").equalsIgnoreCase("listaDetalheCompraColetiva")){
                        ps.setAdicionado("pre-merge");
                        fragment = new ListaDetalheCompraColetiva();
                    }else{
                        ps.setAdicionado("pos-merge");
                        fragment = new CotacoesListaDetalhe();
                    }

                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).addToBackStack(null)
                            .commit();

                    //mandar o preço com "." ao invés de "," pois do outro lado é um float
                    Produtos psTemp = ps;
                    psTemp.setPreco(data.getStringExtra("preco").toString().replace(",", "."));
                    psTemp.setLista(((Cotacao) getArguments().getSerializable("cotacao")).getListaCotacao());

                    new AsyncTaskCompraColetivaAddListaProdutoCotacao(BuscarProdutos.this.getActivity()).execute(psTemp);

                } else if (resultCode == Activity.RESULT_CANCELED) {

                    dialogFragment.dismiss();
                }

                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main_add, menu) ;

        item = menu.findItem(R.id.action_search);
        item.setVisible(true);

        editsearch = (EditText) MenuItemCompat.getActionView(item);
        @SuppressWarnings("deprecation")
        LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT);
        editsearch.setLayoutParams(lparams);
        editsearch.setHint("Buscar");
        editsearch.setTextColor(Color.BLACK);

        editsearch.addTextChangedListener(textWatcher);

        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                editsearch.setText("");
                editsearch.clearFocus();
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                editsearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) BuscarProdutos.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.newProduct:

                Bundle bundle = new Bundle();
                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
                bundle.putString("fragment", (String) getArguments().get("fragment"));
                bundle.putDouble("valorTotal", (Double) getArguments().get("valorTotal"));
                bundle.putSerializable("listaCotacao", ((Cotacao) getArguments().getSerializable("cotacao")).getListaCotacao());
                fragment = new AdicionarProduto();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

        }

        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {

        }
        @Override
        public void afterTextChanged(Editable s) {

            String text = editsearch.getText().toString()
                    .toLowerCase(Locale.getDefault());
            if(listAdapterProduto != null)
                listAdapterProduto.filter(text);

        }

    };

    private Double calculoQuantidadePreco(double preco, int quantidade){
        return preco * quantidade;
    }

}
