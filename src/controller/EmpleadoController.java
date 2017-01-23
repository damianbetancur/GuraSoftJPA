/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.JPAController.EmpleadoJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
import model.Empleado;
import model.JPAController.DireccionJpaController;
import model.JPAController.LocalidadJpaController;
import model.JPAController.ProvinciaJpaController;
import model.JPAController.ZonaJpaController;
import model.Localidad;
import model.Provincia;
import model.Zona;
import view.JframePrincipal;
import view.PanelRegistroEmpleado;



/**
 *
 * @author Ariel
 */
public class EmpleadoController implements ActionListener, KeyListener, MouseListener, ItemListener, FocusListener {
    
    private PanelRegistroEmpleado vista;
    private EmpleadoJpaController modelo;
    
    private ZonaJpaController modeloZona;
    private DireccionJpaController modeloDireccion;
    private ProvinciaJpaController modeloProvincia;
    private LocalidadJpaController modeloLocalidad;
        
    boolean bloquearAceptar = true;
    Zona zBuscada = null;
    Provincia pBuscada = null;
    Localidad lBuscada = null;
    
    boolean zSeleccionada = false;
    boolean pSeleccionada = false;
    boolean lSeleccionada = false;
    
    String dniModificado = null;
    
    int ultimoIndiceSeleccionado = 0;
    List<Empleado> empleados;

    public EmpleadoController(PanelRegistroEmpleado vista, EmpleadoJpaController modelo) {
        this.vista = vista;
        this.modelo = modelo;        
        
        //inhabilita campos
        inhabilitarTodosLosCampos(false);
        
        //Inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Aceptar());
        vista.habilitarBoton(false, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());
        
        
        
    }

    
    /**
     * actionPerformed
     * Controla los eventos que suceden en la vista
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        
        //Boton Agregar
        if (e.getSource()==vista.getJbtn_Nuevo()) {
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
        if (bloquearAceptar) {
            if (e.getSource()==vista.getJbtn_Aceptar()){
                btn_aceptarCrear();
            }
        }else{
            if (e.getSource()==vista.getJbtn_Aceptar()){
                btn_aceptarModificar();
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
        
        if (bloquearAceptar) {
            int seleccion = vista.getTablaEmpleados().rowAtPoint(e.getPoint());
            vista.getJtfID().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 1)));
            vista.getJtfDNI().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 2)));
            vista.getJtfNombre().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 3)));
            vista.getJtfApellido().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 4)));
            
            //Si posee datos de direccion se cargan en la vista
            for (Empleado empleado : empleados) {
                if (empleado.getId().toString().equals(vista.getJtfID().getText())) {
                    if (empleado.getDireccion()!=null) {
                        if (empleado.getDireccion().getLocalidad() !=null) {
                            vista.getJcb_zona_direccion().removeAllItems();
                            vista.getJcb_zona_direccion().addItem(empleado.getDireccion().getLocalidad().getProvincia().getZona().getNombre());
                            
                            vista.getJcb_provincia_direccion().removeAllItems();
                            vista.getJcb_provincia_direccion().addItem(empleado.getDireccion().getLocalidad().getProvincia().getNombre());
                            
                            vista.getJcb_localidad_direccion().removeAllItems();
                            vista.getJcb_localidad_direccion().addItem(empleado.getDireccion().getLocalidad().getNombre());
                            
                            
                            vista.getJtf_calle_direccion().setText(empleado.getDireccion().getCalle());
                            vista.getJtf_numero_direccion().setText(empleado.getDireccion().getNumero());
                            vista.getJtf_piso_direccion().setText(empleado.getDireccion().getPiso());
                            vista.getJtf_departamento_direccion().setText(empleado.getDireccion().getDepartamento());
                        }else{
                            JOptionPane.showMessageDialog(null, "empleado no tiene Localidad asignada");
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "empleado no tiene Direccion asignada");
                    }                    
                }
            }
            //Posiciona la seleccion en el Panel datos empleados. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);            
        }
                
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
    
    /**
     * 
     * @param tablaD 
     */
    public void llenarTabla(JTable tablaD){
        empleados = new ArrayList<Empleado>();
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
        modeloT.addColumn("DNI");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");        
        
        //Cantidad de columnas 
        Object [] columna = new Object[6];
        
        int numero = 0;
        
        for (Empleado emp : modelo.findEmpleadoEntities()) {
            //Guarda en Lista de empleados  
            empleados.add(emp);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);   
            columna[1] = emp.getId();            
            columna[2] = emp.getDni();
            columna[3] = emp.getNombre();
            columna[4] = emp.getApellido();           
            
            modeloT.addRow(columna);
        }
    }
    
    /**
     * 
     * @param estado 
     */
    public void inhabilitarTodosLosCampos(boolean estado){
        //inhabilita campos
        vista.habilitarCampo(estado, vista.getJtfID());
        vista.habilitarCampo(estado, vista.getJtfNombre());
        vista.habilitarCampo(estado, vista.getJtfApellido());
        vista.habilitarCampo(estado, vista.getJtfDNI());
        
        vista.habilitarCampo(estado, vista.getJtf_calle_direccion());
        vista.habilitarCampo(estado, vista.getJtf_numero_direccion());
        vista.habilitarCampo(estado, vista.getJtf_piso_direccion());
        vista.habilitarCampo(estado, vista.getJtf_departamento_direccion());
        
        vista.habilitarCombobox(estado, vista.getJcb_zona_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_provincia_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_localidad_direccion());
    }
    
    /**
     * LimpiarTodosLosCampos
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfNombre());
        vista.limpiarCampo(vista.getJtfApellido());
        vista.limpiarCampo(vista.getJtfDNI());
        
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
        //Inhabilita Botones CRUD
        vista.habilitarBoton(estado, vista.getJbtn_Nuevo());            
        vista.habilitarBoton(estado, vista.getJbtn_Modificar());
        vista.habilitarBoton(estado, vista.getJbtn_Listar());
        vista.habilitarBoton(estado, vista.getJbtn_Eliminar());

        //Inhabilita Botones Aceptar-Cancelar
        vista.habilitarBoton(estado, vista.getJbtn_Aceptar());
        vista.habilitarBoton(estado, vista.getJbtn_Cancelar());

        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());
    }

    public void politicaValidacionDeCampos(){
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtfApellido());
        vista.getValidador().LimitarCaracteres(vista.getJtfApellido(), 30);

        vista.getValidador().validarSoloLetras(vista.getJtfNombre());
        vista.getValidador().LimitarCaracteres(vista.getJtfNombre(), 30);            

        

        vista.getValidador().validarSoloNumero(vista.getJtfDNI());
        vista.getValidador().LimitarCaracteres(vista.getJtfDNI(), 8);
        
        //Direccion
        //vista.getValidador().validarSoloLetras(vista.getJtf_calle_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_calle_direccion(), 30);        
        
        //vista.getValidador().validarSoloNumero(vista.getJtf_numero_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_numero_direccion(), 5);       

        //vista.getValidador().validarSoloNumero(vista.getJtf_piso_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_piso_direccion(), 2);
                
        vista.getValidador().LimitarCaracteres(vista.getJtf_departamento_direccion(), 2);        
    }

    public void btn_listar(){
            bloquearAceptar = true;
            //Inhabilita Boton
            vista.habilitarBoton(false, vista.getJbtn_Aceptar());
            vista.habilitarBoton(false, vista.getJbtn_Cancelar());
            
            //inhabilitar Campos
            inhabilitarTodosLosCampos(false);    
            
            //Habilita Botones
            vista.habilitarBoton(true, vista.getJbtn_Modificar());
            vista.habilitarBoton(true, vista.getJbtn_Eliminar());
            vista.habilitarBoton(true, vista.getJbtn_Nuevo());
            vista.habilitarBoton(true, vista.getJbtn_Volver());
            
            //Llena la tabla
            llenarTabla(vista.getTablaEmpleados());
            
           //Setea ancho de columna
            setAnchoColumna();
                
            //Carga el primer elemento si la lista es mayo a 1
            if (sizeTabla()>=0) {    
                //Si posee datos de direccion se cargan en la vista
                
                //Posicionar el cursor de la lista en el primer Elemento
                vista.getJtfID().setText(empleados.get(0).getId().toString());
                vista.getJtfDNI().setText(empleados.get(0).getDni());
                vista.getJtfNombre().setText(empleados.get(0).getNombre());
                vista.getJtfApellido().setText(empleados.get(0).getApellido());
                if (empleados.get(0).getId().toString().equals(vista.getJtfID().getText())) {
                    if (empleados.get(0).getDireccion()!=null) {
                        if (empleados.get(0).getDireccion().getLocalidad() !=null) {


                            vista.getJcb_zona_direccion().removeAllItems();
                            vista.getJcb_zona_direccion().addItem(empleados.get(0).getDireccion().getLocalidad().getProvincia().getZona().getNombre());
                            
                            vista.getJcb_provincia_direccion().removeAllItems();
                            vista.getJcb_provincia_direccion().addItem(empleados.get(0).getDireccion().getLocalidad().getProvincia().getNombre());
                            
                            vista.getJcb_localidad_direccion().removeAllItems();
                            vista.getJcb_localidad_direccion().addItem(empleados.get(0).getDireccion().getLocalidad().getNombre());
                            
                            vista.getJtf_calle_direccion().setText(empleados.get(0).getDireccion().getCalle());
                            vista.getJtf_numero_direccion().setText(empleados.get(0).getDireccion().getNumero());
                            vista.getJtf_piso_direccion().setText(empleados.get(0).getDireccion().getPiso());
                            vista.getJtf_departamento_direccion().setText(empleados.get(0).getDireccion().getDepartamento());
                        }else{
                            System.out.println("No tiene Localidad");
                        }

                    }else{
                        System.out.println("no tiene direccion");
                    }                    
                }
                
                //posiciona en foco de la lista en el primer Empleado 
                vista.getTablaEmpleados().changeSelection(0, 1, false, false);
            }else{
                //Habilita Botones
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());
                limpiarTodosLosCampos();
                JOptionPane.showMessageDialog(null, "No hay empleados que listar");
            }
            
            //Posiciona la seleccion en el Panel datos empleados. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }

    public void btn_volver(){
        //Limpia campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);

        //Limpia la lista            
        vista.getTablaEmpleados().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);
    }    

    public void btn_cancelar(){
        //Limpiar Campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Aceptar());
        vista.habilitarBoton(false, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());

        //Habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Volver());
        vista.habilitarBoton(true, vista.getJbtn_Listar());
        vista.habilitarBoton(true, vista.getJbtn_Nuevo());
        btn_listar();
    }    
    
    public void btn_modificar(){
        //Posiciona la seleccion en el Panel datos empleados. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
        bloquearAceptar = false;
            
        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);
        vista.habilitarCampo(false, vista.getJtfID());

        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        
        //inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Nuevo());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());        
        vista.habilitarBoton(false, vista.getJbtn_Modificar()); 
        vista.habilitarBoton(false, vista.getJbtn_Volver()); 
        
        
        llenarJcomboboxZona();
        llenarJcomboboxProvincia(zBuscada);        
        llenarJcomboboxLocalidad(pBuscada);
        
        vista.getJcb_zona_direccion().setSelectedItem(zBuscada);
        vista.getJcb_provincia_direccion().setSelectedItem(pBuscada);
        vista.getJcb_localidad_direccion().setSelectedItem(lSeleccionada);
        
    }
        
    public void btn_agregar(){
        //Posiciona la seleccion en el Panel datos empleados. 
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
        vista.habilitarBoton(false, vista.getJbtn_Nuevo());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        
        //posiciona en foco de la lista en el ultimo Empleado creado                    
        vista.getTablaEmpleados().changeSelection(sizeTabla(), 1, false, false);
        
        //inicializa el JcomboBox
        llenarJcomboboxZona();
        llenarJcomboboxProvincia(zBuscada);
        llenarJcomboboxLocalidad(pBuscada);        
    }
       
    public void setAnchoColumna(){
        TableColumnModel columnModel = vista.getTablaEmpleados().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);
    }
    
    public int buscarPosicionEnTabla(Long id){
        int posicion =0;
        for (Empleado emp : modelo.findEmpleadoEntities()) {
            if (id.equals(emp.getId())) {
                return posicion;
            }
            posicion++;
        }
        
        return posicion;
    }
    
    public int sizeTabla(){
        int posicion =0;
        for (Empleado emp : modelo.findEmpleadoEntities()) {            
            posicion++;
        }        
        return posicion-1;
    }
    
    public void btn_aceptarCrear(){       
        
         if (vista.getJcb_localidad_direccion().getSelectedIndex()>=0) {
             //Crear Instancia de Empleado
            Empleado emp = new Empleado();
            Direccion direccion = new Direccion();                

            //setea empleado
             emp.setApellido(vista.getJtfApellido().getText());
             emp.setNombre(vista.getJtfNombre().getText());
             emp.setDni(vista.getJtfDNI().getText());

             //setea Direccion
             direccion.setCalle(vista.getJtf_calle_direccion().getText());
             direccion.setNumero(vista.getJtf_numero_direccion().getText());
             direccion.setPiso(vista.getJtf_piso_direccion().getText());
             direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());

             //Setea direccion con el campo JcomboBox de Objeto localidad
             direccion.setLocalidad((Localidad)vista.getJcb_localidad_direccion().getSelectedItem());  

           //Verificar si el DNI ingresado ya existe en la base de datos
           if (modelo.buscarEmpleadoDNI(emp)==null) {
            
                modeloDireccion = new DireccionJpaController(Conexion.getEmf());
                //Persiste la Direccion
               modeloDireccion.create(direccion);

               //Agrega la direccion al empleado
               emp.setDireccion(direccion);

               //Persiste Empleado
               modelo.create(emp);                    
               JOptionPane.showMessageDialog(null, "Empleado Guardado");  

               //llena la tabla de Empleados
               llenarTabla(vista.getTablaEmpleados());

               //setea tamaño de columnas
               setAnchoColumna();

               //Habilita Botones
               vista.habilitarBoton(true, vista.getJbtn_Listar());
               vista.habilitarBoton(true, vista.getJbtn_Nuevo()); 
               vista.habilitarBoton(true, vista.getJbtn_Volver()); 

               //Inhabilita Boton                    
               inhabilitarTodosLosCampos(false);
               vista.habilitarBoton(false, vista.getJbtn_Aceptar());
               vista.habilitarBoton(false, vista.getJbtn_Cancelar());
               vista.habilitarBoton(false, vista.getJbtn_Modificar());
               vista.habilitarBoton(false, vista.getJbtn_Eliminar());

               //posiciona en foco de la lista en el ultimo Empleado creado                    
               vista.getTablaEmpleados().changeSelection(sizeTabla(), 1, false, false);

               //Da valor al ID en la tabla
               vista.getJtfID().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(sizeTabla(), 1)));

               //Habilita la navegacion en la tabla
               bloquearAceptar = true;

               //Posiciona la seleccion en el Panel datos empleados. 
                vista.getjTabbedPaneContenedor().setSelectedIndex(0);
           
            
            }else{                
                JOptionPane.showMessageDialog(null, "El Empleado ya existe o el DNI es invalido");
            }  
        }else{
             JOptionPane.showMessageDialog(null, "Tiene que ingresar una Localidad");             
        }
          
    }
    
    public void btn_aceptarModificar(){
        
        //instancia de empleado igual al objeto guardado en Base de datos
        Empleado emp = modelo.findEmpleado(Long.parseLong(vista.getJtfID().getText()));
        //setea empleado con nuevos valores
        emp.setApellido(vista.getJtfApellido().getText());
        emp.setNombre(vista.getJtfNombre().getText());
        emp.setDni(vista.getJtfDNI().getText());
        
        Direccion direccion = new Direccion();               


         //setea Direccion
         direccion.setCalle(vista.getJtf_calle_direccion().getText());
         direccion.setNumero(vista.getJtf_numero_direccion().getText());
         direccion.setPiso(vista.getJtf_piso_direccion().getText());
         direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());

         //Setea direccion con el campo JcomboBox de Objeto localidad
         direccion.setLocalidad((Localidad)vista.getJcb_localidad_direccion().getSelectedItem());
        
         //verificar que el DNI NO EXISTA
         if (!emp.getDni().equals(dniModificado)) {            
            //El DNI es igual se mantiene sin cambio            
            try {
                    modeloDireccion = new DireccionJpaController(Conexion.getEmf());
                    modeloDireccion.edit(emp.getDireccion());
                    //edita empleado
                    emp.setDireccion(direccion);
                    modelo.edit(emp);
                    JOptionPane.showMessageDialog(null, "empleado modificado");

                    //llena la tabla de Empleados
                    llenarTabla(vista.getTablaEmpleados());

                    //setea tamaño de columnas
                    setAnchoColumna();

                    //Habilita Botones
                    vista.habilitarBoton(true, vista.getJbtn_Listar());
                    vista.habilitarBoton(true, vista.getJbtn_Nuevo());  

                    //Inhabilita Boton
                    vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                    vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                    vista.habilitarBoton(false, vista.getJbtn_Modificar());

                    inhabilitarTodosLosCampos(false);

                    //posiciona en foco de la lista en el Empleado del modificado
                    vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

                    //Habilita la navegacion en la tabla
                    bloquearAceptar = true;

                } catch (Exception ex) {
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }
         }else{
             //Verifica si existe el DNI, si no existe se procede
             if (modelo.buscarEmpleadoDNI(emp)==null) {
                 //Se modifica el DNI
                 emp.setDni(vista.getJtfDNI().getText());
                 try {
                    //edita empleado
                    modelo.edit(emp);
                    JOptionPane.showMessageDialog(null, "empleado modificado");

                    //llena la tabla de Empleados
                    llenarTabla(vista.getTablaEmpleados());

                    //setea tamaño de columnas
                    setAnchoColumna();

                    //Habilita Botones
                    vista.habilitarBoton(true, vista.getJbtn_Listar());
                    vista.habilitarBoton(true, vista.getJbtn_Nuevo());  

                    //Inhabilita Boton
                    vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                    vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                    vista.habilitarBoton(false, vista.getJbtn_Modificar());

                    inhabilitarTodosLosCampos(false);

                    //posiciona en foco de la lista en el Empleado del modificado
                    vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

                    //Habilita la navegacion en la tabla
                    bloquearAceptar = true;

                } catch (Exception ex) {
                    Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                }


                }else{
                    JOptionPane.showMessageDialog(null, "El DNI Ya existe, modifiquelo");
                }
         }      
      
    }
    
    public void btn_eliminar(){        
        modeloDireccion= new DireccionJpaController(Conexion.getEmf());
        //instancia de empleado igual al objeto guardado en Base de datos
         Empleado emp = modelo.findEmpleado(Long.parseLong(vista.getJtfID().getText()));
         Direccion direccion = null;
         
         for (Empleado empleado : empleados) {
             if (empleado.equals(emp)) {                 
                 direccion = empleado.getDireccion();
             }
        }
        
        try {
            //elimina empleado
            if (direccion!= null) {
                //Se elimina Empleado antes que la direccion por integridad referencia
                modelo.destroy(emp.getId());
                //Se elimina direccion sin asociacion
                modeloDireccion.destroy(direccion.getId());
                
                JOptionPane.showMessageDialog(null, "empleado con direccion eliminados");

                //llena la tabla de Empleados
                llenarTabla(vista.getTablaEmpleados());

                //setea tamaño de columnas
                setAnchoColumna();

                //Habilita Botones
                vista.habilitarBoton(true, vista.getJbtn_Listar());
                vista.habilitarBoton(true, vista.getJbtn_Nuevo());  

                //Inhabilita Boton
                vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());

                inhabilitarTodosLosCampos(false);
                limpiarTodosLosCampos();

                //posiciona en foco de la lista en el Empleado del modificado
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

                //Habilita la navegacion en la tabla
                bloquearAceptar = true;
                
            }else{
                //System.out.println(emp.getDireccion().getLocalidad().getNombre());
                modelo.destroy(emp.getId());
                JOptionPane.showMessageDialog(null, "empleado sin Direccion eliminado");

                //llena la tabla de Empleados
                llenarTabla(vista.getTablaEmpleados());

                //setea tamaño de columnas
                setAnchoColumna();

                //Habilita Botones
                vista.habilitarBoton(true, vista.getJbtn_Listar());
                vista.habilitarBoton(true, vista.getJbtn_Nuevo());  

                //Inhabilita Boton
                vista.habilitarBoton(false, vista.getJbtn_Aceptar());
                vista.habilitarBoton(false, vista.getJbtn_Cancelar());
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());

                inhabilitarTodosLosCampos(false);
                limpiarTodosLosCampos();

                //posiciona en foco de la lista en el Empleado del modificado
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

                //Habilita la navegacion en la tabla
                bloquearAceptar = true;
                
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
    
    public void llenarJcomboboxZona(){
        modeloZona = new ZonaJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloZona.findZonaEntities());
        vista.getJcb_zona_direccion().setModel(mdl);
        this.zBuscada = (Zona)vista.getJcb_zona_direccion().getSelectedItem();
    }

    public void llenarJcomboboxProvincia(Zona z){
        modeloProvincia = new ProvinciaJpaController(Conexion.getEmf());             
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloProvincia.buscarProvinciasPorZona(z));
        vista.getJcb_provincia_direccion().setModel(mdl);        
        this.pBuscada = (Provincia)vista.getJcb_provincia_direccion().getSelectedItem();
    }
    
    public void llenarJcomboboxLocalidad(Provincia p){
        modeloLocalidad = new LocalidadJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloLocalidad.buscarLocalidadPorProvincia(p));
        vista.getJcb_localidad_direccion().setModel(mdl);
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
        if (e.getSource().equals(vista.getJcb_zona_direccion())) {
            this.zSeleccionada = true;
        }
        if (e.getSource().equals(vista.getJcb_provincia_direccion())) {            
            this.pSeleccionada = true;
        }
        if (e.getSource().equals(vista.getJcb_localidad_direccion())) {
            this.lSeleccionada = true;
        }
        
    }

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
        if(e.getSource().equals(vista.getJtfDNI())){
            dniModificado = vista.getJtfDNI().getText();
        }
        
    }
    
}
