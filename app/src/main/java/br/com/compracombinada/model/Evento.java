package br.com.compracombinada.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class Evento implements Serializable {

    private Integer id;

    private String nome;

    private String dataHora;

    private List<Local> locais;

    private List<Lista> listas;

    private List<Usuario> usuarioConvidados;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public List<Local> getLocais() {
        return locais;
    }

    public void setLocais(List<Local> locais) {
        this.locais = locais;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public List<Usuario> getUsuarioConvidados() {
        return usuarioConvidados;
    }

    public void setUsuarioConvidados(List<Usuario> usuarioConvidados) {
        this.usuarioConvidados = usuarioConvidados;
    }
}
