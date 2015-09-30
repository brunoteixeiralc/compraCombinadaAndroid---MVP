package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.rest.CompraCobinadaREST;
import br.com.compracombinada.dialog.DialogFragmentCotacao;

public class AsyncTaskCompraColetivaPreferencias extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private DialogFragmentCotacao ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaPreferencias(DialogFragmentCotacao ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Buscando as preferências...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getPreferenciasProduto(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaPrefvivierencias(jsonString);
        progressDialog.dismiss();

    }

}
