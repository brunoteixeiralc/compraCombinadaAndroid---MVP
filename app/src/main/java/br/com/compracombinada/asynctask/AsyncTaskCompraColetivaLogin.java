package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.activity.LoginActivity;
import br.com.compracombinada.rest.CompraCobinadaREST;
import br.com.compracombinada.utils.Utils;

public class AsyncTaskCompraColetivaLogin extends AsyncTask<String, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private LoginActivity ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaLogin(LoginActivity ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx, "Compra Combinada", "Carregando...", true);
    }

    @Override
    protected String doInBackground(String... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getUsuarioCompraCombinada(params[0], Utils.encrypt(params[1]));

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinada(jsonString);
        progressDialog.dismiss();

    }

}
