package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class TempoPermanenciaInvalidoException extends RegraNegocioException {

    public TempoPermanenciaInvalidoException() {
        super(ResourceMessage.TEMPO_PERMANENCIA_INVALIDO);
    }
}
