package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CONTRATANTE")
public class Contratante extends Usuario {
    private String cpf;
    private String endereco;
}
