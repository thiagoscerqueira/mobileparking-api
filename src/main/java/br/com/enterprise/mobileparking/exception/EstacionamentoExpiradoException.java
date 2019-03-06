package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class EstacionamentoExpiradoException extends RegraNegocioException {

    public EstacionamentoExpiradoException() {
        super(ResourceMessage.EXPIRADO);
    }
}
