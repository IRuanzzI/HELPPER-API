package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PRESTADOR")
public class Prestador extends Usuario{

    private Double avaliacao;

    private Integer CPF;

    private String cidade;

    @ElementCollection
    private List<String> servicos = new ArrayList<>();

    private String bioProfissional;

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getCPF() {
        return CPF;
    }

    public void setCPF(Integer CPF) {
        this.CPF = CPF;
    }

    public List<String> getServicos() {
        return servicos;
    }

    public void setServicos(List<String> servicos) {
        this.servicos = servicos;
    }

    public String getBioProfissional() {
        return bioProfissional;
    }

    public void setBioProfissional(String bioProfissional) {
        this.bioProfissional = bioProfissional;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
