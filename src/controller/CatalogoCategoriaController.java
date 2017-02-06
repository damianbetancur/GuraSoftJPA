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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.CatalogoArticulos;
import model.CategoriaArticulo;
import model.JPAController.CatalogoArticuloJpaController;
import model.JPAController.CategoriaArticuloJpaController;
import view.JframePrincipal;
import view.PanelRegistroCatalogoCategoria;

/**
 *
 * @author Ariel
 */
public class CatalogoCategoriaController extends Controller{

    private PanelRegistroCatalogoCategoria vista;
    private CategoriaArticuloJpaController modelo;
    private CatalogoArticuloJpaController modeloCatalogoArticulo;    
        
    boolean bloquearAceptarCrear = false;
    boolean bloquearAceptarEliminar = false;
    boolean bloquearAceptarModificar = false;
       
    CatalogoArticulos catalogo = null;           
    
    int ultimoIndiceSeleccionado = 0;
    List<CategoriaArticulo> categorias;

    /**
     * Constructor Empleado
     * @param vista PanelRegistroEmpleado
     * @param modelo EmpleadoJpaController
     */
    public CatalogoCategoriaController(PanelRegistroCatalogoCategoria vista, CategoriaArticuloJpaController modelo) {
        modeloCatalogoArticulo = new CatalogoArticuloJpaController(Conexion.getEmf());
        catalogo = modeloCatalogoArticulo.findCatalogoDeArticulos(1L);
        
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
        //Posiciona la seleccion en el Panel datos categorias. 
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
        
        //posiciona en foco de la lista en el ultimo Empleado creado                    
        vista.getTablaCategorias().changeSelection(sizeTabla(), 1, false, false);                
        
        //desbloquea el boton nuevo
        bloquearAceptarCrear=true;      
        
        //Limpia la lista            
        vista.getTablaCategorias().setModel(new DefaultTableModel());
    }
    
    /**
     * Controla el Boton Modificar
     * Desbloquea el Boton AceptarModificar para poder modificar
     */
    public void btn_modificar(){
        
        //Posiciona la seleccion en el Panel datos categorias. 
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
        
        //Crea instancia de cliente
        CategoriaArticulo categoriaModificado = new CategoriaArticulo();
        
        //setea cliente en funcion al ID de la vista
        categoriaModificado = modelo.findCategoriaArticulo(Long.parseLong(vista.getJtfID().getText()));       
        
        
        //desbloquea el boton modificar
        bloquearAceptarModificar = true;
        
        //Limpia la lista            
        vista.getTablaCategorias().setModel(new DefaultTableModel());
    }
    
    /**
     * Contola el Boton Eliminar
     * Desbloquea el Boton Aceptareliminar para poder eliminar
     */
    public void btn_eliminar(){ 
        
        //Posiciona la seleccion en el Panel datos categorias. 
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
        
        //Crea instancia de cliente
        CategoriaArticulo categoriaAEliminar = new CategoriaArticulo();
        
        //setea cliente en funcion al ID de la vista
        categoriaAEliminar = modelo.findCategoriaArticulo(Long.parseLong(vista.getJtfID().getText()));      
        
        
        //Habilita boton Aceptar Eliminar Bloqueado
        bloquearAceptarEliminar = true;
        
        //Limpia la lista            
        vista.getTablaCategorias().setModel(new DefaultTableModel());
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
            llenarTabla(vista.getTablaCategorias());
            
           //Setea ancho de columna
            setAnchoColumna();
                
            //Carga el primer elemento si la lista es mayo a 1
            if (sizeTabla()>=0) {    
                //Si posee datos de direccion se cargan en la vista
                
                //Posicionar el cursor de la lista en el primer Elemento
                vista.getJtfID().setText(categorias.get(0).getId().toString());
                vista.getJtfDescripcion().setText(categorias.get(0).getDescripcion());
                
                //posiciona en foco de la lista en el primer Empleado 
                vista.getTablaCategorias().changeSelection(0, 1, false, false);
            }else{
                //Habilita Botones
                vista.habilitarBoton(false, vista.getJbtn_Modificar());
                vista.habilitarBoton(false, vista.getJbtn_Eliminar());
                limpiarTodosLosCampos();
                JOptionPane.showMessageDialog(null, "No hay Categorias que listar");
            }
            
            //Posiciona la seleccion en el Panel datos categorias. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }
    
    /**
     * Controla el Boton Aceptar cuando se este creando
 crea instancia de cliente, slo si existe localidad
 crea instancia de direccion y persiste, solamente si el cliente no existe
 asocia la direccion al cliente
 persiste cliente
     */
    public void btn_aceptarCrear(){
        boolean categoriaCreada = false;


        //Crear Instancia de Categoria
        CategoriaArticulo categoria = new CategoriaArticulo();

        //setea descripcion de Categoria
        categoria.setDescripcion(vista.getJtfDescripcion().getText());


       if (!categoriaCreada) {

           //Agrega la catalogo a la que pertenece el Cliente
           categoria.setUnCatalogoDeArticulos(catalogo);

           //Persiste Empleado
           modelo.create(categoria);

           //Bandera de cliente creado a verdadero
           categoriaCreada =true;

           //Mensaje de cliente Guardado
           JOptionPane.showMessageDialog(null, "Categoria Guardado");
       }


       if (categoriaCreada) {
           //llena la tabla de Empleados
           llenarTabla(vista.getTablaCategorias());

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

            //posiciona en foco de la lista en el ultimo Empleado creado                    
            vista.getTablaCategorias().changeSelection(sizeTabla(), 1, false, false);

            //Da valor al ID en la tabla
            vista.getJtfID().setText(String.valueOf(vista.getTablaCategorias().getValueAt(sizeTabla(), 1)));

            //Posiciona la seleccion en el Panel datos categorias. 
             vista.getjTabbedPaneContenedor().setSelectedIndex(0);

             //Todos los botones de aceptar Bloqueados
             bloquearAceptarCrear=false;
             bloquearAceptarEliminar = false;
             bloquearAceptarModificar = false;
       }
    }
    
    /**
     * Controla el Boton Aceptar cuando se este modificando
    crea instancia de cliente, en funcion al ID seleccionado
    crea una instancia de direccion del cliente
    verifica que el DNI del cliente es modificado, si lo es setea el dni del cliente instanciado
    edita direccion
    edita cliente
     */
    public void btn_aceptarModificar(){
        boolean categoriaModifocada = false;
                
        //instancia de Categoria igual al objeto guardado en Base de datos
        CategoriaArticulo categoria = modelo.findCategoriaArticulo(Long.parseLong(vista.getJtfID().getText()));

        //setea Descripcion Categoria con nuevos valores
        categoria.setDescripcion(vista.getJtfDescripcion().getText());  

        if (!categoriaModifocada) {       
            try {
                //agrega el catalogo a categoria
                categoria.setUnCatalogoDeArticulos(catalogo);

                //Persiste Categoria
                modelo.edit(categoria);
                JOptionPane.showMessageDialog(null, "Categoria modificada");

                //Bandera de Categoria creado a verdadero
                categoriaModifocada =true;

                } catch (Exception ex) {
                    Logger.getLogger(CatalogoCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if (categoriaModifocada) {
            //llena la tabla de Categoria
            llenarTabla(vista.getTablaCategorias());

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

            //posiciona en foco de la lista en el Empleado del modificado
            vista.getTablaCategorias().changeSelection(buscarPosicionEnTabla(categoria.getId()), 1, false, false);

            //Todos los botones de aceptar Bloqueados
            bloquearAceptarCrear=false;
            bloquearAceptarEliminar = false;
            bloquearAceptarModificar = false;
        }         
    }
    
    /**
     * Controla el Boton Aceptar cuando se este eliminando
    crea instancia de cliente, en funcion al ID seleccionado
    crea una instancia de direccion del cliente
    verifica que el cliente tenga dreccion, si tiene direccion se elimia la direccion del cliente,
    elimina direccion
    elimina cliente
     */
    public void btn_aceptarEliminar(){
        boolean categoriaEliminada = false;
        
        //instancia de cliente igual al objeto guardado en Base de datos
         CategoriaArticulo categoria = modelo.findCategoriaArticulo(Long.parseLong(vista.getJtfID().getText()));
               
        if (!categoriaEliminada) { 
            try {
                //Se elimina Categoria 
                modelo.destroy(categoria.getId());    
                categoriaEliminada = true;
                JOptionPane.showMessageDialog(null, "Categoria Eliminada");

            } catch (Exception ex) {
                Logger.getLogger(CatalogoCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (categoriaEliminada) {
            //llena la tabla de Categorias
            llenarTabla(vista.getTablaCategorias());

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

            //posiciona en foco de la lista en el Empleado del modificado
            vista.getTablaCategorias().changeSelection(buscarPosicionEnTabla(categoria.getId()), 1, false, false);

            btn_listar();

            //Todos los botones de aceptar Bloqueados
            bloquearAceptarCrear=false;
            bloquearAceptarEliminar = false;
            bloquearAceptarModificar = false;
            
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
        vista.getTablaCategorias().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);
        
        //Todos los botones de aceptar Bloqueados
        bloquearAceptarCrear=false;
        bloquearAceptarEliminar = false;
        bloquearAceptarModificar = false;
    }
    
    
    
    /**
     * Llena Jtable de cliente
 crea una lista de categorias existentes en la base de datos. 
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD){
        categorias = new ArrayList<CategoriaArticulo>();
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
        modeloT.addColumn("Descripcion");
        
        //Cantidad de columnas 
        Object [] columna = new Object[4];
        
        int numero = 0;
        
        for (CategoriaArticulo categoria : modelo.findCategoriaArticuloEntities()) {
            //Guarda en Lista de categorias  
            categorias.add(categoria);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);   
            columna[1] = categoria.getId();            
            columna[2] = categoria.getDescripcion();   
            
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
        vista.habilitarCampo(estado, vista.getJtfDescripcion());        
    }
    
    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfDescripcion());        
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
        vista.getValidador().validarSoloLetras(vista.getJtfDescripcion());
        vista.getValidador().LimitarCaracteres(vista.getJtfDescripcion(), 30);           
             
    }
       
    /**
     * Establece el Ancho de cada columna de la tabla cliente de la vista.
     */
    public void setAnchoColumna(){
        TableColumnModel columnModel = vista.getTablaCategorias().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(150);
    }
    
    /**
     * Busca la posicion que ocupa un cliente en la tabla cliente
     * @param id de Empleado
     * @return posicion de cliente
     */
    public int buscarPosicionEnTabla(Long id){
        int posicion =0;
        for (CategoriaArticulo categoria : modelo.findCategoriaArticuloEntities()) {
            if (id.equals(categoria.getId())) {
                return posicion;
            }
            posicion++;
        }
        
        return posicion;
    }
    
    /**
     * informa el tamaño de la tabla categorias
     * @return tamaño de la tabla
     */
    public int sizeTabla(){
        int posicion =0;
        for (CategoriaArticulo categoria : modelo.findCategoriaArticuloEntities()) {            
            posicion++;
        }        
        return posicion-1;
    }    
    

    
    /**
     * Verifica el cambio de estado en los JComboBox
     * @param e Evento de cambio de JCOMBOBOX
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        
    }

    /**
     * Verifica el Foco ganado por los elementos de la vista
     * @param e Evento de foco  Ganado
     */
    @Override
    public void focusGained(FocusEvent e) {
        
    }

    /**
     * Verifica el Foco perdido por los elementos de la vista
     * @param e Evento de Foco Perdido
     */
    @Override
    public void focusLost(FocusEvent e) {
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
     * Verifica los eventos de click realizados en la tabla de categorias
 si cambia se completan los datos del cliente
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //carga los datos en la vista si cualquiera de las variables es verdadera
        //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
            int seleccion = vista.getTablaCategorias().rowAtPoint(e.getPoint());
            vista.getJtfID().setText(String.valueOf(vista.getTablaCategorias().getValueAt(seleccion, 1)));
            vista.getJtfDescripcion().setText(String.valueOf(vista.getTablaCategorias().getValueAt(seleccion, 2)));
            
            //Posiciona la seleccion en el Panel datos categorias. 
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
