package br.com.compracombinada.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class Lista implements Serializable {

    private Integer id;

    private List<Produtos> produtos;

    private String nome;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
