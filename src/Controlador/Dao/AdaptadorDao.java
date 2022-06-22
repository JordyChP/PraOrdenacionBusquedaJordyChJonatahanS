/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Lista.controlador.Lista;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Usuario
 */
public class AdaptadorDao <T> {
    private Class<T> clazz;
    private String carpeta = "datos" + File.separatorChar;
    private Lista<T> lista = new Lista();
    
    public AdaptadorDao(Class<T> clazz) {
        this.clazz = clazz;
        carpeta += this.clazz.getSimpleName() + ".datos";
        lista.setClazz(clazz);
    }
    
    public boolean guardar(Object dato) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(carpeta));
            Lista aux = listar();
            aux.insertarNodo(dato);
            oos.writeObject(aux);
            oos.close();
            return true;
        } catch (Exception e) {
            System.out.println("No se ha guardado" + e);
        }
        return false;
    }
    
    public Lista<T> listar() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(carpeta));
            lista = (Lista<T>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("error listar"+e);
        }
        return lista;
    }
}
