package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.fragment.FinalizarEvento;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaFinalizarEventos extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private FinalizarEvento ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaFinalizarEventos(FinalizarEvento ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Pesquisando seus eventos para serem finalizados...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getEventosUsuario(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaEventos(jsonString);
        progressDialog.dismiss();

    }

}
