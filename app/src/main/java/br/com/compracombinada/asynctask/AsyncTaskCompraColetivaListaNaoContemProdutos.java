package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import br.com.compracombinada.FinalizarEventoBtn;
import br.com.compracombinada.ProdutosNaoContemListaDetalhe;
import br.com.compracombinada.model.Evento;
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
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Carregando os produtos em falta...", true);
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
