package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;

import br.com.compracombinada.R;
import br.com.compracombinada.fragment.ListaDetalheCompraColetiva;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaAddCotacao extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private ListaDetalheCompraColetiva ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaAddCotacao(ListaDetalheCompraColetiva ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), ctx.getResources().getString(R.string.app_name), "Adicionando Cotação...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.addCotacaoCompraCombinada((Cotacao) params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaCotacao(jsonString);
        progressDialog.dismiss();

    }

}
