package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.R;
import br.com.compracombinada.fragment.AdicionarProduto;
import br.com.compracombinada.model.Produto;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaAddProduto extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private AdicionarProduto ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaAddProduto(AdicionarProduto ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), ctx.getResources().getString(R.string.app_name), "Adicionando Produto...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getAddProdutoCompraCombinada((Produto) params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaAddProduto(jsonString);
        progressDialog.dismiss();

    }

}
