/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.beans;

/**
 *
 * @author acruzb
 */
public class Concepto {
    
    private double importe;
    private double valorUnitario;
    private String descripcion;
    private String noIdentificacion;
    private String unidad;
    private double cantidad;

    public Concepto(double importe, double valorUnitario, String descripcion, String noIdentificacion, String unidad, double cantidad) {
        this.importe = importe;
        this.valorUnitario = valorUnitario;
        this.descripcion = descripcion;
        this.noIdentificacion = noIdentificacion;
        this.unidad = unidad;
        this.cantidad = cantidad;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNoIdentificacion() {
        return noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
            
}
