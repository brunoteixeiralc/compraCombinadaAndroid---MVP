package br.com.compracombinada.model;

import java.io.Serializable;

/**
 * Created by bruno on 20/08/14.
 */
public class Familia implements Serializable {

    private Integer id;

    private String nome;

    private String descricao;

    private Grupo grupo;


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
}
