package br.com.compracombinada.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class EventoConvidado implements Serializable {

    private Integer id;

    private Evento evento;

    private Usuario usuario;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
