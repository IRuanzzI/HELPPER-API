package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Prestador;

import java.util.List;

public class PrestadorDTO {
    private Double avalicao;
    private String cidade;
    private List<String> servicos;
    private String bioProfissional;

    public PrestadorDTO() {

    }

    public PrestadorDTO(Prestador prestador) {
        this.avalicao = prestador.getAvaliacao();
        this.cidade = prestador.getCidade();
        this.servicos = prestador.getServicos();
        this.bioProfissional = prestador.getBioProfissional();
    }

}
