package br.com.compracombinada.asynctask;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.compracombinada.AdicionarProduto;
import br.com.compracombinada.BuscarProdutos;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.rest.CompraCobinadaREST;
import br.com.compracombinada.util.DialogFragmentBuscaProduto;

public class AsyncTaskCompraColetivaAddListaProdutoCotacao extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private Context ctx;
    //private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaAddListaProdutoCotacao(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
       // progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Adicionando o produto na lista...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getAddListaProdutoCotacao((Produtos) params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        //ctx.retornoAsyncTaskCompraCombinadaAddProduto(jsonString);
       // progressDialog.dismiss();

    }

}
