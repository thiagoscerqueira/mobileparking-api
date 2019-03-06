package br.com.enterprise.mobileparking.exception;

import br.com.enterprise.mobileparking.enums.ResourceMessage;

public class UsuarioNaoPossuiSaldoSuficienteException extends RegraNegocioException {

    public UsuarioNaoPossuiSaldoSuficienteException() {
        super(ResourceMessage.SALDO_INSUFICIENTE);
    }
}
