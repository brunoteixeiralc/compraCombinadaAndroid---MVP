package br.com.compracombinada.rest;


public class CompraCobinadaREST {

	public String getUsuarioCompraCombinada(String login,String senha) throws Exception {
		
		 String URL_WS = "http://compracombinada-env.elasticbeanstalk.com/REST/compracombinada/usuario/";
	     String[] resposta = new WebServiceCompraCombinada().get(URL_WS + login + "/" + senha);
	     
	     if (resposta[0].equals("200")) {
	    	 return resposta[1];
	         
	     } else {
	         throw new Exception(resposta[1]);
	     }
	    }
}
