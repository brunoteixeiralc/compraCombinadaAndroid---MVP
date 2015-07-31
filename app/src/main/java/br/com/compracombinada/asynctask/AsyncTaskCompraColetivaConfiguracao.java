package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.LoginActivity;
import br.com.compracombinada.SplashScreen;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaConfiguracao extends AsyncTask<String, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private SplashScreen ctx;

    public AsyncTaskCompraColetivaConfiguracao(SplashScreen ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getConfiguracaoCompraCombinada();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinada(jsonString);

    }

}
