package br.com.enterprise.mobileparking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class TempoPermanencia {

    @Id
    private Integer tempo;
    private BigDecimal valor;

    public TempoPermanencia() {
    }

    public TempoPermanencia(Integer tempo, BigDecimal valor) {
        this.tempo = tempo;
        this.valor = valor;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
