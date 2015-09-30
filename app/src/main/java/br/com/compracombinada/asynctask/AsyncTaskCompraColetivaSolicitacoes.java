package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.fragment.Solicitacoes;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaSolicitacoes extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private Solicitacoes ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaSolicitacoes(Solicitacoes ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Enviando...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String msg = null;

        try {

            msg = compraCobinadaREST.getSolicitacoesCompraCombinada((br.com.compracombinada.model.Solicitacoes) params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        ctx.retornoAsyncTaskCompraCombinadaSolicitacoes(msg);
        progressDialog.dismiss();

    }

}
