package br.com.enterprise.mobileparking.dto;

public class RenovaEstacionamentoDto {

    private String estacionamentoUsuarioId;
    private Integer tempoPermanencia;

    public String getEstacionamentoUsuarioId() {
        return estacionamentoUsuarioId;
    }

    public void setEstacionamentoUsuarioId(String estacionamentoUsuarioId) {
        this.estacionamentoUsuarioId = estacionamentoUsuarioId;
    }

    public Integer getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(Integer tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }
}
