package br.com.helpper.helpper_api.ENTITY;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String orcamento;

    @Column
    private String tipo;

    @Column
    private Date  data;

    @Column
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "contratante_id")
    private Contratante contratante;

  

    public Servico(){
    }
    
    //AQUI HERDA O ENDEREÇO DO USUARIO
    
    public String getEnderecoDoContratante() {
        return contratante != null ? contratante.getEndereco() : null;
    }
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getorcamento() {
        return orcamento;
    }

    public void setorcamento(String orcamento) {
        orcamento = orcamento;
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

}


// tipo, ID, Prazo, Status, Descrição