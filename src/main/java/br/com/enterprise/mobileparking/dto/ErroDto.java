package br.com.enterprise.mobileparking.dto;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class ErroDto {
    private ResourceMessage erro;

    public ErroDto() {
    }

    public ErroDto(ResourceMessage erro) {
        this.erro = erro;
    }

    public ResourceMessage getErro() {
        return erro;
    }

    public void setErro(ResourceMessage erro) {
        this.erro = erro;
    }
}
