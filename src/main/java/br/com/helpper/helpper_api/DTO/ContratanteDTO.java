package br.com.helpper.helpper_api.DTO;

import br.com.helpper.helpper_api.ENTITY.Contratante;

public class ContratanteDTO {
    private String endereco;

    public ContratanteDTO() {

    }

    public ContratanteDTO(Contratante contratante) {
        this.endereco = contratante.getEndereco();
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
