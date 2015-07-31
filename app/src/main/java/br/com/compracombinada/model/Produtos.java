package br.com.compracombinada.model;

import java.io.Serializable;

/**
 * Created by bruno on 20/08/14.
 */
public class Produtos implements Serializable {

    private Integer id;

    private Produto produto;

    private int quantidade;

    private String preco;

    private String usuarioNome;

    private boolean naoContem;

    private boolean deletou;

    private Produto produtoTempPref;


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

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }


    public boolean isNaoContem() {
        return naoContem;
    }

    public void setNaoContem(boolean naoContem) {
        this.naoContem = naoContem;
    }


    public boolean isDeletou() {
        return deletou;
    }

    public void setDeletou(boolean deletou) {
        this.deletou = deletou;
    }

    public Produto getProdutoTempPref() {
        return produtoTempPref;
    }

    public void setProdutoTempPref(Produto produtoTempPref) {
        this.produtoTempPref = produtoTempPref;
    }
}
