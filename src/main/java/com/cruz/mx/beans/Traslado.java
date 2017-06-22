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
public class Traslado {
    
    private double importe;
    private double tasa;
    private String impuesto;

    public Traslado(double importe, double tasa, String impuesto) {
        this.importe = importe;
        this.tasa = tasa;
        this.impuesto = impuesto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }
           
}
