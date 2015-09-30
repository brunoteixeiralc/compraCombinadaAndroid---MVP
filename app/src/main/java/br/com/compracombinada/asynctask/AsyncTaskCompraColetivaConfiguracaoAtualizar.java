package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.fragment.Configuracao;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaConfiguracaoAtualizar extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private Configuracao ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaConfiguracaoAtualizar(Configuracao ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Atualizando...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String servidor = null;

        try {

            servidor = compraCobinadaREST.getConfiguracaoCompraCombinadaAtualizar((br.com.compracombinada.model.Configuracao)params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return servidor;
    }

    @Override
    protected void onPostExecute(String servidor) {
        ctx.retornoAsyncTaskCompraCombinadaConfiguracaoAtualizar(servidor);
        progressDialog.dismiss();

    }

}
