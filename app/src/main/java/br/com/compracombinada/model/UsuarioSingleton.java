package br.com.compracombinada.model;


public class UsuarioSingleton {

	    private static UsuarioSingleton mInstance = null;
	 
	    private Usuario usuario;
	    
	    private UsuarioSingleton(){
	        usuario = new Usuario();
	    }
	 
	    public static UsuarioSingleton getInstance(){
	        if(mInstance == null)
	        {
	            mInstance = new UsuarioSingleton();
	        }
	        return mInstance;
	    }
	 
		public Usuario getUsuario() {
			return this.usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

}
