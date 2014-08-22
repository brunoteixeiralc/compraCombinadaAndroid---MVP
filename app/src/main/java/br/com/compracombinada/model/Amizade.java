package br.com.compracombinada.model;

/**
 * Created by bruno on 20/08/14.
 */
public class Amizade {

    private Integer id;

    private Usuario usuarioConvidado;

    private boolean statusAceite;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuarioConvidado() {
        return usuarioConvidado;
    }

    public void setUsuarioConvidado(Usuario usuarioConvidado) {
        this.usuarioConvidado = usuarioConvidado;
    }

    public boolean isStatusAceite() {
        return statusAceite;
    }

    public void setStatusAceite(boolean statusAceite) {
        this.statusAceite = statusAceite;
    }
}
