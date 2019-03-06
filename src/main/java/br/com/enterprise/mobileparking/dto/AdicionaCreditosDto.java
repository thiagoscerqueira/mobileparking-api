package br.com.enterprise.mobileparking.dto;

import java.math.BigDecimal;

public class AdicionaCreditosDto {

    private String usuario;
    private BigDecimal valor;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
