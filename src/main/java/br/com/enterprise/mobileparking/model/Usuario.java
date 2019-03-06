package br.com.enterprise.mobileparking.model;

import br.com.enterprise.mobileparking.exception.UsuarioNaoPossuiSaldoSuficienteException;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@DynamicUpdate
public class Usuario {

    @Id
    private String id;
    private String nome;
    private BigDecimal saldoAtualizado;

    public Usuario() {
    }

    public Usuario(String usuarioId) {
        this.id = usuarioId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldoAtualizado() {
        return saldoAtualizado;
    }

    public void setSaldoAtualizado(BigDecimal saldoAtualizado) {
        this.saldoAtualizado = saldoAtualizado;
    }

    public void incrementaSaldo(BigDecimal valor) {
        this.saldoAtualizado = this.saldoAtualizado.add(valor);
    }

    public void decrementaSaldo(BigDecimal valor) throws UsuarioNaoPossuiSaldoSuficienteException {
        BigDecimal saldoAtualizado = this.saldoAtualizado.subtract(valor);

        if (saldoAtualizado.compareTo(BigDecimal.ZERO) < 0) {
            throw new UsuarioNaoPossuiSaldoSuficienteException();
        }

        this.saldoAtualizado = saldoAtualizado;
    }

}
