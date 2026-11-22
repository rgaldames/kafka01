package com.banco.pipeline.model;

public class AlertaStock {
    private String producto;
    private Integer cantidad;
    private String origen;
    private String motivo;
    private String severidad;

    public AlertaStock() {}

    public AlertaStock(String producto, Integer cantidad, String origen, String motivo, String severidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.origen = origen;
        this.motivo = motivo;
        this.severidad = severidad;
    }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
}
