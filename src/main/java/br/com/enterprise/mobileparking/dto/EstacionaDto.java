package br.com.enterprise.mobileparking.dto;

public class EstacionaDto {

    private String usuario;
    private String placaVeiculo;
    private Integer tempoPermanencia;

    public EstacionaDto() {
    }

    public EstacionaDto(String usuario, String placaVeiculo, Integer tempoPermanencia) {
        this.usuario = usuario;
        this.placaVeiculo = placaVeiculo;
        this.tempoPermanencia = tempoPermanencia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Integer getTempoPermanencia() {
        return tempoPermanencia;
    }

    public void setTempoPermanencia(Integer tempoPermanencia) {
        this.tempoPermanencia = tempoPermanencia;
    }
}
