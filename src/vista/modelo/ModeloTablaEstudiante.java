/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista.modelo;

import Lista.controlador.Lista;
import Modelo.Auto;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Usuario
 */
public class ModeloTablaEstudiante extends AbstractTableModel{

    private Lista<Auto> lista = new Lista();

    public Lista<Auto> getLista() {
        return lista;
    }

    public void setLista(Lista<Auto> lista) {
        this.lista = lista;
    }
    
    @Override
    public int getRowCount() {
        return lista.tamanio();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        Auto es = lista.consultarDatoPosicion(i);
        switch(i1){
            case 0: return (i + 1);
            case 1: return es.getModelo();
            case 2: return es.getColor();
            case 3: return es.getPlaca();
            default: return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0: return "Nro";
            case 1: return "Modelo";
            case 2: return "Color";
            case 3: return "Placa";
            default: return null;
        }
    }
    
}
