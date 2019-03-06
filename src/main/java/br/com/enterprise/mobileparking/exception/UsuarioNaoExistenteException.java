package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class UsuarioNaoExistenteException extends RegraNegocioException {

    public UsuarioNaoExistenteException() {
        super(ResourceMessage.USUARIO_NAO_EXISTENTE);
    }
}
