package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.model.Produtos;
import br.com.compracombinada.rest.CompraCobinadaREST;
import br.com.compracombinada.dialog.DialogFragmentGramas;

public class AsyncTaskCompraColetivaGramas extends AsyncTask<Object, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private DialogFragmentGramas ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaGramas(DialogFragmentGramas ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Atualizando a quantidade de gramas...", true);
    }

    @Override
    protected String doInBackground(Object... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getUpdateProdutoCotacao((Produtos)params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaNovaMedida(jsonString);
        progressDialog.dismiss();

    }

}
