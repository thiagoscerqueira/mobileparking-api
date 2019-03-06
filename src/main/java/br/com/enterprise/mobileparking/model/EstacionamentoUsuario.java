package br.com.enterprise.mobileparking.model;

import br.com.enterprise.mobileparking.exception.ExisteEstacionamentoVigenteParaUsuarioException;
import br.com.enterprise.mobileparking.exception.UsuarioNaoPossuiSaldoSuficienteException;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@DynamicUpdate
public class EstacionamentoUsuario {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private TempoPermanencia tempoPermanencia;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private String placaVeiculo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TempoPermanencia getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(TempoPermanencia tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Duration calculaTempoRestante() {
        LocalDateTime dataHoraFim = calculaDataHoraFim();
        return Duration.between(LocalDateTime.now(), dataHoraFim);
    }

    private LocalDateTime calculaDataHoraFim() {
        return this.getDataHoraInicio().plusMinutes(this.getTempoPermanencia().getTempo());
    }

    public void renova(TempoPermanencia novaTempoPermanencia) throws UsuarioNaoPossuiSaldoSuficienteException {
        BigDecimal valorSaldoDecrementar = novaTempoPermanencia.getValor().subtract(this.tempoPermanencia.getValor());
        this.getUsuario().decrementaSaldo(valorSaldoDecrementar);
        this.tempoPermanencia = novaTempoPermanencia;
    }

    public void finaliza() throws ExisteEstacionamentoVigenteParaUsuarioException {
        if (!isExpirado()) {
            throw new ExisteEstacionamentoVigenteParaUsuarioException();
        }
        this.setDataHoraFim(calculaDataHoraFim());
    }

    public Boolean isExpirado() {
        return this.getDataHoraFim() == null && this.calculaDataHoraFim().compareTo(LocalDateTime.now()) < 0;
    }
}
