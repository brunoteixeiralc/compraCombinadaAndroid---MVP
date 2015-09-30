package br.com.compracombinada.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.rest.CompraCobinadaREST;

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
