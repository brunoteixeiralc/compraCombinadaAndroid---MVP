package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.Cotacoes;
import br.com.compracombinada.EventosConvidados;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaEventosConvidados extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private EventosConvidados ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaEventosConvidados(EventosConvidados ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Pesquisando os eventos em que foi convidado...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getEventosConvidados(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaEventosConvidados(jsonString);
        progressDialog.dismiss();

    }

}
