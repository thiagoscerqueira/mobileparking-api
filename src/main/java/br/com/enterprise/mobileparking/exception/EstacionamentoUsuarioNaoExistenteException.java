package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class EstacionamentoUsuarioNaoExistenteException extends RegraNegocioException {
    public EstacionamentoUsuarioNaoExistenteException() {
        super(ResourceMessage.ESTACIONAMENTO_NAO_EXISTENTE);
    }
}
