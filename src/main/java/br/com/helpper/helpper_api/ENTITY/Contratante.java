package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONTRATANTE")
public class Contratante extends Usuario {



    private String endereco;

    public Contratante() {

    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
