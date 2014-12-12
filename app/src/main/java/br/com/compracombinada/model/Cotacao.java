package br.com.compracombinada.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class Cotacao implements Serializable {

    private Integer id;

    private Usuario usuario;

    private Evento evento;

    private Lista listaCotacao;


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
}
