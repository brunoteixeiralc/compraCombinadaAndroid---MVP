package br.com.compracombinada.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by bruno on 20/08/14.
 */
public class Solicitacoes implements Serializable {

    private Integer id;

    private String nomeApp;

    private String sugestoes;

    private Usuario usuario;

    private Timestamp interacao;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSugestoes() {
        return sugestoes;
    }

    public void setSugestoes(String sugestoes) {
        this.sugestoes = sugestoes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeApp() {
        return nomeApp;
    }

    public void setNomeApp(String nomeApp) {
        this.nomeApp = nomeApp;
    }

    public Timestamp getInteracao() {
        return interacao;
    }

    public void setInteracao(Timestamp interacao) {
        this.interacao = interacao;
    }
}
