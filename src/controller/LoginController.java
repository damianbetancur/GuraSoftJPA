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
        if (e.getSource()==vista.getIniciarSesion()) {
        Usuario u = new Usuario();
        u.setNombre(vista.getUsuario().getText());
        u.setClave(vista.getClave().getText());
            if (modelo.iniciarSesion(u)!=null) {
                u = modelo.iniciarSesion(u);
                Bienvenida inicio = new Bienvenida(u.getTipoUsuario().getId().toString());
                inicio.setVisible(true);
                vista.dispose();
                
                
            }else{
                vista.setMensaje("ERROR: usuario o Password incorrecto");
                vista.limpiar();            
            }
        }
    }
}
