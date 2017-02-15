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
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Direccion;
import model.Empresa;
import model.JPAController.DireccionJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.LocalidadJpaController;
import model.JPAController.ProveedorJpaController;
import model.JPAController.ProvinciaJpaController;
import model.JPAController.ZonaJpaController;
import model.Localidad;
import model.Proveedor;
import model.Provincia;
import model.Zona;
import view.JframePrincipal;
import view.PanelRegistroProveedor;

/**
 *
 * @author Ariel
 */
public class ProveedorController extends Controller{
    private PanelRegistroProveedor vista;
    private ProveedorJpaController modelo;
    
    private ZonaJpaController modeloZona;
    private DireccionJpaController modeloDireccion;
    private ProvinciaJpaController modeloProvincia;
    private LocalidadJpaController modeloLocalidad;
    private EmpresaJpaController modeloEmpresa;
        
    boolean bloquearAceptarCrear = false;
    boolean bloquearAceptarEliminar = false;
    boolean bloquearAceptarModificar = false;
    
    Zona zBuscada = null;
    Provincia pBuscada = null;
    Localidad lBuscada = null;
    Empresa empresa = null;
        
    
    boolean zSeleccionada = false;
    boolean pSeleccionada = false;
    boolean lSeleccionada = false;    
    
    String cuitModificado = null;
    
    int ultimoIndiceSeleccionado = 0;
    List<Proveedor> proveedores;

    /**
     * Constructor Proveedor
     * @param vista PanelRegistroProveedor
     * @param modelo ProveedorJpaController
     */
    public ProveedorController(PanelRegistroProveedor vista, ProveedorJpaController modelo) {        
        modeloEmpresa = new EmpresaJpaController(Conexion.getEmf());
        empresa = modeloEmpresa.findEmpresa(1L);
        
        this.vista = vista;
        this.modelo = modelo;        
        
        //inhabilita campos
        inhabilitarTodosLosCampos(false);
        
        //Inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        vista.habilitarBoton(false, vista.getJbtn_Aceptar());
        vista.habilitarBoton(false, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());
    }
    
    /**
     * ActionPerformed
     * Controla los eventos que suceden en la vista al presionar los Botones de CRUD
     * @param e  recepcion de Evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        
        //Boton Agregar
        if (e.getSource()==vista.getJbtn_Agregar()) {
            btn_agregar();
        }
        
        //Boton Modificar
        if (e.getSource()==vista.getJbtn_Modificar()) {
            btn_modificar();
        }        
        
        //Boton Eliminar
        if (e.getSource()==vista.getJbtn_Eliminar()) {
            btn_eliminar();
        }
        
        //Boton Listar
        if (e.getSource()==vista.getJbtn_Listar()){            
            btn_listar();            
        }       
        
        //Boton Aceptar
        if (bloquearAceptarCrear && !bloquearAceptarEliminar && !bloquearAceptarModificar) {
            if (e.getSource()==vista.getJbtn_Aceptar()){
                btn_aceptarCrear();
            }
        }
        if (!bloquearAceptarCrear && !bloquearAceptarEliminar && bloquearAceptarModificar) {
            if (e.getSource()==vista.getJbtn_Aceptar()){
                btn_aceptarModificar();
            }
        }    
        if (!bloquearAceptarCrear && bloquearAceptarEliminar && !bloquearAceptarModificar) {
            if (e.getSource()==vista.getJbtn_Aceptar()){
                btn_aceptarEliminar();
            }
        }
        
        //Boton Cancelar
        if (e.getSource()==vista.getJbtn_Cancelar()){
            btn_cancelar();            
        }
        
        //Boton Volver
        if (e.getSource()==vista.getJbtn_Volver()){
            btn_volver();
        }
        
        
        
    }

    /**
     * Controla el Boton Agregar
     * Desbloquea el Boton AceptarCrear para poder crear 
     */
    public void btn_agregar(){
        //Posiciona la seleccion en el Panel datos Proveedor. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
        
        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());

        //Limpiar Campos
        limpiarTodosLosCampos();

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);
        vista.habilitarCampo(false, vista.getJtfID());

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        
        //posiciona en foco de la lista en el ultimo Proveedor creado                    
        vista.getTablaProveedores().changeSelection(sizeTabla(), 1, false, false);
        
        //inicializa el JcomboBox
        llenarJcomboboxZona();
        llenarJcomboboxProvincia(zBuscada);
        llenarJcomboboxLocalidad(pBuscada);             
        
        //desbloquea el boton nuevo
        bloquearAceptarCrear=true;      
        
        //Limpia la lista            
        vista.getTablaProveedores().setModel(new DefaultTableModel());
    }
    
    /**
     * Controla el Boton Modificar
     * Desbloquea el Boton AceptarModificar para poder modificar
     */
    public void btn_modificar(){
        
        //Posiciona la seleccion en el Panel datos proveedores. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);        
            
        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);
        vista.habilitarCampo(false, vista.getJtfID());

        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        
        //inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());        
        vista.habilitarBoton(false, vista.getJbtn_Modificar()); 
        vista.habilitarBoton(false, vista.getJbtn_Volver()); 
        
        //Crea instancia de proveedor
        Proveedor provModificado = new Proveedor();
        
        //setea proveedor en funcion al ID de la vista
        provModificado = modelo.findProveedor(Long.parseLong(vista.getJtfID().getText()));       
        
        //Llena el combobox de Zona
        llenarJcomboboxZona();
        //Posiciona dentro del combobox zona al objeto zona que posee el proveedor.
        vista.getJcb_zona_direccion().setSelectedItem(provModificado.getDireccion().getLocalidad().getProvincia().getZona());
        
        //Llena el combobox de provincia
        llenarJcomboboxProvincia(provModificado.getDireccion().getLocalidad().getProvincia().getZona()); 
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del proveedor.
        vista.getJcb_provincia_direccion().setSelectedItem(provModificado.getDireccion().getLocalidad().getProvincia());
        
        //Llena el combobox de Localidad
        llenarJcomboboxLocalidad(provModificado.getDireccion().getLocalidad().getProvincia());
        //Posiciona dentro del combobox Localidad al objeto Localidad dentro de Provincia dentro zona que posee el proveedor.
        vista.getJcb_localidad_direccion().setSelectedItem(provModificado.getDireccion().getLocalidad());
                
        //dniModificado incializa
        cuitModificado = vista.getJtfCUIT().getText();
        
        //desbloquea el boton modificar
        bloquearAceptarModificar = true;
        
        //Limpia la lista            
        vista.getTablaProveedores().setModel(new DefaultTableModel());
    }
    
    /**
     * Contola el Boton Eliminar
     * Desbloquea el Boton Aceptareliminar para poder eliminar
     */
    public void btn_eliminar(){ 
        
        //Posiciona la seleccion en el Panel datos proveedores. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
        bloquearAceptarCrear = false;
            
        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //inHabilitar Campos            
        inhabilitarTodosLosCampos(false);
        vista.habilitarCampo(false, vista.getJtfID());

        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        
        //inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());        
        vista.habilitarBoton(false, vista.getJbtn_Modificar()); 
        vista.habilitarBoton(false, vista.getJbtn_Volver()); 
        
        //Crea instancia de proveedor
        Proveedor provAEliminar = new Proveedor();
        
        //setea proveedor en funcion al ID de la vista
        provAEliminar = modelo.findProveedor(Long.parseLong(vista.getJtfID().getText()));       
        
        //Llena el combobox de Zona
        llenarJcomboboxZona();
        //Posiciona dentro del combobox zona al objeto zona que posee el proveedor.
        vista.getJcb_zona_direccion().setSelectedItem(provAEliminar.getDireccion().getLocalidad().getProvincia().getZona());
        
        //Llena el combobox de provincia
        llenarJcomboboxProvincia(provAEliminar.getDireccion().getLocalidad().getProvincia().getZona());         
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del proveedor.
        vista.getJcb_provincia_direccion().setSelectedItem(provAEliminar.getDireccion().getLocalidad().getProvincia());
        
        //Llena el combobox de Localidad
        llenarJcomboboxLocalidad(provAEliminar.getDireccion().getLocalidad().getProvincia());        
        //Posiciona dentro del combobox Localidad al objeto Localidad dentro de Provincia dentro zona que posee el proveedor.
        vista.getJcb_localidad_direccion().setSelectedItem(provAEliminar.getDireccion().getLocalidad());
                
        //cuitModificado incializa
        cuitModificado = vista.getJtfCUIT().getText();
        
        //cuitModificado incializa
        cuitModificado = vista.getJtfCUIT().getText();
        
        //Habilita boton Aceptar Eliminar Bloqueado
        bloquearAceptarEliminar = true;
        
        //Limpia la lista            
        vista.getTablaProveedores().setModel(new DefaultTableModel());
    }   
    
    /**
     * Controla el boton Listar
     * llena la tabla de la vista
     * si la tabla es mayor que 0 (cero), completa los JtextFields con el primer objeto de la tabla
     * si la tabla esta avcia no muestra nada
     */
    public void btn_listar(){
            //Inhabilita Boton
            vista.habilitarBoton(false, vista.getJbtn_Aceptar());
            vista.habilitarBoton(false, vista.getJbtn_Cancelar());
            
            //inhabilitar Campos
            inhabilitarTodosLosCampos(false);    
            
            //Habilita Botones
            vista.habilitarBoton(true, vista.getJbtn_Modificar());
            vista.habilitarBoton(true, vista.getJbtn_Eliminar());
            vista.habilitarBoton(true, vista.getJbtn_Agregar());
            vista.habilitarBoton(true, vista.getJbtn_Volver());
            
            //Llena la tabla
            llenarTabla(vista.getTablaProveedores());
            
           //Setea ancho de columna
            setAnchoColumna();
                
            //Carga el primer elemento si la lista es mayo a 1
            if (sizeTabla()>=0) {    
                //Si posee datos de direccion se cargan en la vista
                
                //Posicionar el cursor de la lista en el primer Proveedor
                vista.getJtfID().setText(proveedores.get(0).getId().toString());
                vista.getJtfCUIT().setText(proveedores.get(0).getCuit());
                vista.getJtfRazonSocial().setText(proveedores.get(0).getRazonSocial());
                if (proveedores.get(0).getId().toString().equals(vista.getJtfID().getText())) {
                    if (proveedores.get(0).getDireccion()!=null) {
                        if (proveedores.get(0).getDireccion().getLocalidad() !=null) {


                            vista.getJcb_zona_direccion().removeAllItems();
                            vista.getJcb_zona_direccion().addItem(proveedores.get(0).getDireccion().getLocalidad().getProvincia().getZona().getNombre());
                            
                            vista.getJcb_provincia_direccion().removeAllItems();
                            vista.getJcb_provincia_direccion().addItem(proveedores.get(0).getDireccion().getLocalidad().getProvincia().getNombre());
                            
                            vista.getJcb_localidad_direccion().removeAllItems();
                            vista.getJcb_localidad_direccion().addItem(proveedores.get(0).getDireccion().getLocalidad().getNombre());
                            
                            vista.getJtf_calle_direccion().setText(proveedores.get(0).getDireccion().getCalle());
                            vista.getJtf_numero_direccion().setText(proveedores.get(0).getDireccion().getNumero());
                            vista.getJtf_piso_direccion().setText(proveedores.get(0).getDireccion().getPiso());
                            vista.getJtf_departamento_direccion().setText(proveedores.get(0).getDireccion().getDepartamento());                            
                            
                        }else{
                            System.out.println("No tiene Localidad");
                        }

                    }else{
                        System.out.println("no tiene direccion");
                    }                    
                }
                
                //posiciona en foco de la lista en el primer Proveedor 
                vista.getTablaProveedores().changeSelection(0, 1, false, false);
            }else{
                //Habilita Botones
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());
                limpiarTodosLosCampos();
                JOptionPane.showMessageDialog(null, "No hay Proveedor que listar");
            }
            
            //Posiciona la seleccion en el Panel datos proveedores. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }
    
    /**
     * Controla el Boton Aceptar cuando se este creando
    crea instancia de proveedor, slo si existe localidad
    crea instancia de direccion y persiste, solamente si el proveedor no existe
    asocia la direccion al proveedor
    persiste proveedor
     */
    public void btn_aceptarCrear(){
        boolean proveedorCreado=false;
        
        if (vista.getJcb_localidad_direccion().getSelectedIndex()>=0) {   
            //Crear Instancia de Proveedor
            Proveedor prov = new Proveedor();
            
            //setea DNI proveedor
            prov.setCuit(vista.getJtfCUIT().getText());
                          
            //Crea Instancia de Direccion
            Direccion direccion = new Direccion(); 

            //setea Direccion
            direccion.setCalle(vista.getJtf_calle_direccion().getText());
            direccion.setNumero(vista.getJtf_numero_direccion().getText());
            direccion.setPiso(vista.getJtf_piso_direccion().getText());
            direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());
             
            //Instancia DireccionJpaController
            modeloDireccion = new DireccionJpaController(Conexion.getEmf());

            //Setea direccion con el campo JcomboBox de Objeto localidad
            direccion.setLocalidad((Localidad)vista.getJcb_localidad_direccion().getSelectedItem());
             
            
            //Verificar si el CUIT ingresado ya existe en la base de datos
            if (modelo.buscarProveedorCUIT(prov)==null) {   
                
               if (!proveedorCreado) {
                   //Setea Datos de Proveedor
                   prov.setRazonSocial(vista.getJtfRazonSocial().getText());
                   prov.setCuit(vista.getJtfCUIT().getText()); 
                   
                   //Persiste la Direccion
                   modeloDireccion.create(direccion);

                   //Agrega la direccion al proveedor
                   prov.setDireccion(direccion);
                   
                   //Agrega la empresa a la que pertenece el Proveedor
                   prov.setUnaEmpresa(empresa);
                   
                   //Persiste Proveedor
                   modelo.create(prov);  
                  
                   //Bandera de proveedor creado a verdadero
                   proveedorCreado =true;
                   
                   //Mensaje de proveedor Guardado
                   JOptionPane.showMessageDialog(null, "Proveedor Guardado");
               }
               
                
               if (proveedorCreado) {
                     
                   //llena la tabla de Proveedores
                   llenarTabla(vista.getTablaProveedores());
                   
                   //setea tamaño de columnas
                    setAnchoColumna();

                    //Habilita Botones
                    vista.habilitarBoton(true, vista.getJbtn_Listar());
                    vista.habilitarBoton(true, vista.getJbtn_Agregar()); 
                    vista.habilitarBoton(true, vista.getJbtn_Volver()); 

                    //Inhabilita Boton                    
                    inhabilitarTodosLosCampos(false);
                    
                    //Inhabilita los Botones
                    vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                    vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                    vista.habilitarBoton(false, vista.getJbtn_Modificar());
                    vista.habilitarBoton(false, vista.getJbtn_Eliminar());

                    //posiciona en foco de la lista en el ultimo Proveedor creado                    
                    vista.getTablaProveedores().changeSelection(sizeTabla(), 1, false, false);

                    //Da valor al ID en la tabla
                    vista.getJtfID().setText(String.valueOf(vista.getTablaProveedores().getValueAt(sizeTabla(), 1)));

                    //Posiciona la seleccion en el Panel datos proveedores. 
                     vista.getjTabbedPaneContenedor().setSelectedIndex(0);

                     //Todos los botones de aceptar Bloqueados
                     bloquearAceptarCrear=false;
                     bloquearAceptarEliminar = false;
                     bloquearAceptarModificar = false;
               }
               
                                
            
            }else{                
                JOptionPane.showMessageDialog(null, "El Proveedor ya existe o el CUIT es invalido");
            }  
        }else{
             JOptionPane.showMessageDialog(null, "Tiene que ingresar una Localidad");             
        }
        
    }
    
    /**
     * Controla el Boton Aceptar cuando se este modificando
    crea instancia de proveedor, en funcion al ID seleccionado
    crea una instancia de direccion del proveedor
    verifica que el DNI del proveedor es modificado, si lo es setea el dni del proveedor instanciado
    edita direccion
    edita proveedor
     */
    public void btn_aceptarModificar(){
        boolean proveedorModifocado=false;
        
        if (vista.getJcb_localidad_direccion().getSelectedIndex()>=0) {
            //instancia de proveedor igual al objeto guardado en Base de datos
            Proveedor prov = modelo.findProveedor(Long.parseLong(vista.getJtfID().getText()));

            //setea CUIT proveedor con nuevos valores
            prov.setCuit(vista.getJtfCUIT().getText());                

            //Instancia de Direccion
            Direccion direccion = new Direccion();  

            //setea Direccion
            direccion.setId(prov.getDireccion().getId());
            direccion.setCalle(vista.getJtf_calle_direccion().getText());
            direccion.setNumero(vista.getJtf_numero_direccion().getText());
            direccion.setPiso(vista.getJtf_piso_direccion().getText());
            direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());

            //Instancia DireccionJpaController
            modeloDireccion = new DireccionJpaController(Conexion.getEmf());

            //Setea direccion con el campo JcomboBox de Objeto localidad
            direccion.setLocalidad((Localidad)vista.getJcb_localidad_direccion().getSelectedItem());
                     
            
            if (!proveedorModifocado) {
                //verificar que el CUIT sea igual que el CUIT actual        
                if (prov.getCuit().equals(cuitModificado)) {            
                    //El CUIT es igual se mantiene sin cambio            
                    try {
                        //Setea Datos de Proveedor
                        prov.setRazonSocial(vista.getJtfRazonSocial().getText());                        

                        //Edita Direccion
                        modeloDireccion.edit(direccion);

                        //agrega direccion a proveedor
                        prov.setDireccion(direccion);

                        //Persiste Proveedor
                        modelo.edit(prov);
                        JOptionPane.showMessageDialog(null, "Proveedor modificado con CUIT igual");
                        
                        //Bandera de proveedor creado a verdadero
                        proveedorModifocado =true;

                        } catch (Exception ex) {
                            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 //Si el CUIT no es igual que el CUIT actual
                 }else{
                     //Verifica si existe el CUIT, si no existe se procede
                     if (modelo.buscarProveedorCUIT(prov)==null) {
                         //Se modifica el CUIT
                         prov.setCuit(vista.getJtfCUIT().getText());
                         try {
                            //Setea Datos de Proveedor
                            prov.setRazonSocial(vista.getJtfRazonSocial().getText());
                            
                            //Edita Direccion
                            modeloDireccion.edit(direccion);

                            //agrega direccion a prov
                            prov.setDireccion(direccion);

                            //Persiste Proveedor
                            modelo.edit(prov);
                            JOptionPane.showMessageDialog(null, "Proveedor y CUIT modificado");
                            
                            //Bandera de proveedor creado a verdadero
                            proveedorModifocado =true;

                        } catch (Exception ex) {
                            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                        }


                        }else{
                            JOptionPane.showMessageDialog(null, "El CUIT Ya existe, modifiquelo");
                        }
                 }
            }
            if (proveedorModifocado) {
                
                //llena la tabla de Proveedor
                llenarTabla(vista.getTablaProveedores());

                //setea tamaño de columnas
                setAnchoColumna();

                //Habilita Botones
                vista.habilitarBoton(true, vista.getJbtn_Listar());
                vista.habilitarBoton(true, vista.getJbtn_Agregar());  
                vista.habilitarBoton(true, vista.getJbtn_Volver());

                //Inhabilita Boton
                vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Agregar());

                inhabilitarTodosLosCampos(false);

                //posiciona en foco de la lista en el Proveedor del modificado
                vista.getTablaProveedores().changeSelection(buscarPosicionEnTabla(prov.getId()), 1, false, false);

                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear=false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;
                
                
            }
         }else{
             JOptionPane.showMessageDialog(null, "Tiene que ingresar una Localidad");             
        }
    }
    
    /**
     * Controla el Boton Aceptar cuando se este eliminando
    crea instancia de proveedor, en funcion al ID seleccionado
    crea una instancia de direccion del proveedor
    verifica que el proveedor tenga dreccion, si tiene direccion se elimia la direccion del proveedor,
    elimina direccion
    elimina proveedor
     */
    public void btn_aceptarEliminar(){
        modeloDireccion= new DireccionJpaController(Conexion.getEmf());
        //instancia de proveedor igual al objeto guardado en Base de datos
         Proveedor prov = modelo.findProveedor(Long.parseLong(vista.getJtfID().getText()));
         Direccion direccion = null;
         
         for (Proveedor proveedor : proveedores) {
             if (proveedor.equals(prov)) {                 
                 direccion = proveedor.getDireccion();
             }
        }
        
        try {
            //elimina proveedor
            if (direccion!= null) {
                //Se elimina Proveedor antes que la direccion por integridad referencia
                modelo.destroy(prov.getId());
                //Se elimina direccion sin asociacion
                modeloDireccion.destroy(direccion.getId());
                
                JOptionPane.showMessageDialog(null, "Proveedor con direccion eliminados");

                //llena la tabla de Proveedor
                llenarTabla(vista.getTablaProveedores());

                //setea tamaño de columnas
                setAnchoColumna();

                //Habilita Botones
                vista.habilitarBoton(true, vista.getJbtn_Listar());
                vista.habilitarBoton(true, vista.getJbtn_Agregar());  

                //Inhabilita Boton
                vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());

                inhabilitarTodosLosCampos(false);
                limpiarTodosLosCampos();

                //posiciona en foco de la lista en el Proveedor del modificado
                vista.getTablaProveedores().changeSelection(buscarPosicionEnTabla(prov.getId()), 1, false, false);
                              
                btn_listar();
                cuitModificado = vista.getJtfCUIT().getText();
                
                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear=false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;
            }else{
                modelo.destroy(prov.getId());
                JOptionPane.showMessageDialog(null, "Proveedor sin Direccion eliminado");

                //llena la tabla de Proveedor
                llenarTabla(vista.getTablaProveedores());

                //setea tamaño de columnas
                setAnchoColumna();

                //Habilita Botones
                vista.habilitarBoton(true, vista.getJbtn_Listar());
                vista.habilitarBoton(true, vista.getJbtn_Agregar());  

                //Inhabilita Boton
                vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());

                inhabilitarTodosLosCampos(false);
                limpiarTodosLosCampos();

                //posiciona en foco de la lista en el Proveedor del modificado
                vista.getTablaProveedores().changeSelection(buscarPosicionEnTabla(prov.getId()), 1, false, false);
                
                btn_listar();
                cuitModificado = vista.getJtfCUIT().getText();
                
                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear=false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Controla el Boton Cancelar
     * Bloquea el Boton AceptarCrear para poder crear
     * Bloquea el Boton AceptarEliminar para poder eliminar
     * Bloquea el Boton AceptarModificar para poder modificar
     */
    public void btn_cancelar(){
        //Limpiar Campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Volver());
        vista.habilitarBoton(true, vista.getJbtn_Listar());
        
        btn_listar();
        
        //Todos los botones de aceptar Bloqueados
        bloquearAceptarCrear=false;
        bloquearAceptarEliminar = false;
        bloquearAceptarModificar = false;
    }
    
    /**
     * Controla el Boton Volver
     * Bloquea el Boton AceptarCrear para poder crear
     * Bloquea el Boton AceptarEliminar para poder eliminar
     * Bloquea el Boton AceptarModificar para poder modificar
     * Habilita el Arbol del Panel Principal
     */
    public void btn_volver(){
        //Limpia campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);

        //Limpia la lista            
        vista.getTablaProveedores().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);
        
        //Todos los botones de aceptar Bloqueados
        bloquearAceptarCrear=false;
        bloquearAceptarEliminar = false;
        bloquearAceptarModificar = false;
    }
    
    
    
    /**
     * Llena Jtable de proveedor
    crea una lista de proveedores existentes en la base de datos. 
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD){
        proveedores = new ArrayList<Proveedor>();
        //Celdas no editables
        DefaultTableModel modeloT = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        };
        //Inmovilizar Columnas
        tablaD.getTableHeader().setReorderingAllowed(false) ;
        
        //Inhabilitar redimension de columnas
        tablaD.getTableHeader().setResizingAllowed(false);
        
        //Permite Seleccionar solamente una fila
        tablaD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);        
        
        tablaD.setModel(modeloT);               
        
        //Setea las cabeceras de la tabla
        modeloT.addColumn("N°");
        modeloT.addColumn("ID");
        modeloT.addColumn("CUIT");
        modeloT.addColumn("Razon Social"); 
        
        //Cantidad de columnas 
        Object [] columna = new Object[4];
        
        int numero = 0;
        
        for (Proveedor prov : modelo.findProveedorEntities()) {
            //Guarda en Lista de proveedores  
            proveedores.add(prov);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);   
            columna[1] = prov.getId();            
            columna[2] = prov.getCuit();
            columna[3] = prov.getRazonSocial();         
            
            modeloT.addRow(columna);
        }
    }
    
    /**
     * Modifica la habilitación de los JtextField de la vista en funcion al parametro de estado. 
     * @param estado de campos
     */
    public void inhabilitarTodosLosCampos(boolean estado){
        //inhabilita campos
        vista.habilitarCampo(estado, vista.getJtfID());
        vista.habilitarCampo(estado, vista.getJtfRazonSocial());
        vista.habilitarCampo(estado, vista.getJtfCUIT());
        
        vista.habilitarCampo(estado, vista.getJtf_calle_direccion());
        vista.habilitarCampo(estado, vista.getJtf_numero_direccion());
        vista.habilitarCampo(estado, vista.getJtf_piso_direccion());
        vista.habilitarCampo(estado, vista.getJtf_departamento_direccion());
        
        vista.habilitarCombobox(estado, vista.getJcb_zona_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_provincia_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_localidad_direccion());
    }
    
    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfRazonSocial());
        vista.limpiarCampo(vista.getJtfCUIT());
        
        vista.limpiarCampo(vista.getJtf_calle_direccion());
        vista.limpiarCampo(vista.getJtf_numero_direccion());
        vista.limpiarCampo(vista.getJtf_piso_direccion());
        vista.limpiarCampo(vista.getJtf_departamento_direccion());
        
        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_zona_direccion());
        vista.limpiarCombobox(vista.getJcb_provincia_direccion());
        vista.limpiarCombobox(vista.getJcb_localidad_direccion());
    }

    /**
     * Modifica la habilitación de los Botones de la vista en funcion al parametro de estado.
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado){
        //Inhabilita Botones CRUD
        vista.habilitarBoton(estado, vista.getJbtn_Agregar());            
        vista.habilitarBoton(estado, vista.getJbtn_Modificar());
        vista.habilitarBoton(estado, vista.getJbtn_Listar());
        vista.habilitarBoton(estado, vista.getJbtn_Eliminar());

        //Inhabilita Botones Aceptar-Cancelar
        vista.habilitarBoton(estado, vista.getJbtn_Aceptar());
        vista.habilitarBoton(estado, vista.getJbtn_Cancelar());

        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());
    }

    /**
     * Establece la politica de datos que contendran los elemmentos de la vista. 
     */
    public void politicaValidacionDeCampos(){
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtfRazonSocial());
        vista.getValidador().LimitarCaracteres(vista.getJtfRazonSocial(), 30);

        vista.getValidador().validarSoloNumero(vista.getJtfCUIT());
        vista.getValidador().LimitarCaracteres(vista.getJtfCUIT(), 10);
                
        vista.getValidador().LimitarCaracteres(vista.getJtf_calle_direccion(), 30);        
        
        //vista.getValidador().validarSoloNumero(vista.getJtf_numero_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_numero_direccion(), 5);       

        //vista.getValidador().validarSoloNumero(vista.getJtf_piso_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_piso_direccion(), 2);
                
        vista.getValidador().LimitarCaracteres(vista.getJtf_departamento_direccion(), 2);        
    }
       
    /**
     * Establece el Ancho de cada columna de la tabla proveedor de la vista.
     */
    public void setAnchoColumna(){
        TableColumnModel columnModel = vista.getTablaProveedores().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(150);
    }
    
    /**
     * Busca la posicion que ocupa un proveedor en la tabla proveedor
     * @param id de Proveedor
     * @return posicion de proveedor
     */
    public int buscarPosicionEnTabla(Long id){
        int posicion =0;
        for (Proveedor prov : modelo.findProveedorEntities()) {
            if (id.equals(prov.getId())) {
                return posicion;
            }
            posicion++;
        }
        
        return posicion;
    }
    
    /**
     * informa el tamaño de la tabla proveedores
     * @return tamaño de la tabla
     */
    public int sizeTabla(){
        int posicion =0;
        for (Proveedor prov : modelo.findProveedorEntities()) {            
            posicion++;
        }        
        return posicion-1;
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
            
    /**
     * Verifica el cambio de estado en los JComboBox
     * @param e Evento de cambio de JCOMBOBOX
     */
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

    /**
     * Verifica el Foco ganado por los elementos de la vista
     * @param e Evento de foco  Ganado
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource().equals(vista.getJcb_zona_direccion())) {
            this.zSeleccionada = true;
        }
        if (e.getSource().equals(vista.getJcb_provincia_direccion())) {            
            this.pSeleccionada = true;
        }
        if (e.getSource().equals(vista.getJcb_localidad_direccion())) {
            this.lSeleccionada = true;
        }
        if(e.getSource().equals(vista.getJtfCUIT())){
            cuitModificado = vista.getJtfCUIT().getText();
        }        
        
    }

    /**
     * Verifica el Foco perdido por los elementos de la vista
     * @param e Evento de Foco Perdido
     */
    @Override
    public void focusLost(FocusEvent e) {
        
        if (e.getSource().equals(vista.getJcb_zona_direccion())) {
            zSeleccionada = false;
        }
        if (e.getSource().equals(vista.getJcb_provincia_direccion())) {            
            pSeleccionada = false;
        }
        if (e.getSource().equals(vista.getJcb_localidad_direccion())) {
            lSeleccionada = false;
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
    
    /**
     * Verifica los eventos de click realizados en la tabla de proveedores
    si cambia se completan los datos del proveedor
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //carga los datos en la vista si cualquiera de las variables es verdadera
            int seleccion = vista.getTablaProveedores().rowAtPoint(e.getPoint());
            vista.getJtfID().setText(String.valueOf(vista.getTablaProveedores().getValueAt(seleccion, 1)));
            vista.getJtfCUIT().setText(String.valueOf(vista.getTablaProveedores().getValueAt(seleccion, 2)));
            vista.getJtfRazonSocial().setText(String.valueOf(vista.getTablaProveedores().getValueAt(seleccion, 3)));
            
            //Si posee datos de direccion se cargan en la vista
            for (Proveedor proveedor : proveedores) {
                if (proveedor.getId().toString().equals(vista.getJtfID().getText())) {
                    if (proveedor.getDireccion()!=null) {
                        if (proveedor.getDireccion().getLocalidad() !=null) {
                            vista.getJcb_zona_direccion().removeAllItems();
                            vista.getJcb_zona_direccion().addItem(proveedor.getDireccion().getLocalidad().getProvincia().getZona().getNombre());
                            
                            vista.getJcb_provincia_direccion().removeAllItems();
                            vista.getJcb_provincia_direccion().addItem(proveedor.getDireccion().getLocalidad().getProvincia().getNombre());
                            
                            vista.getJcb_localidad_direccion().removeAllItems();
                            vista.getJcb_localidad_direccion().addItem(proveedor.getDireccion().getLocalidad().getNombre());
                                                        
                            vista.getJtf_calle_direccion().setText(proveedor.getDireccion().getCalle());
                            vista.getJtf_numero_direccion().setText(proveedor.getDireccion().getNumero());
                            vista.getJtf_piso_direccion().setText(proveedor.getDireccion().getPiso());
                            vista.getJtf_departamento_direccion().setText(proveedor.getDireccion().getDepartamento());
                            
                            
                        }else{
                            JOptionPane.showMessageDialog(null, "Proveedor no tiene Localidad asignada");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Proveedor no tiene Direccion asignada");
                    }                    
                }
            }
            //Posiciona la seleccion en el Panel datos proveedores. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);            
        //}
                
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
    
}

