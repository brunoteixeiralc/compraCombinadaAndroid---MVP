package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.fragment.Cotacoes;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaPesquisaCotacoes extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private Cotacoes ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaPesquisaCotacoes(Cotacoes ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Pesquisando suas cotações...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getCotacoesUsuario(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaPesquisaCotacao(jsonString);
        progressDialog.dismiss();

    }

}
