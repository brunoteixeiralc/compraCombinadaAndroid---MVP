package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.fragment.ProdutosNaoContem;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaPesquisaProdutosNaoContem extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private ProdutosNaoContem ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaPesquisaProdutosNaoContem(ProdutosNaoContem ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Pesquisando os produtos em falta...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getEventosFinalizadosUsuario(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaPesquisaCotacao(jsonString);
        progressDialog.dismiss();

    }

}
