package br.com.compracombinada.model;

import java.io.Serializable;

/**
 * Created by bruno on 20/08/14.
 */
public class ValidarCotacao implements Serializable {

    private Integer id;

    private Usuario usuario;

    private Evento evento;

    private Lista listaCotacao;

    private Local localEscolhido;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }


    public Lista getListaCotacao() {
        return listaCotacao;
    }

    public void setListaCotacao(Lista listaCotacao) {
        this.listaCotacao = listaCotacao;
    }

    public Local getLocalEscolhido() {
        return localEscolhido;
    }

    public void setLocalEscolhido(Local localEscolhido) {
        this.localEscolhido = localEscolhido;
    }
}
