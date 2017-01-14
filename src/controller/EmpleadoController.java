/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.JPAController.EmpleadoJpaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Empleado;
import view.JframePrincipal;
import view.PanelRegistroEmpleado;



/**
 *
 * @author Ariel
 */
public class EmpleadoController implements ActionListener, KeyListener, MouseListener{
    
    private PanelRegistroEmpleado vista;
    private EmpleadoJpaController modelo;
    boolean bloquearAceptar = true;

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
            vista.getJtfDireccion().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 5)));
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
        modeloT.addColumn("Direccion");
        
        //Cantidad de columnas 
        Object [] columna = new Object[6];
        
        int numero = 0;
        
        for (Empleado emp : modelo.findPersonaEntities()) {
            numero = numero + 1;
            columna[0] = String.valueOf(numero);   
            columna[1] =emp.getId();            
            columna[2] =emp.getDni();
            columna[3] =emp.getNombre();
            columna[4] =emp.getApellido();
            columna[5] =emp.getDireccion();
            
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
        vista.habilitarCampo(estado, vista.getJtfDireccion());
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
        vista.limpiarCampo(vista.getJtfDireccion());
    }
    
    /**
     * 
     * @param estado 
     */
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
    
    /**
     * 
     */
    public void politicaValidacionDeCampos(){
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtfApellido());
        vista.getValidador().LimitarCaracteres(vista.getJtfApellido(), 30);

        vista.getValidador().validarSoloLetras(vista.getJtfNombre());
        vista.getValidador().LimitarCaracteres(vista.getJtfNombre(), 30);            

        vista.getValidador().LimitarCaracteres(vista.getJtfDireccion(), 50);

        vista.getValidador().validarSoloNumero(vista.getJtfDNI());
        vista.getValidador().LimitarCaracteres(vista.getJtfDNI(), 8);
    }
    
    /**
     * 
     */
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
            
            //setea tamaño de columnas
                TableColumnModel columnModel = vista.getTablaEmpleados().getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(10);
                columnModel.getColumn(1).setPreferredWidth(50);
                columnModel.getColumn(2).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                columnModel.getColumn(4).setPreferredWidth(150);
                
            if (sizeTabla()>=0) {                
                //Posicionar el cursor de la lista en el primer Elemento
                vista.getJtfID().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(0, 1)));
                vista.getJtfDNI().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(0, 2)));
                vista.getJtfNombre().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(0, 3)));
                vista.getJtfApellido().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(0, 4)));        
                vista.getJtfDireccion().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(0, 5)));
                
                //posiciona en foco de la lista en el Empleado del modificado
                vista.getTablaEmpleados().changeSelection(0, 1, false, false);
            }else{
                //Habilita Botones
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());
                limpiarTodosLosCampos();
                JOptionPane.showMessageDialog(null, "No hay empleados que listar");
            }
    }
    
    /**
     * 
     */
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
    
    /**
     * 
     */
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
    }
    
    /**
     * 
     */
    public void btn_modificar(){
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
    }
    
    /**
     * 
     */
    public void btn_agregar(){
        
        
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
        
    }
    
    /**
     * 
     */
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
        for (Empleado emp : modelo.findPersonaEntities()) {
            if (id.equals(emp.getId())) {
                return posicion;
            }
            posicion++;
        }
        
        return posicion;
    }
    
    public int sizeTabla(){
        int posicion =0;
        for (Empleado emp : modelo.findPersonaEntities()) {            
            posicion++;
        }        
        return posicion-1;
    }
    
    public void btn_aceptarCrear(){
        //Crear Instancia de Empleado
        Empleado emp = new Empleado();               
        //setea empleado
         emp.setApellido(vista.getJtfApellido().getText());
         emp.setNombre(vista.getJtfNombre().getText());
         emp.setDni(vista.getJtfDNI().getText());
         emp.setDireccion(vista.getJtfDireccion().getText());

        //Verificar si el DNI ingresado ya existe en la base de datos
        if (modelo.buscarEmpleadoDNI(emp)==null) {
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
        }else{                
            JOptionPane.showMessageDialog(null, "El Empleado ya existe o el DNI es invalido");
        }    
    }
    
    public void btn_aceptarModificar(){
        //instancia de empleado igual al objeto guardado en Base de datos
         Empleado emp = modelo.findPersona(Long.parseLong(vista.getJtfID().getText()));
         //setea empleado
         emp.setApellido(vista.getJtfApellido().getText());
         emp.setNombre(vista.getJtfNombre().getText());
         emp.setDni(vista.getJtfDNI().getText());
         emp.setDireccion(vista.getJtfDireccion().getText());
         
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
    }
    
    public void btn_eliminar(){
        //instancia de empleado igual al objeto guardado en Base de datos
         Empleado emp = modelo.findPersona(Long.parseLong(vista.getJtfID().getText()));
         //setea empleado
         emp.setApellido(vista.getJtfApellido().getText());
         emp.setNombre(vista.getJtfNombre().getText());
         emp.setDni(vista.getJtfDNI().getText());
         emp.setDireccion(vista.getJtfDireccion().getText());

        try {
            //edita empleado
            modelo.destroy(emp.getId());
            JOptionPane.showMessageDialog(null, "empleado eliminado");

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
            

        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
