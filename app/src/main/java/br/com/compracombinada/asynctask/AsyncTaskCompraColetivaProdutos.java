package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.R;
import br.com.compracombinada.fragment.BuscarProdutos;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaProdutos extends AsyncTask<Void, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private BuscarProdutos ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaProdutos(BuscarProdutos ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(),ctx.getResources().getString(R.string.app_name), "Buscando produtos...", true);
    }

    @Override
    protected String doInBackground(Void... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getProdutosCompraCombinda();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaProdutos(jsonString);
        progressDialog.dismiss();

    }

}
