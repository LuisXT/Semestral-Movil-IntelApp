package com.example.proyectofinal.Entities;

public class Resultado {

    Double puntajeVisual,puntajeAuditivo,puntajeKine;
    String tipoInteligencia;

    public Resultado(Double puntajeVisual, Double puntajeAuditivo, Double puntajeKine, String tipoInteligencia) {
        this.puntajeVisual = puntajeVisual;
        this.puntajeAuditivo = puntajeAuditivo;
        this.puntajeKine = puntajeKine;
        this.tipoInteligencia = tipoInteligencia;
    }


    public Double getPuntajeVisual() {
        return puntajeVisual;
    }

    public void setPuntajeVisual(Double puntajeVisual) {
        this.puntajeVisual = puntajeVisual;
    }

    public Double getPuntajeAuditivo() {
        return puntajeAuditivo;
    }

    public void setPuntajeAuditivo(Double puntajeAuditivo) {
        this.puntajeAuditivo = puntajeAuditivo;
    }

    public Double getPuntajeKine() {
        return puntajeKine;
    }

    public void setPuntajeKine(Double puntajeKine) {
        this.puntajeKine = puntajeKine;
    }

    public String getTipoInteligencia() {
        return tipoInteligencia;
    }

    public void setTipoInteligencia(String tipoInteligencia) {
        this.tipoInteligencia = tipoInteligencia;
    }
}
