package br.com.enterprise.mobileparking.dto;

import java.math.BigDecimal;

public class ConsultaCreditoDto {

    private String usuario;
    private BigDecimal saldo;

    public ConsultaCreditoDto() {
    }

    public ConsultaCreditoDto(String usuario, BigDecimal saldo) {
        this.usuario = usuario;
        this.saldo = saldo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
