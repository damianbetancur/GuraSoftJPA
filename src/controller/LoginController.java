/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.JPAController.UsuarioJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Usuario;
import view.Login;


/**
 *
 * @author Ariel
 */
public class LoginController implements ActionListener{

    private Login vista;
    private UsuarioJpaController modelo;

    public LoginController(Login vista, UsuarioJpaController modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
    }
    
   

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        Usuario u = new Usuario();
        modelo.create(u);
        if (e.getSource()==vista.getIniciarSesion()) {
                        
            System.out.println(vista.getUsuario().getText());
            Bienvenida inicio = new Bienvenida();
            inicio.setVisible(true);
            vista.dispose();
        }
        

        
    }
    
}
