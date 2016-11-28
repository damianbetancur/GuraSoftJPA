/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.JPAController.EmpleadoJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Empleado;
import view.JframePrincipal;
import view.PanelRegistroEmpleado;


/**
 *
 * @author Ariel
 */
public class EmpleadoController implements ActionListener{
    
    private PanelRegistroEmpleado vista;
    private EmpleadoJpaController modelo;

    public EmpleadoController(PanelRegistroEmpleado vista, EmpleadoJpaController modelo) {
        this.vista = vista;
        this.modelo = modelo;     
    }

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource()==vista.getJbtnNuevo()) {
            Empleado p = new Empleado();   
            System.out.println("hola");
            p.setNombre(vista.getJtfNombre().getText());


            //modelo = new EmpleadoJpaController(Conexion.getEmf());        
            modelo.create(p);
        }
        if (e.getSource()==vista.getJbtnBuscar()){
            System.out.println("Buscarrrrrrrr");
           
            JframePrincipal.modificarArbol(true);
            
            
        }
        
        
        
        
    }
    
    
    
}
