/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador.Dao;

import Modelo.Auto;

/**
 *
 * @author Usuario
 */
public class EstudianteDao extends AdaptadorDao<Auto> {
    private Auto estudiante;

    public EstudianteDao() {
        super(Auto.class);
    }

    public Auto getEstudiante() {
        if(estudiante==null)
            estudiante = new Auto();
        return estudiante;
    }

    public void setEstudiante(Auto estudiante) {
        this.estudiante = estudiante;
    }
    
    public boolean guardar(){
        return this.guardar(estudiante);
    }
}
