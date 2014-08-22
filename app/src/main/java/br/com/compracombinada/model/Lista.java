package br.com.compracombinada.model;

import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class Lista {

    private Integer id;

    private List<Produtos> produtos;


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
}
