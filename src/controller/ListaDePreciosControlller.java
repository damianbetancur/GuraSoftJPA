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
import model.Articulo;
import model.Empresa;
import model.JPAController.ArticuloJpaController;
import model.JPAController.CategoriaArticuloJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.ListaDePrecioJpaController;
import model.JPAController.PrecioArticuloJpaController;
import model.JPAController.ProveedorJpaController;
import model.ListaDePrecio;
import model.PrecioArticulo;
import view.JframePrincipal;
import view.PanelRegistroListaDePrecioArticulo;



/**
 * Clase controladora de Articulos de categorias en el Catalogo
 * @author Ariel
 */
public class ListaDePreciosControlller extends Controller {
    
    private PanelRegistroListaDePrecioArticulo vista;
    private PrecioArticuloJpaController modelo;
    
   
    //modelos necesarios para el controlador.
    private EmpresaJpaController modeloEmpresa;
    private CategoriaArticuloJpaController modeloCategoriaArticulo;
    private ProveedorJpaController modeloProveedor;
    private ListaDePrecioJpaController modeloListaDePrecio;
    private ArticuloJpaController modeloArticuloCatalogo;
    private PrecioArticuloJpaController modeloPrecioArticulo;
        
    
    //Entidades necesarias para el controlador
    Empresa empresa = null;        
    ListaDePrecio listaDePrecio = null;
    
    
    
    List<PrecioArticulo> PreciosDeArticulos;
    
    List<Articulo> articulosCatalogo;

    /**
     * Constructor CatalogoCategoriaArticuloController
     * @param vista PanelRegistroCatalogoArticulo
     * @param modelo ArticuloJpaController
     */
    public ListaDePreciosControlller(PanelRegistroListaDePrecioArticulo vista, PrecioArticuloJpaController modelo) {
        modeloArticuloCatalogo = new ArticuloJpaController(Conexion.getEmf());
        modeloListaDePrecio = new ListaDePrecioJpaController(Conexion.getEmf());
        modeloPrecioArticulo = new PrecioArticuloJpaController(Conexion.getEmf());
        
        modeloEmpresa = new EmpresaJpaController(Conexion.getEmf());
        empresa = modeloEmpresa.findEmpresa(1L);
        
        this.vista = vista;
        this.modelo = modelo;        
        
        //inhabilita campos
        inhabilitarTodosLosCampos(false);
        
        //Inhabilita Botones
        vista.habilitarBoton(false, vista.getJbtn_Aceptar());
        vista.habilitarBoton(false, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_AsignarPrecio());
        
        
        listarArticulosDeListaDePrecios();
        politicaValidacionDeCampos();
    }
    
    /**
     * ActionPerformed
     * Controla los eventos que suceden en la vista al presionar los Botones de CRUD
     * @param e  recepcion de Evento
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
        
        //Boton AsignarPrecio
        if (e.getSource()==vista.getJbtn_AsignarPrecio()){
            btn_asignarPrecio();            
        }
        
        //Boton Aceptar
        if (e.getSource()==vista.getJbtn_Aceptar()){
            btn_aceptar();            
        }
        
        
    }


    
   
    
    /**
     * Controla el boton Listar
     * llena la tabla de la vista
     * si la tabla es mayor que 0 (cero), completa los JtextFields con el primer objeto de la tabla
     * si la tabla esta avcia no muestra nada
     */
    public void listarArticulosDeListaDePrecios(){
            //Inhabilita Boton
            vista.habilitarBoton(false, vista.getJbtn_Aceptar());
            vista.habilitarBoton(false, vista.getJbtn_Cancelar());
            
            //inhabilitar Campos
            inhabilitarTodosLosCampos(false);    
            
            //Habilita Botones
            vista.habilitarBoton(true, vista.getJbtn_AsignarPrecio());
            vista.habilitarBoton(true, vista.getJbtn_Volver());
            
            
            //Llena la tabla
            llenarTabla(vista.getTablaArticulos());
            
           //Setea ancho de columna
            setAnchoColumna();
                
            //Carga el primer elemento si la lista es mayo a 1
            if (sizeTabla()>=0) {    
                //Si posee datos de direccion se cargan en la vista
                
                //Posicionar el cursor de la lista en el primer Elemento
                vista.getJtfID().setText(articulosCatalogo.get(0).getId().toString());
                
                for (Articulo articulo : articulosCatalogo) {
                if (articulo.getId().toString().equals(vista.getJtfID().getText())) {
                    vista.getJtfDescripcion().setText(articulo.getDescripcion());
                    
                    //Setea JCBox de Categoria en articulo
                    vista.getJcb_Categoria().removeAllItems();
                    vista.getJcb_Categoria().addItem(articulo.getUnCategoriaDeArticulos().getDescripcion());
                    
                    //Setea JCBox de Proveedor en articulo
                    vista.getJcb_Proveedor().removeAllItems();
                    vista.getJcb_Proveedor().addItem(articulo.getUnProveedor().getRazonSocial());
                    
                    
                }
            }
          
                
                //posiciona en foco de la lista en el primer Empleado 
                vista.getTablaArticulos().changeSelection(0, 1, false, false);
            }else{
                //Habilita Botones
                vista.habilitarBoton(false, vista.getJbtn_AsignarPrecio());
                limpiarTodosLosCampos();
                JOptionPane.showMessageDialog(null, "No hay Articulos que listar");
            }
            
            //Posiciona la seleccion en el Panel datos PreciosDeArticulos. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }
    

    public void btn_asignarPrecio () {
        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
        
        vista.habilitarCombobox(true, vista.getJcb_ListaPrecio());
        vista.habilitarCampo(true, vista.getJtf_Precio());
        
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_AsignarPrecio());
        
        llenarJcomboboxListaDePrecio();
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
        
        listarArticulosDeListaDePrecios();
        
        
    }
    
    public void btn_aceptar(){
        boolean precioArticulo = false;
        
        if (!precioArticulo) {
        if (!vista.getJtf_Precio().getText().isEmpty()) {
            try {
                ListaDePrecio unaListaDePrecio = new ListaDePrecio();
                unaListaDePrecio = (ListaDePrecio)vista.getJcb_ListaPrecio().getSelectedItem();

                PrecioArticulo nuevoPrecioArticulo = new PrecioArticulo();

                nuevoPrecioArticulo.setId_articulo(Long.parseLong(vista.getJtfID().getText()));
                nuevoPrecioArticulo.setId_listaDePrecio(unaListaDePrecio.getId());

                float precio = Float.parseFloat(vista.getJtf_Precio().getText());
                nuevoPrecioArticulo.setPrecio(precio);
                
                
                if (modeloPrecioArticulo.buscarPrecioArticulo(nuevoPrecioArticulo)==null) {
                    modeloPrecioArticulo.create(nuevoPrecioArticulo);
                    precioArticulo = true;
                }else{                    
                    modeloPrecioArticulo.edit(nuevoPrecioArticulo);
                    JOptionPane.showMessageDialog(null, "Articulo en La lista de Precio Modificado");
                    precioArticulo = true;
                }
                
                
            } catch (Exception ex) {
                Logger.getLogger(ListaDePreciosControlller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Ingrese un precio para el Articulo");        
        }
        }
        if (precioArticulo) {
            //llena la tabla de Empleados
           llenarTabla(vista.getTablaArticulos());

           //setea tamaño de columnas
            setAnchoColumna();

            //Habilita Botones 
            vista.habilitarBoton(true, vista.getJbtn_Volver()); 

            //Inhabilita Boton                    
            inhabilitarTodosLosCampos(false);

            //Inhabilita los Botones
            vista.habilitarBoton(false, vista.getJbtn_Aceptar());
            vista.habilitarBoton(false, vista.getJbtn_Cancelar());

            //posiciona en foco de la lista en el ultimo Empleado creado                    
            vista.getTablaArticulos().changeSelection(sizeTabla(), 1, false, false);

            //Da valor al ID en la tabla
            vista.getJtfID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(sizeTabla(), 1)));

        }
        
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
        vista.getTablaArticulos().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);
        
     
    }
    
    
    
    /**
     * Llena Jtable de cliente
 crea una lista de PreciosDeArticulos existentes en la base de datos. 
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD){       
        articulosCatalogo = new ArrayList<Articulo>();
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
        
        for (Articulo articulo : modeloArticuloCatalogo.findArticuloEntities()) {
            //Guarda en Lista de articulos  
            articulosCatalogo.add(articulo);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);   
            columna[1] = articulo.getId();            
            columna[2] = articulo.getDescripcion();   
            
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
        vista.habilitarCampo(estado, vista.getJtf_Precio());
        
        
        vista.habilitarCombobox(estado, vista.getJcb_Categoria());
        vista.habilitarCombobox(estado, vista.getJcb_Proveedor());
        vista.habilitarCombobox(estado, vista.getJcb_ListaPrecio());
    }
    
    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfDescripcion()); 
        vista.limpiarCampo(vista.getJtf_Precio());
        
        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_Categoria());
        vista.limpiarCombobox(vista.getJcb_Proveedor());
        vista.limpiarCombobox(vista.getJcb_ListaPrecio());
        
    }

    /**
     * Modifica la habilitación de los Botones de la vista en funcion al parametro de estado.
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado){
        //Inhabilita Botones CRUD         
        vista.habilitarBoton(estado, vista.getJbtn_AsignarPrecio());

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
        
        vista.getValidador().LimitarCaracteres(vista.getJtf_Precio(), 8);
    }
       
    /**
     * Establece el Ancho de cada columna de la tabla cliente de la vista.
     */
    public void setAnchoColumna(){
        TableColumnModel columnModel = vista.getTablaArticulos().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25);
        columnModel.getColumn(1).setPreferredWidth(25);
        columnModel.getColumn(2).setPreferredWidth(350);
    }
    
    /**
     * Busca la posicion que ocupa un cliente en la tabla cliente
     * @param id de Empleado
     * @return posicion de cliente
     */
    public int buscarPosicionEnTabla(Long id){
        int posicion =0;
        /*
        for (PrecioArticulo precioArticulo : modelo.findPrecioArticuloEntities()) {
            /*if (id.equals(precioArticulo.toString()) {
                return posicion;
            }
            
            posicion++;
        }
        */
        return posicion;
    }
    
    /**
     * informa el tamaño de la tabla PreciosDeArticulos
     * @return tamaño de la tabla
     */
    public int sizeTabla(){
        int posicion =0;
        for (Articulo articulo : modeloArticuloCatalogo.findArticuloEntities()) {            
            posicion++;
        }        
        return posicion-1;
    }    
    
    /**
     * llena el JcomboBox de Zona con objetos Zona de la base de datos
     */
    public void llenarJcomboboxCategoria(){
        modeloCategoriaArticulo = new CategoriaArticuloJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloCategoriaArticulo.findCategoriaArticuloEntities());
        vista.getJcb_Categoria().setModel(mdl);
    }

    /**
     * llena el JcomboBox de Provincia con objetos Provincia de la base de datos en funcion a un objeto Zona
     * @param z Zona
     */
    public void llenarJcomboboxProveedor(){
        modeloProveedor = new ProveedorJpaController(Conexion.getEmf());             
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloProveedor.findProveedorEntities());
        vista.getJcb_Proveedor().setModel(mdl); 
    }
    
    public void llenarJcomboboxListaDePrecio(){        
        modeloListaDePrecio = new ListaDePrecioJpaController(Conexion.getEmf());             
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloListaDePrecio.findListaDePrecioEntities());
        vista.getJcb_ListaPrecio().setModel(mdl); 
    }
    
    public void buscarListaDePrecioDeArticulo(){
    
    
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
            
            
                        
        }
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
        if (!Character.isDigit(e.getKeyChar())&&e.getKeyChar()!='.') {
            e.consume();
        }
        if (e.getKeyChar()=='.'&&vista.getJtf_Precio().getText().contains(".")) {
            e.consume();
        }
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
    /**
     * Verifica los eventos de click realizados en la tabla de PreciosDeArticulos
 si cambia se completan los datos del cliente
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //carga los datos en la vista si cualquiera de las variables es verdadera
        //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
            int seleccion = vista.getTablaArticulos().rowAtPoint(e.getPoint());
            vista.getJtfID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
            
            for (Articulo articulo : articulosCatalogo) {
                if (articulo.getId().toString().equals(vista.getJtfID().getText())) {
                    vista.getJtfDescripcion().setText(articulo.getDescripcion());
                    
                    //Setea JCBox de Categoria en articulo
                    vista.getJcb_Categoria().removeAllItems();
                    vista.getJcb_Categoria().addItem(articulo.getUnCategoriaDeArticulos().getDescripcion());
                    
                    //Setea JCBox de Proveedor en articulo
                    vista.getJcb_Proveedor().removeAllItems();
                    vista.getJcb_Proveedor().addItem(articulo.getUnProveedor().getRazonSocial());
                    
                    PrecioArticulo unPrecioArticulo= new PrecioArticulo();
                    unPrecioArticulo.setId_articulo(articulo.getId());                    
                    if (modeloPrecioArticulo.buscarPrecioArticuloPrimerListaDePrecio(unPrecioArticulo)!=null) {
                        unPrecioArticulo = modeloPrecioArticulo.buscarPrecioArticuloPrimerListaDePrecio(unPrecioArticulo);
                        listaDePrecio = modeloListaDePrecio.findListaDePrecio(unPrecioArticulo.getId_listaDePrecio());
                        
                        vista.getJcb_ListaPrecio().removeAllItems();
                        vista.getJcb_ListaPrecio().addItem(listaDePrecio.getDescripcion());
                        
                        vista.getJtf_Precio().setText(Float.toString(unPrecioArticulo.getPrecio()));
                    }else{
                        vista.getJcb_ListaPrecio().removeAllItems();
                        vista.getJtf_Precio().setText("");
                    
                    }
                    
                    
                    /*
                    PrecioArticulo unPrecioArticulo= new PrecioArticulo();                    
                    modeloPrecioArticulo.buscarPrecioArticulo(unPrecioArticulo);
                    */        
                    
                   
                }
            }
            //Si posee datos de direccion se cargan en la vista
          
            
            //Posiciona la seleccion en el Panel datos PreciosDeArticulos. 
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
