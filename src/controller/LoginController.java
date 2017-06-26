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
public class LoginController implements ActionListener {

    private Login vista;
    private UsuarioJpaController modelo;

    //Patron Singleton
    private static Usuario usuarioRegistradoInstanciaUnica;

    public LoginController(Login vista, UsuarioJpaController modelo) {
        this.vista = vista;
        this.modelo = modelo;

        //Instacia Unica Singleton
        createInstance();
    }

    public LoginController() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getIniciarSesion()) {
            usuarioRegistradoInstanciaUnica.setNombre(vista.getUsuario().getText());
            usuarioRegistradoInstanciaUnica.setClave(vista.getClave().getText());
            if (modelo.iniciarSesion(usuarioRegistradoInstanciaUnica) != null) {
                usuarioRegistradoInstanciaUnica = modelo.iniciarSesion(usuarioRegistradoInstanciaUnica);
                Bienvenida inicio = new Bienvenida(usuarioRegistradoInstanciaUnica.getTipoUsuario().getId().toString());
                inicio.setVisible(true);
                vista.dispose();

            } else {
                vista.setMensaje("ERROR: usuario o Password incorrecto");
                vista.limpiar();
            }
        }
    }

    //Aplicacion patron Songleton
    private synchronized static void createInstance() {
        if (usuarioRegistradoInstanciaUnica == null) {
            usuarioRegistradoInstanciaUnica = new Usuario();
        }
    }

    public static Usuario getInstance() {
        createInstance();
        return usuarioRegistradoInstanciaUnica;
    }

}
