/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.business;

import com.cruz.mx.beans.Concepto;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author acruzb
 */
public class AbstractConcepto extends AbstractTableModel{
    
    private final String[] columnNames = { "Importe", "Valor unitario", "Descripción", "Número identificación", "Unidad", "Cantidad"};
    private ArrayList<Concepto> registro;

    public AbstractConcepto() {
        registro = new ArrayList<>();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return registro.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Concepto staff = registro.get(row);
        switch (col){
            case 0:
                return staff.getImporte();
            case 1:
                return staff.getValorUnitario();
            case 2:
                return staff.getDescripcion();
            case 3:
                return staff.getNoIdentificacion();
            case 4:
                return staff.getUnidad();
            case 5:
                return staff.getCantidad();
        }
        return "";
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public void addData(Concepto data){
        registro.add(data);
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    public void emptyData(){
        this.registro = new ArrayList<>();
    }
}
