/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import model.Empresa;
import model.JPAController.DireccionJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.LocalidadJpaController;
import model.JPAController.ProvinciaJpaController;
import model.JPAController.ZonaJpaController;
import model.Localidad;
import model.Provincia;
import model.Zona;
import view.JframePrincipal;
import view.PanelEmpresa;

/**
 *
 * @author Ariel
 */
public class EmpresaController extends Controller {
    private PanelEmpresa vista;
    private EmpresaJpaController modelo;
    
    ArrayList<Empresa> empresas = null;
    boolean zSeleccionada = false;
    boolean pSeleccionada = false;
    boolean lSeleccionada = false;
    
    Zona zBuscada = null;
    Provincia pBuscada = null;
    Localidad lBuscada = null;
    
    private ZonaJpaController modeloZona;
    private DireccionJpaController modeloDireccion;
    private ProvinciaJpaController modeloProvincia;
    private LocalidadJpaController modeloLocalidad;
    
    public EmpresaController(PanelEmpresa vista, EmpresaJpaController modelo) {
        this.vista = vista;
        this.modelo = modelo;        
        
        //inhabilita campos
        //inhabilitarTodosLosCampos(false);
        
        llenarEmpresa();
        CompletarCampos();
        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        
        //Si se detecta cambio  estado en un componente JCOMBOBOX
        if (e.getStateChange() == ItemEvent.SELECTED) {
            //Si la zona seleccionada esta activa
            if (this.zSeleccionada) { 
                this.zBuscada = (Zona) vista.getJcb_zona_direccion().getSelectedItem();
                llenarJcomboboxProvincia(zBuscada);
                this.pBuscada = (Provincia) vista.getJcb_provincia_direccion().getSelectedItem();
                llenarJcomboboxLocalidad(pBuscada);                
            }
            //si la provincia esta activa muestra las localidades de esa provincia
            if (this.pSeleccionada) {
                this.zBuscada = (Zona) vista.getJcb_zona_direccion().getSelectedItem();
                this.pBuscada = (Provincia) vista.getJcb_provincia_direccion().getSelectedItem();
                llenarJcomboboxLocalidad(pBuscada);                
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }
    
    /**
     * llena el JcomboBox de Zona con objetos Zona de la base de datos
     */
    public void llenarJcomboboxZona(){
        modeloZona = new ZonaJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloZona.findZonaEntities());
        vista.getJcb_zona_direccion().setModel(mdl);
        this.zBuscada = (Zona)vista.getJcb_zona_direccion().getSelectedItem();
    }

    /**
     * llena el JcomboBox de Provincia con objetos Provincia de la base de datos en funcion a un objeto Zona
     * @param z Zona
     */
    public void llenarJcomboboxProvincia(Zona z){
        modeloProvincia = new ProvinciaJpaController(Conexion.getEmf());             
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloProvincia.buscarProvinciasPorZona(z));
        vista.getJcb_provincia_direccion().setModel(mdl);        
        this.pBuscada = (Provincia)vista.getJcb_provincia_direccion().getSelectedItem();
    }
    
    /**
     * llena el JcomboBox de Localidad con objetos Localidad de la base de datos en funcion a un objeto Provincia
     * @param p Provincia
     */
    public void llenarJcomboboxLocalidad(Provincia p){
        modeloLocalidad = new LocalidadJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloLocalidad.buscarLocalidadPorProvincia(p));
        vista.getJcb_localidad_direccion().setModel(mdl);
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
    
    public void inhabilitarTodosLosCampos(boolean estado){
        //inhabilita campos
        vista.habilitarCampo(estado, vista.getJtfID());
        vista.habilitarCampo(estado, vista.getJtfRazonSocial());
        vista.habilitarCampo(estado, vista.getJtf_Cuit());
        
        vista.habilitarCampo(estado, vista.getJtf_calle_direccion());
        vista.habilitarCampo(estado, vista.getJtf_numero_direccion());
        vista.habilitarCampo(estado, vista.getJtf_piso_direccion());
        vista.habilitarCampo(estado, vista.getJtf_departamento_direccion());
        
        vista.habilitarCombobox(estado, vista.getJcb_zona_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_provincia_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_localidad_direccion());
    }
    
    public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfRazonSocial());
        vista.limpiarCampo(vista.getJtf_Cuit());
        
        vista.limpiarCampo(vista.getJtf_calle_direccion());
        vista.limpiarCampo(vista.getJtf_numero_direccion());
        vista.limpiarCampo(vista.getJtf_piso_direccion());
        vista.limpiarCampo(vista.getJtf_departamento_direccion());
        
        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_zona_direccion());
        vista.limpiarCombobox(vista.getJcb_provincia_direccion());
        vista.limpiarCombobox(vista.getJcb_localidad_direccion());
    }
    
    public void inhabilitarTodosLosBotones(boolean estado){      
        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());
    }
    
    public void llenarEmpresa(){
        empresas = new ArrayList<Empresa>();
        
        for (Empresa emp : modelo.findEmpresaEntities()) {
            //Guarda en Lista de empleados  
            empresas.add(emp);
        }
    }
    
    public int sizeTabla(){
        int posicion =0;
        for (Empresa emp : modelo.findEmpresaEntities()) {            
            posicion++;
        }        
        return posicion-1;
    }
    
    public void CompletarCampos(){
        if (sizeTabla()>=0) {    
                //Si posee datos de direccion se cargan en la vista
                
                //Posicionar el cursor de la lista en el primer Elemento
                vista.getJtfID().setText(empresas.get(0).getId().toString());                
                vista.getJtfRazonSocial().setText(empresas.get(0).getRazonSocial());
                vista.getJtf_Cuit().setText(empresas.get(0).getCuit());
                if (empresas.get(0).getId().toString().equals(vista.getJtfID().getText())) {
                    if (empresas.get(0).getDireccion()!=null) {
                        if (empresas.get(0).getDireccion().getLocalidad() !=null) {


                            vista.getJcb_zona_direccion().removeAllItems();
                            vista.getJcb_zona_direccion().addItem(empresas.get(0).getDireccion().getLocalidad().getProvincia().getZona().getNombre());
                            
                            vista.getJcb_provincia_direccion().removeAllItems();
                            vista.getJcb_provincia_direccion().addItem(empresas.get(0).getDireccion().getLocalidad().getProvincia().getNombre());
                            
                            vista.getJcb_localidad_direccion().removeAllItems();
                            vista.getJcb_localidad_direccion().addItem(empresas.get(0).getDireccion().getLocalidad().getNombre());
                            
                            vista.getJtf_calle_direccion().setText(empresas.get(0).getDireccion().getCalle());
                            vista.getJtf_numero_direccion().setText(empresas.get(0).getDireccion().getNumero());
                            vista.getJtf_piso_direccion().setText(empresas.get(0).getDireccion().getPiso());
                            vista.getJtf_departamento_direccion().setText(empresas.get(0).getDireccion().getDepartamento());
                        }else{
                            System.out.println("No tiene Localidad");
                        }

                    }else{
                        System.out.println("no tiene direccion");
                    }                    
                }
                
            }else{
                
                
                limpiarTodosLosCampos();
                JOptionPane.showMessageDialog(null, "No hay Empresa que listar");
            }
    
    }
}
