/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.JPAController.Conexion;
import model.JPAController.UsuarioJpaController;
import view.*;

/**
 *
 * @author Ariel
 */
public class Main {

    public static void main(String[] args) {

        //Se Crea Login Vista .
        Login vista = new Login();

        //Se Crea Modelo UsuarioJPAController            
        UsuarioJpaController modelo = new UsuarioJpaController(Conexion.getEmf());

        //Se crea el controlador de LoginController
        LoginController controlador = new LoginController(vista, modelo);

        //setea el panel para que sea escuchado por el controlador
        vista.setControlador(controlador);

        vista.arranca();

    }
}
