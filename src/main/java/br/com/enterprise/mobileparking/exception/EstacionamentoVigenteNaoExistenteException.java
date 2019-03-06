package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class EstacionamentoVigenteNaoExistenteException extends RegraNegocioException {

    public EstacionamentoVigenteNaoExistenteException() {
        super(ResourceMessage.ESTACIONAMENTO_VIGENTE_NAO_EXISTENTE);
    }
}
