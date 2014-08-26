package br.com.compracombinada.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by bruno on 20/08/14.
 */
public class Usuario implements Serializable {

    private Integer id;

    private String nome;

    private String sexo;

    private String dataNascimento;

    private String endereco;

    private String login;

    private String senha;

    //private String foto;

    private List<Evento> eventos;

    private List<RegisterID> registerIDs;

    private List<Amizade> amizades;

    private List<Lista> listas;


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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<RegisterID> getRegisterIDs() {
        return registerIDs;
    }

    public void setRegisterIDs(List<RegisterID> registerIDs) {
        this.registerIDs = registerIDs;
    }

    public List<Amizade> getAmizades() {
        return amizades;
    }

    public void setAmizades(List<Amizade> amizades) {
        this.amizades = amizades;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }
}
