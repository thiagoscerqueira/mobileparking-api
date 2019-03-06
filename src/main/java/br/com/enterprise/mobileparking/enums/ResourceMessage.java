package br.com.enterprise.mobileparking.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceMessage {

    EXPIRADO(1, "Estacionamento expirado", HttpStatus.BAD_REQUEST),
    ESTACIONAMENTO_NAO_EXISTENTE(2, "Estacionamento Não Existente", HttpStatus.NOT_FOUND),
    EXISTE_ESTACIONAMENTO_VIGENTE(3, "Já existe estacionamento vigente para o usuário", HttpStatus.BAD_REQUEST),
    TEMPO_PERMANENCIA_INVALIDO(4, "Tempo de permanência informado não é válido", HttpStatus.BAD_REQUEST),
    USUARIO_NAO_EXISTENTE(5, "Usuário Não Existente", HttpStatus.NOT_FOUND),
    SALDO_INSUFICIENTE(6, "Saldo do usuário é insuficiente", HttpStatus.BAD_REQUEST),
    ESTACIONAMENTO_VIGENTE_NAO_EXISTENTE(7, "Estacionamento vigente não existente", HttpStatus.NOT_FOUND);


    private final int codigo;
    private final String mensagem;
    private final HttpStatus status;

    ResourceMessage(int codigo, String mensagem, HttpStatus status) {
        this.codigo = codigo;
        this.mensagem = mensagem;
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
