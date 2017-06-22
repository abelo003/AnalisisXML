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
public class Retencion {
    
    private double importe;
    private String impuesto;

    public Retencion(double importe, String impuesto) {
        this.importe = importe;
        this.impuesto = impuesto;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }
    
}
