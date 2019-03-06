package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class RegraNegocioException extends Exception {
    private ResourceMessage mensagem;

    public RegraNegocioException(ResourceMessage mensagem) {
        super(mensagem.getMensagem());
        this.mensagem = mensagem;
    }

    public ResourceMessage getMensagem() {
        return mensagem;
    }
}
