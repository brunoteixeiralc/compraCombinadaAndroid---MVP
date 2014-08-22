package br.com.compracombinada.asynctask;

import android.os.AsyncTask;

import br.com.compracombinada.LoginActivity;
import br.com.compracombinada.rest.CompraCobinadaREST;

public class AsyncTaskCompraColetiva extends AsyncTask<Void, Void, String> {
	
	private CompraCobinadaREST compraCobinadaREST = new CompraCobinadaREST();
	private LoginActivity ctx;
	
	public AsyncTaskCompraColetiva(LoginActivity ctx) {
		this.ctx = ctx;
	}
	
	 @Override
     protected String doInBackground(Void... params) {

		 String jsonString = null;

        try {
        	
        	jsonString = compraCobinadaREST.getUsuarioCompraCombinada("bruno","12345678");
		
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
