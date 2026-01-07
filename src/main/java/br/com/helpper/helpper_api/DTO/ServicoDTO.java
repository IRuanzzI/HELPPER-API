package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Contratante;
import br.com.helpper.helpper_api.ENTITY.Servico;

import java.util.Date;

public class ServicoDTO {
    private  long id;
    private String orcamento;
    private String tipo;
    private Date data;
    private String descricao;
    private Contratante contratante;
    private String enderecoDoContratante;

    public ServicoDTO() {

    }

    public ServicoDTO(Servico servico) {
        this.id =  servico.getId();
        this.orcamento = servico.getorcamento();
        this.tipo = servico.getTipo();
        this.data = servico.getData();
        this.descricao = servico.getDescricao();
        this.enderecoDoContratante = servico.getEnderecoDoContratante();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(String orcamento) {
        this.orcamento = orcamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Contratante getContratante() {
        return contratante;
    }

    public void setContratante(Contratante contratante) {
        this.contratante = contratante;
    }

    public String getEnderecoDoContratante() {
        return enderecoDoContratante;
    }

    public void setEnderecoDoContratante(String enderecoDoContratante) {
        this.enderecoDoContratante = enderecoDoContratante;
    }
}

