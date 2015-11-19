package br.com.compracombinada.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.compracombinada.R;
import br.com.compracombinada.adapter.ListAdapterExpProdutosCompraColetiva;
import br.com.compracombinada.asynctask.AsyncTaskCompraColetivaAddCotacao;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.ProdutoPreferencia;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.model.Usuario;
import br.com.compracombinada.model.UsuarioSingleton;
import br.com.compracombinada.dialog.DialogFragmentCotacao;

/**
 * Created by bruno on 21/08/14.
 */
public class ListaDetalheCompraColetiva extends Fragment {

    private View view;
    private ExpandableListView listView;
    private List<Produtos> listProdutos;
    private ListAdapterExpProdutosCompraColetiva listAdapterProdutos;
    private Fragment fragment;
    private DialogFragmentCotacao dialogFragmentCotacao;
    private Cotacao cotacao;
    private TextView valorTotal;
    private Double valorTotalDouble = 0.0;
    private MenuItem item;
    private Map<String,List<Produtos>> expListProdutos;
    private List<String> keys;
    private MenuItem itemAdd;
    private boolean[] groupExpandedArray;
    private boolean isDonoEvento;
    private String jsonProdutos;
    public static final String PREFS_NAME = "offline";
    private SharedPreferences sharedPreferences;
    private boolean fezMerge;
    final Thread.UncaughtExceptionHandler oldHandler = Thread.getDefaultUncaughtExceptionHandler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(
                            Thread paramThread,
                            Throwable paramThrowable
                    ) {

                        SharedPreferences settings = ListaDetalheCompraColetiva.this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        boolean success = settings.edit().remove(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                                cotacao.getEvento().getId())).commit();
                        if(!success){
                            Log.e("Erro compracombinada","Erro ao deletar");
                        }

                        if(!fezMerge) {

                            Gson gson = new Gson();
                            jsonProdutos = gson.toJson(listProdutos);

                            boolean successInsert = settings.edit().putString(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                                    cotacao.getEvento().getId()), jsonProdutos).commit();
                            if (!successInsert) {
                                Log.e("Erro compracombinada", "Erro ao inserir");
                            }
                        }

                        if (oldHandler != null)
                            oldHandler.uncaughtException(
                                    paramThread,
                                    paramThrowable
                            ); //Delegates to Android's error handling
                        else
                            System.exit(2); //Prevents the service/app from freezing
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.expandable_list, container, false);

        expListProdutos = new HashMap<String,List<Produtos>>();
        listProdutos = new ArrayList<Produtos>();
        fezMerge = false;

        setHasOptionsMenu(true);

        valorTotal = (TextView) view.findViewById(R.id.valorTotal);
        valorTotal.setVisibility(View.VISIBLE);
        valorTotal.setText("Valor total de R$ 00,00");

        cotacao = (Cotacao) getArguments().get("cotacao");

        //verifica se é dono do evento
        if(cotacao != null && cotacao.getEvento().getUsuario().getId().equals(UsuarioSingleton.getInstance().getUsuario().getId())){
            isDonoEvento = true;
        }

        //pegar os produtos caso tenha tido algum problema na cotação
        sharedPreferences = ListaDetalheCompraColetiva.this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        jsonProdutos = sharedPreferences.getString(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                cotacao.getEvento().getId()), null);
        if(jsonProdutos != null) {

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Produtos>>() {
            }.getType();
            listProdutos = gson.fromJson(jsonProdutos, type);

            for (Produtos ps:listProdutos) {

                if(ps.getPreco() != null) {
                    if (ps.getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")) {
                        valorTotalDouble = valorTotalDouble + Double.parseDouble(ps.getPreco().replace(",", "."));
                    } else {
                        valorTotalDouble = valorTotalDouble + calculoQuantidadePreco(Double.parseDouble(ps.getPreco().replace(",", ".")), ps.getQuantidade());
                    }
                }
            }

        }else{

            listProdutos.addAll((java.util.Collection<? extends Produtos>) getArguments().get("listProdutosCompraColetiva"));

            //Atualiza o valor total da cotação ao se adicionar um produto novo
            valorTotalDouble = (Double) getArguments().get("valorTotal");
        }

        if(valorTotalDouble != null && valorTotalDouble != 0.0){

            DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
            String formatted = precoFinal.format(valorTotalDouble);

            valorTotal.setText("Valor total de R$ " + formatted);
        }else{
            valorTotalDouble = 0.0;
        }

        listView = (ExpandableListView) view.findViewById(R.id.list);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                keys = new ArrayList<String>(expListProdutos.keySet());

                Produtos ps = expListProdutos.get(keys.get(i)).get(i1);
                Integer usuarioDonoEventoId = cotacao.getEvento().getUsuario().getId();

                if (!ps.isDeletou()) {
//                    if (ps.getPreco() != null)
//                        valorTotalDouble = valorTotalDouble - calculoQuantidadePreco(Double.parseDouble(ps.getPreco().replace(",", ".")), ps.getQuantidade());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produto", ps);
                    bundle.putInt("usuarioDonoEvento", usuarioDonoEventoId);
                  //  bundle.putDouble("valorTotalDouble", valorTotalDouble);
                    dialogFragmentCotacao = DialogFragmentCotacao.newInstance();
                    dialogFragmentCotacao.setArguments(bundle);
                    dialogFragmentCotacao.setTargetFragment(ListaDetalheCompraColetiva.this, 1);
                    dialogFragmentCotacao.show(ListaDetalheCompraColetiva.this.getActivity().getSupportFragmentManager(), "dialog_cotacao");
                }

                int numberOfGroups = listAdapterProdutos.getGroupCount();
                groupExpandedArray = new boolean[numberOfGroups];
                for (int j = 0; j < numberOfGroups; j++)
                    groupExpandedArray[j] = listView.isGroupExpanded(j);


                return false;
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                long packedPosition = listView.getExpandableListPosition(i);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {

                    keys = new ArrayList<String>(expListProdutos.keySet());

                    Produtos ps = expListProdutos.get(keys.get(groupPosition)).get(childPosition);

                    if (!ps.isDeletou()) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("produto", ps);
                        fragment = new ProdutoDetalhe();
                        fragment.setArguments(bundle);

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.container, fragment).addToBackStack(null)
                                .commit();
                    }

                    return true;
                }
                return false;
            }
        });

            for (Produtos p : listProdutos) {

                if (expListProdutos.containsKey(p.getProduto().getDivisao().getNome())) {
                    expListProdutos.get(p.getProduto().getDivisao().getNome()).add(p);
                } else {
                    List<Produtos> lp = new ArrayList<Produtos>();
                    lp.add(p);
                    expListProdutos.put(p.getProduto().getDivisao().getNome(), lp);
                }
            }

        //colocar em order alfabética a lista do grupo de alimentos
        for(Map.Entry<String,List<Produtos>> entry : expListProdutos.entrySet()) {

            Collections.sort(entry.getValue(), new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    Produtos p1 = (Produtos) o1;
                    Produtos p2 = (Produtos) o2;
                    return p1.getProduto().getNome().compareToIgnoreCase(p2.getProduto().getNome());
                }
            });

        }

        listAdapterProdutos = new ListAdapterExpProdutosCompraColetiva(this, expListProdutos,isDonoEvento);
        listView.setAdapter(listAdapterProdutos);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:

                if (resultCode == Activity.RESULT_OK) {

                    dialogFragmentCotacao.dismiss();

                    Produtos p = (Produtos) dialogFragmentCotacao.getArguments().get("produto");

                    for (int i = 0; i < listProdutos.size(); i++) {
                        if (p.equals(listProdutos.get(i))) {

                            if (data.getSerializableExtra("produtoPreferencia") != null) {
                                listProdutos.get(i).setProdutoTempPref(((ProdutoPreferencia) data.getSerializableExtra("produtoPreferencia")).getProduto());
                                data.removeExtra("produtoPreferencia");
                            }

                            listProdutos.get(i).setNaoContem(data.getBooleanExtra("naoContem", false));

                                if (listProdutos.get(i).getProduto().getFamilia().getMedida().equalsIgnoreCase("quilo")) {

                                    if (listProdutos.get(i).isNaoContem()) {

                                        if (listProdutos.get(i).getPreco() != null) {

                                            valorTotalDouble = valorTotalDouble - Double.parseDouble(listProdutos.get(i).getPreco().replace(",", "."));
                                        }

                                    } else {

                                        if (!data.getStringExtra("preco").isEmpty()) {

                                            if (listProdutos.get(i).getPreco() != null) {

                                                valorTotalDouble = valorTotalDouble - Double.parseDouble(listProdutos.get(i).getPreco().replace(",", "."));
                                            }

                                            listProdutos.get(i).setPrecoKG(data.getStringExtra("preco"));

                                            listProdutos.get(i).setPreco(calculoKG(listProdutos.get(i).getQuantidade(), listProdutos.get(i).getPrecoKG()));

                                            if (listProdutos.get(i).getPrecoKG().isEmpty())
                                                listProdutos.get(i).setPrecoKG(null);
                                            else
                                                valorTotalDouble = valorTotalDouble + Double.parseDouble(listProdutos.get(i).getPreco().replace(",", "."));
                                        }
                                    }

                                    break;

                                } else {

                                    if (listProdutos.get(i).isNaoContem()) {

                                        if (listProdutos.get(i).getPreco() != null)
                                            valorTotalDouble = valorTotalDouble - calculoQuantidadePreco(Double.parseDouble(listProdutos.get(i).getPreco().replace(",", ".")), listProdutos.get(i).getQuantidade());

                                    } else {

                                        if (!data.getStringExtra("preco").isEmpty()) {

                                            if (listProdutos.get(i).getPreco() != null)
                                                valorTotalDouble = valorTotalDouble - calculoQuantidadePreco(Double.parseDouble(listProdutos.get(i).getPreco().replace(",", ".")), listProdutos.get(i).getQuantidade());

                                            listProdutos.get(i).setPreco(data.getStringExtra("preco"));

                                            if (listProdutos.get(i).getPreco().isEmpty())
                                                listProdutos.get(i).setPreco(null);
                                            else
                                                valorTotalDouble = valorTotalDouble + calculoQuantidadePreco(Double.parseDouble(listProdutos.get(i).getPreco().replace(",", ".")), listProdutos.get(i).getQuantidade());
                                        }
                                    }

                                    break;
                                }
                        }
                    }

                    expListProdutos = new HashMap<String, List<Produtos>>();

                    for (Produtos prod : listProdutos){

                        if(expListProdutos.containsKey(prod.getProduto().getDivisao().getNome())){
                            expListProdutos.get(prod.getProduto().getDivisao().getNome()).add(prod);
                        }else{
                            List<Produtos> lp = new ArrayList<Produtos>();
                            lp.add(prod);
                            expListProdutos.put(prod.getProduto().getDivisao().getNome(),lp);
                        }
                    }

                    //colocar em order alfabética a lista do grupo de alimentos
                    for(Map.Entry<String,List<Produtos>> entry : expListProdutos.entrySet()) {

                        Collections.sort(entry.getValue(), new Comparator() {

                            @Override
                            public int compare(Object o1, Object o2) {
                                Produtos p1 = (Produtos) o1;
                                Produtos p2 = (Produtos) o2;
                                return p1.getProduto().getNome().compareToIgnoreCase(p2.getProduto().getNome());
                            }
                        });

                    }

                    listAdapterProdutos = new ListAdapterExpProdutosCompraColetiva(this, expListProdutos,isDonoEvento);
                    listView.setAdapter(listAdapterProdutos);

                    DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
                    String formatted = precoFinal.format(valorTotalDouble);

                    valorTotal.setText("Valor total de R$ " + formatted);

                    for (int j=0;j<groupExpandedArray.length;j++)
                        if (groupExpandedArray[j] == true)
                            listView.expandGroup(j);

                } else if (resultCode == Activity.RESULT_CANCELED) {

                    dialogFragmentCotacao.dismiss();
                }

                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);

        item = menu.findItem(R.id.merge);

        item.setVisible(true);

        itemAdd = menu.findItem(R.id.add);

        if(isDonoEvento || (cotacao != null && cotacao.getEvento().getUsuario().getId().equals(UsuarioSingleton.getInstance().getUsuario().getId())))
            itemAdd.setVisible(true);


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.merge:

                cotacao.getListaCotacao().setProdutos(listProdutos);

                for (Produtos p : cotacao.getListaCotacao().getProdutos()) {

                    p.setAdicionado("pre-merge");

                    if ((p.getPreco() != null && (!p.isNaoContem() || !p.isDeletou())) || (p.getPreco() == null && (p.isNaoContem() || p.isDeletou()))) {

                        if (p.getPreco() != null)
                            p.setPreco(p.getPreco().replace(",", "."));

                        if(p.getPrecoKG() != null){
                            p.setPrecoKG(p.getPrecoKG().replace(",","."));
                        }

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

                break;

            case R.id.add:

                //BUSCAR PRODUTO CODE
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
//                bundle.putString("fragment", "listaDetalheCompraColetiva");
//                bundle.putDouble("valorTotal", valorTotalDouble);
//                bundle.putSerializable("cotacao", cotacao);
//                fragment = new BuscarProdutos();
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.container, fragment).addToBackStack(null)
//                        .commit();

                Bundle bundle = new Bundle();
                bundle.putSerializable("listProdutosCompraColetiva", (ArrayList<Produtos>) listProdutos);
                bundle.putString("fragment", "listaDetalheCompraColetiva");
                bundle.putDouble("valorTotal", valorTotalDouble);
                bundle.putSerializable("listaCotacao", cotacao.getListaCotacao());
                bundle.putSerializable("cotacao", cotacao);
                fragment = new AdicionarProduto();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment).addToBackStack(null)
                        .commit();

                //coloquei como true a variavel para ao entrar no onPause(), não fazer o insert
                fezMerge = true;

                sharedPreferences = ListaDetalheCompraColetiva.this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                boolean success = sharedPreferences.edit().remove(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";"
                        +cotacao.getEvento().getId())).commit();
                if (!success) {
                    Log.e("Erro compracombinada", "Erro ao deletar");
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void retornoAsyncTaskCompraCombinadaCotacao(String retorno) {

        fragment = new SucessoMerge();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).addToBackStack(null)
                .commit();

        sharedPreferences = ListaDetalheCompraColetiva.this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean success = sharedPreferences.edit().remove(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                cotacao.getEvento().getId())).commit();
        if (!success) {
            Log.e("Erro compracombinada","Erro ao deletar");
        }

        fezMerge = true;

    }

    private String calculoKG(int quantGramas, String precoKG){

        Double precoKGFormat = Double.parseDouble(precoKG.replace(",", "."));
        Double calculoFinal = (precoKGFormat * quantGramas)/1000;

        DecimalFormat precoFinal = new DecimalFormat("#,#00.00", new DecimalFormatSymbols(new Locale ("pt", "BR")));
        String formatted = precoFinal.format(calculoFinal);

        return formatted;
    }


    private Double calculoQuantidadePreco(double preco, int quantidade){
        return preco * quantidade;
    }

    @Override
    public void onDestroy() {

        sharedPreferences = ListaDetalheCompraColetiva.this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean success = sharedPreferences.edit().remove(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                cotacao.getEvento().getId())).commit();
        if (!success) {
            Log.e("Erro compracombinada", "Erro ao deletar");
        }

        if (!fezMerge) {

            Gson gson = new Gson();
            jsonProdutos = gson.toJson(listProdutos);

            boolean successInsert = sharedPreferences.edit().putString(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                    cotacao.getEvento().getId()), jsonProdutos).commit();
            if (!successInsert) {
                Log.e("Erro compracombinada", "Erro ao inserir");
            }

    }

        super.onDestroy();
    }


    @Override
    public void onPause() {

        SharedPreferences settings = ListaDetalheCompraColetiva.this.getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean success = settings.edit().remove(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                cotacao.getEvento().getId())).commit();
        if(!success){
            Log.e("Erro compracombinada","Erro ao deletar");
        }

        if(!fezMerge) {

            Gson gson = new Gson();
            jsonProdutos = gson.toJson(listProdutos);

            boolean successInsert = settings.edit().putString(String.valueOf(UsuarioSingleton.getInstance().getUsuario().getId() + ";" +
                    cotacao.getEvento().getId()), jsonProdutos).commit();
            if (!successInsert) {
                Log.e("Erro compracombinada", "Erro ao inserir");
            }
        }

        super.onPause();
    }


}
