package br.com.compracombinada.model;

import java.io.Serializable;

/**
 * Created by bruno on 20/08/14.
 */
public class Produtos implements Serializable {

    private Integer id;

    private Produto produto;

    private int quantidade;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
