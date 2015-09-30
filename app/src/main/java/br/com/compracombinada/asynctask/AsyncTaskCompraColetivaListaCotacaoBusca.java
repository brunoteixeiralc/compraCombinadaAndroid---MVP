package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.rest.CompraCobinadaREST;
import br.com.compracombinada.dialog.DialogFragmentBuscaProduto;

public class AsyncTaskCompraColetivaListaCotacaoBusca extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private DialogFragmentBuscaProduto ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaListaCotacaoBusca(DialogFragmentBuscaProduto ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Adicionando o produto na lista..", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getListaCotacaoUsuario(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaListaCotacaoUsuario(jsonString);
        progressDialog.dismiss();

    }

}
