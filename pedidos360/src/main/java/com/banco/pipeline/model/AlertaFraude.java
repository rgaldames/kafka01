package com.banco.pipeline.model;

public class AlertaFraude {
    private String usuario;
    private Double monto;
    private String motivo;
    private String severidad;

    public AlertaFraude() {}

    public AlertaFraude(String usuario, Double monto, String motivo, String severidad) {
        this.usuario = usuario;
        this.monto = monto;
        this.motivo = motivo;
        this.severidad = severidad;
    }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
}
