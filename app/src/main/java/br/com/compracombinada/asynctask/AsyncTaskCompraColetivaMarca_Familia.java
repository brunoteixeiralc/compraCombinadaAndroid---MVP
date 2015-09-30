package br.com.compracombinada.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.compracombinada.fragment.AdicionarProduto;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetivaMarca_Familia extends AsyncTask<Void, Void, List<String>> {

    private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
    private AdicionarProduto ctx;
    private ProgressDialog progressDialog;

    public AsyncTaskCompraColetivaMarca_Familia(AdicionarProduto ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(ctx.getActivity(), "Compra Combinada", "Buscando dados...", true);
    }

    @Override
    protected List<String> doInBackground(Void... params) {

       // String jsonStringMarca = null;
        String jsonStringFamilia = null;
        String jsonStringDivisao = null;

        List<String> jsons = new ArrayList<String>();

        try {

          //  jsonStringMarca = compraCobinadaREST.getMarcasCompraCombinda();
            jsonStringFamilia = compraCobinadaREST.getFamiliasCompraCombinda();
            jsonStringDivisao = compraCobinadaREST.getDivisaoCompraCombinda();

          //  jsons.add(jsonStringMarca);
            jsons.add(jsonStringFamilia);
            jsons.add(jsonStringDivisao);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return jsons;
    }

    @Override
    protected void onPostExecute(List<String> jsons) {
        ctx.retornoAsyncTaskCompraCombinadaMarcas_Familia(jsons);
        progressDialog.dismiss();

    }

}
