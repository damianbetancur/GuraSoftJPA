/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import model.JPAController.EmpresaJpaController;
import view.JframePrincipal;
import view.PanelEmpresa;

/**
 *
 * @author Ariel
 */
public class EmpresaController implements ActionListener, KeyListener, MouseListener{
    
    private PanelEmpresa vista;
    private EmpresaJpaController modelo;
    boolean bloquearAceptar = true;

    public EmpresaController(PanelEmpresa vista, EmpresaJpaController modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        
        //Inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Aceptar());
        vista.habilitarBoton(false, vista.getJbtn_Cancelar());

        
    }

    
    /**
     * actionPerformed
     * Controla los eventos que suceden en la vista
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        //Boton Cancelar
        if (e.getSource()==vista.getJbtn_Cancelar()){
            btn_cancelar();            
        }
        
        //Boton Volver
        if (e.getSource()==vista.getJbtn_Volver()){
            btn_volver();
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    public void btn_volver(){
        //Limpia campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);


        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);
    }
    
    /**
     * 
     * @param estado 
     */
    public void inhabilitarTodosLosCampos(boolean estado){
        //inhabilita campos
        vista.habilitarCampo(estado, vista.getJtfID());
        vista.habilitarCampo(estado, vista.getJtfNombre());
        vista.habilitarCampo(estado, vista.getJtfDNI());
        vista.habilitarCampo(estado, vista.getJtfDireccion());
    }
    
    /**
     * LimpiarTodosLosCampos
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfNombre());
        vista.limpiarCampo(vista.getJtfDNI());
        vista.limpiarCampo(vista.getJtfDireccion());
    }
    
    /**
     * 
     * @param estado 
     */
    public void inhabilitarTodosLosBotones(boolean estado){
        //Inhabilita Botones Aceptar-Cancelar
        vista.habilitarBoton(estado, vista.getJbtn_Aceptar());
        vista.habilitarBoton(estado, vista.getJbtn_Cancelar());

        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());
    }
    
    /**
     * 
     */
    public void politicaValidacionDeCampos(){
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtfNombre());
        vista.getValidador().LimitarCaracteres(vista.getJtfNombre(), 30);            

        vista.getValidador().LimitarCaracteres(vista.getJtfDireccion(), 50);

        vista.getValidador().validarSoloNumero(vista.getJtfDNI());
        vista.getValidador().LimitarCaracteres(vista.getJtfDNI(), 8);
    }
    
    public void btn_cancelar(){
        //Limpiar Campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Aceptar());
        vista.habilitarBoton(false, vista.getJbtn_Cancelar());
        //Habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Volver());
    }
}
