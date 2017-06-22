/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.business;

import com.cruz.mx.beans.Retencion;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author acruzb
 */
public class AbstractRetenciones extends AbstractTableModel{
    
    private final String[] columnNames = { "Importe", "Impuesto"};
    private ArrayList<Retencion> registro;

    public AbstractRetenciones() {
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
        Retencion staff = registro.get(row);
        switch (col){
            case 0:
                return staff.getImporte();
            case 1:
                return staff.getImpuesto();
        }
        return "";
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    public void addData(Retencion data){
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
