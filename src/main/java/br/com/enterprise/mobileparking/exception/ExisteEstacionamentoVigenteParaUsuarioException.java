package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class ExisteEstacionamentoVigenteParaUsuarioException extends RegraNegocioException {

    public ExisteEstacionamentoVigenteParaUsuarioException() {
        super(ResourceMessage.EXISTE_ESTACIONAMENTO_VIGENTE);
    }
}
