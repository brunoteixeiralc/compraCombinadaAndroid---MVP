package br.com.compracombinada.model;

import java.io.Serializable;

/**
 * Created by bruno on 20/08/14.
 */
public class RegisterID  implements Serializable {

    private Integer id;

    private String register;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
