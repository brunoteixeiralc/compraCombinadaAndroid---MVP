package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.R;
import br.com.compracombinada.fragment.AdicionarProduto;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaListaCotacaoAddProduto extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private AdicionarProduto ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaListaCotacaoAddProduto(AdicionarProduto ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(),ctx.getResources().getString(R.string.app_name), "Adicionando o produto na lista..", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getListaCotacaoUsuario(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaListaCotacaoUsuario(jsonString);
        progressDialog.dismiss();

    }

}
