package controller;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Articulo;
import model.Empresa;
import model.JPAController.ArticuloJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.ListaDePrecioJpaController;
import model.JPAController.PrecioArticuloJpaController;
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
    private ListaDePrecioJpaController modeloListaDePrecio;
    private ArticuloJpaController modeloArticuloCatalogo;
    private PrecioArticuloJpaController modeloPrecioArticulo;
    
    //Entidades necesarias para el controlador
    Empresa empresa = null;        
    
    
    ListaDePrecio lpBuscada = null;
    
    
    List<Articulo> articulosCatalogo;
    
    List<PrecioArticulo> articulosprecios;

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
        
        
        
        llenarJcomboboxListaDePrecio();
        this.lpBuscada = (ListaDePrecio) vista.getJcb_ListaDePrecio().getSelectedItem();
        
        llenarTabla(vista.getTablaArticulos());
            
            if(!articulosCatalogo.isEmpty()){
                //posiciona en foco de la lista en el Articulo 
                vista.getTablaArticulos().changeSelection(0, 1, false, false); 
                
                Articulo articuloEncontrado = null;
                int seleccion = 0;
                vista.getJtf_ID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
                for (Articulo articulo : articulosCatalogo) {
                    if (articulo.getId().toString().equals(vista.getJtf_ID().getText())) {
                        articuloEncontrado = articulo;
                    }
                }
                vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion());

                //Setea JCBox de Categoria en articulo
                vista.getJcb_Categoria().removeAllItems();
                vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeArticulos().getDescripcion());

                //Setea JCBox de Proveedor en articulo
                vista.getJcb_Proveedor().removeAllItems();
                vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());
                
            }else{
                System.out.println("lista vacia");
            }
      
    }
    
    /**
     * ActionPerformed
     * Controla los eventos que suceden en la vista al presionar los Botones de CRUD
     * @param e  recepcion de Evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
         //Boton Volver
        if (e.getSource()==vista.getJbtn_Volver()){
            btn_volver();
        }
      
         //Boton Volver
        if (e.getSource()==vista.getJbtn_Agregar()){
            btn_volver();
        }
        
         //Boton Volver
        if (e.getSource()==vista.getJbtn_Cancelar()){
            btn_volver();
        }
        
         //Boton Volver
        if (e.getSource()==vista.getJbtn_Modificar()){
            btn_volver();
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
        boolean agregado = false;
        articulosCatalogo = new ArrayList<Articulo>();
        articulosprecios = new ArrayList<PrecioArticulo>();
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
            
            if(!modeloPrecioArticulo.findPrecioArticuloEntities().isEmpty()){
                for (PrecioArticulo precioArticulo : modeloPrecioArticulo.findPrecioArticuloEntities()) {
                    if (articulo.getId().equals(precioArticulo.getId_articulo()) && lpBuscada.getId().equals(precioArticulo.getId_listaDePrecio())&& agregado==false) {
                        articulosprecios.add(precioArticulo);
                        
                         //Guarda en Lista de articulos  
                        articulosCatalogo.add(articulo);
                        numero = numero + 1;
                        columna[0] = String.valueOf(numero);   
                        columna[1] = articulo.getId();            
                        columna[2] = articulo.getDescripcion();   

                        modeloT.addRow(columna);
                        //tiene precio
                        agregado = true;
                    }
                    if(agregado==false){
                        articulosCatalogo.add(articulo);
                        numero = numero + 1;
                        columna[0] = String.valueOf(numero);   
                        columna[1] = articulo.getId();            
                        columna[2] = articulo.getDescripcion();   

                        modeloT.addRow(columna);
                        agregado = true;
                    }

                }            
            }else{
                articulosCatalogo.add(articulo);
                numero = numero + 1;
                columna[0] = String.valueOf(numero);   
                columna[1] = articulo.getId();            
                columna[2] = articulo.getDescripcion();   

                modeloT.addRow(columna);
            
            }
            
           
            
            agregado = false;
        }
        
        
        setAnchoColumna();
    }
    
    /**
     * Establece el Ancho de cada columna de la tabla articulo de la vista.
     */
    public void setAnchoColumna(){
        TableColumnModel columnModel = vista.getTablaArticulos().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(450);
    }
    
    
    /**
     * Carga las listas de precios definidas
     */    
    public void llenarJcomboboxListaDePrecio(){ 
            modeloListaDePrecio = new ListaDePrecioJpaController(Conexion.getEmf());
            DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloListaDePrecio.findListaDePrecioEntities());
            vista.getJcb_ListaDePrecio().setModel(mdl);
            
    }
    
    
    
    /**
     * Verifica el cambio de estado en los JComboBox de Lista de Precio
     * @param e Evento de cambio de JCOMBOBOX
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        Articulo articuloEncontrado = null;
        PrecioArticulo precioArticuloEncontrado = null;
        
        if (e.getStateChange() == ItemEvent.SELECTED) {
            
            this.lpBuscada = (ListaDePrecio) vista.getJcb_ListaDePrecio().getSelectedItem();
            
            llenarTabla(vista.getTablaArticulos());
            
            if(!articulosCatalogo.isEmpty()){
                //posiciona en foco de la lista en el Articulo 
                vista.getTablaArticulos().changeSelection(0, 1, false, false);                 
                
                //Posiciona el cursor en el 1er elemento de la Tabla de Articulos
                int seleccion = 0;
                vista.getJtf_ID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
                
                for (Articulo articulo : articulosCatalogo) {
                    if (articulo.getId().toString().equals(vista.getJtf_ID().getText())) {
                        articuloEncontrado = articulo;
                    }
                    
                    if(!articulosprecios.isEmpty()){
                        for (PrecioArticulo PreciosDeArticulo : articulosprecios) {
                            if(PreciosDeArticulo.getId_articulo().equals(articuloEncontrado.getId())){
                                precioArticuloEncontrado = PreciosDeArticulo;
                            }
                        }
                    }
                    
                }
                
                
                
                vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion());

                //Setea JCBox de Categoria en articulo
                vista.getJcb_Categoria().removeAllItems();
                vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeArticulos().getDescripcion());

                //Setea JCBox de Proveedor en articulo
                vista.getJcb_Proveedor().removeAllItems();
                vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());
                
                if(precioArticuloEncontrado != null){
                    vista.getJtf_precio().setText("");
                    vista.getJtf_precio().setText(Float.toString(precioArticuloEncontrado.getPrecio()));
                }
                
                
            }else{
                System.out.println("lista vacia");
            }
            
            
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
        Articulo articuloEncontrado = null;
        PrecioArticulo precioArticuloEncontrado= null;
        
        int seleccion = vista.getTablaArticulos().rowAtPoint(e.getPoint());
        vista.getJtf_ID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
        
        for (Articulo articulo : articulosCatalogo) {
            if (articulo.getId().toString().equals(vista.getJtf_ID().getText())) {
                articuloEncontrado = articulo;                
            }
        }
        
        if(!articulosprecios.isEmpty()){
            for (PrecioArticulo PreciosDeArticulo : modeloPrecioArticulo.findPrecioArticuloEntities()) {
                if(PreciosDeArticulo.getId_articulo().equals(articuloEncontrado.getId())){
                    precioArticuloEncontrado = PreciosDeArticulo;
                }
            }
        }
       
        
        System.out.println(precioArticuloEncontrado);
        
        vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion());
        
        //Setea JCBox de Categoria en articulo
        vista.getJcb_Categoria().removeAllItems();
        vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeArticulos().getDescripcion());

        //Setea JCBox de Proveedor en articulo
        vista.getJcb_Proveedor().removeAllItems();
        vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());
        
        if(precioArticuloEncontrado != null){
            vista.getJtf_precio().setText("");
            vista.getJtf_precio().setText(Float.toString(precioArticuloEncontrado.getPrecio()));
        }else{
            vista.getJtf_precio().setText("");
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
     * Modifica la habilitación de los JtextField de la vista en funcion al parametro de estado. 
     * @param estado de campos
     */
    public void inhabilitarTodosLosCampos(boolean estado){
        //inhabilita campos
        vista.habilitarCampo(estado, vista.getJtf_ID());
        vista.habilitarCampo(estado, vista.getJtf_Descripcion());        
        vista.habilitarCampo(estado, vista.getJtf_precio());
    }
    
     public void limpiarTodosLosCampos(){        
        vista.limpiarCampo(vista.getJtf_ID());
        vista.limpiarCampo(vista.getJtf_Descripcion());
        vista.limpiarCampo(vista.getJtf_precio());
        
        
        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_Categoria());
        vista.limpiarCombobox(vista.getJcb_ListaDePrecio());
        vista.limpiarCombobox(vista.getJcb_Proveedor());
        
    }
     
     
     /**
     * Modifica la habilitación de los Botones de la vista en funcion al parametro de estado.
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado){
        //Inhabilita Botones CRUD
        vista.habilitarBoton(estado, vista.getJbtn_Agregar());            
        vista.habilitarBoton(estado, vista.getJbtn_Modificar());

        //Inhabilita Botones Aceptar-Cancelar
        vista.habilitarBoton(estado, vista.getJbtn_Cancelar());
        vista.habilitarBoton(estado, vista.getJbtn_Aceptar());

        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());
    }
}
