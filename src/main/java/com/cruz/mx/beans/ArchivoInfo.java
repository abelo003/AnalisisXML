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
public class ArchivoInfo {
    
    private String nombre;
    private String rutaRelativa;
    private String rutaCompleta;

    public ArchivoInfo(String nombre, String rutaRelativa, String rutaCompleta) {
        this.nombre = nombre;
        this.rutaRelativa = rutaRelativa;
        this.rutaCompleta = rutaCompleta;
    }    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaCompleta() {
        return rutaCompleta;
    }

    public void setRutaCompleta(String rutaCompleta) {
        this.rutaCompleta = rutaCompleta;
    }

    public String getRutaRelativa() {
        return rutaRelativa;
    }

    public void setRutaRelativa(String rutaRelativa) {
        this.rutaRelativa = rutaRelativa;
    }
}
