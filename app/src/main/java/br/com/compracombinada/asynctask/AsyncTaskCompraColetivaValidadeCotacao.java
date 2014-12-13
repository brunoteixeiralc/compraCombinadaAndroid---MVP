package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.FinalizarEventoBtn;
import br.com.compracombinada.ListaDetalheCompraColetiva;
import br.com.compracombinada.model.Cotacao;
import br.com.compracombinada.model.Evento;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaValidadeCotacao extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private FinalizarEventoBtn ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaValidadeCotacao(FinalizarEventoBtn ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Validando cotação...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.validaCotacao((Evento) params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaValidaCotacao(jsonString);
        progressDialog.dismiss();

    }

}