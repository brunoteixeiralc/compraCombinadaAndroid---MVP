package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.R;
import br.com.compracombinada.fragment.ProdutosNaoContemListaDetalhe;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaListaNaoContemProdutos extends AsyncTask<Integer, Void, String> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private ProdutosNaoContemListaDetalhe ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaListaNaoContemProdutos(ProdutosNaoContemListaDetalhe ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), ctx.getResources().getString(R.string.app_name), "Carregando os produtos em falta...", true);
    }

    @Override
    protected String doInBackground(Integer... params) {

        String jsonString = null;

        try {

            jsonString = compraCobinadaREST.getProdutosEmFalta(params[0]);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String jsonString) {
        ctx.retornoAsyncTaskCompraCombinadaProdutosEmFalta(jsonString);
        progressDialog.dismiss();

    }

}
