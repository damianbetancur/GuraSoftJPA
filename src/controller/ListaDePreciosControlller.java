package controller;

import java.awt.Color;
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
    
    private boolean aceptar_agregar;
    private boolean aceptar_modificar;
   
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
    
    //Articulo Encontrado
    Articulo articuloEncontrado;
    PrecioArticulo precioArticuloEncontrado;
    

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
        
        //Limpia campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);

        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
        
        
        llenarJcomboboxListaDePrecio();        
        
        llenarTabla(vista.getTablaArticulos(), this.lpBuscada);
            
        cargarArticuloInicio();
        
        //habilita el boton volver
        vista.getJbtn_Volver().setEnabled(true);
        
        vista.getJbtn_Aceptar().setVisible(false);
        vista.getJbtn_Cancelar().setVisible(false);
      
        aceptar_modificar = false;
        aceptar_agregar = false;
    }
    
    /**
     * ActionPerformed
     * Controla los eventos que suceden en la vista al presionar los Botones de CRUD
     * @param e  recepcion de Evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        
      
         //Boton Agregar
        if (e.getSource()==vista.getJbtn_Agregar()){
            btn_agregar();
            aceptar_agregar = true;
            aceptar_modificar = false;
        }
        
         //Boton Cancelar
        if (e.getSource()==vista.getJbtn_Cancelar()){
            btn_cancelar();
            aceptar_modificar = false;
            aceptar_agregar = false;
        }
        
         //Boton Modificar
        if (e.getSource()==vista.getJbtn_Modificar()){
            btn_modificar();
            aceptar_agregar = false;
            aceptar_modificar = true;            
        }
        
         //Boton Volver
        if (e.getSource()==vista.getJbtn_Volver()){
            btn_volver();
            aceptar_modificar = false;
            aceptar_agregar = false;
        }
        
        //Boton Aceptar
        if (e.getSource()==vista.getJbtn_Aceptar()){
            try {
                btn_aceptar();
                aceptar_modificar = false;
                aceptar_agregar = false;
            } catch (Exception ex) {
                Logger.getLogger(ListaDePreciosControlller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Boton agregar habilita el precio 
     */
    public void btn_agregar(){
        
        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        
        
        vista.getJbtn_Aceptar().setVisible(true);
        vista.getJbtn_Cancelar().setVisible(true);

        //Limpiar Campos
        limpiarTodosLosCampos();

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);
        vista.habilitarCampo(false, vista.getJtf_precio());

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        
        
        
        //Articulo articuloEncontrado;
        //PrecioArticulo precioArticuloEncontrado;
        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
        
        if(articuloEncontrado != null){
            vista.getJtf_ID().setText(articuloEncontrado.getId().toString());
            vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion().toString());

            //Setea JCBox de Categoria en articulo
            vista.getJcb_Categoria().removeAllItems();
            vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeCatalogo().getDescripcion());

            //Setea JCBox de Proveedor en articulo
            vista.getJcb_Proveedor().removeAllItems();
            vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial()); 
            
            
            if(precioArticuloEncontrado == null){
                vista.getJtf_precio().setText("");
                vista.getJtf_precio().setBackground(Color.GREEN);
                vista.getJtf_precio().setEnabled(true);
                vista.getJtf_precio().setEditable(true);
            }
        }
    }
    
    public void btn_modificar(){
        
        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        
        
        vista.getJbtn_Aceptar().setVisible(true);
        vista.getJbtn_Cancelar().setVisible(true);

        //Limpiar Campos
        limpiarTodosLosCampos();

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);
        vista.habilitarCampo(false, vista.getJtf_precio());

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        
        
        
        //Articulo articuloEncontrado;
        //PrecioArticulo precioArticuloEncontrado;
        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
        
        if(articuloEncontrado != null){
            vista.getJtf_ID().setText(articuloEncontrado.getId().toString());
            vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion().toString());

            //Setea JCBox de Categoria en articulo
            vista.getJcb_Categoria().removeAllItems();
            vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeCatalogo().getDescripcion());

            //Setea JCBox de Proveedor en articulo
            vista.getJcb_Proveedor().removeAllItems();
            vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial()); 
            
            
            if(precioArticuloEncontrado != null){
                vista.getJtf_precio().setText(Float.toString(precioArticuloEncontrado.getPrecio()));
                vista.getJtf_precio().setBackground(Color.GREEN);
                vista.getJtf_precio().setEnabled(true);
                vista.getJtf_precio().setEditable(true);
            }
        }
    }
    
    
    public void btn_aceptar() throws Exception{
        PrecioArticulo precioArticuloNuevo = new PrecioArticulo();
        modelo = new PrecioArticuloJpaController(Conexion.getEmf());
        
        if(articuloEncontrado != null){            
            
            //No existe precio en Articulo
            if(precioArticuloEncontrado == null){
                
                precioArticuloNuevo.setId_articulo(articuloEncontrado.getId());
                precioArticuloNuevo.setId_listaDePrecio(lpBuscada.getId());
                
                if(!vista.getJtf_precio().getText().isEmpty()){
                    precioArticuloNuevo.setPrecio(Float.parseFloat(vista.getJtf_precio().getText()));
                    
                    if(aceptar_agregar == true){
                        //Persiste Precio Articulo
                        modelo.create(precioArticuloNuevo);
                        JOptionPane.showMessageDialog(null, "Precio Creado");
                    }
                    
                }else{
                    precioArticuloNuevo.setPrecio(0F);
                }
            }//Existe precio de Articulo
            else{
                precioArticuloNuevo.setId_articulo(articuloEncontrado.getId());
                precioArticuloNuevo.setId_listaDePrecio(lpBuscada.getId());
                
                if(!vista.getJtf_precio().getText().isEmpty()){
                    precioArticuloNuevo.setPrecio(Float.parseFloat(vista.getJtf_precio().getText()));
                    
                    if(aceptar_modificar == true){
                        //Persiste Precio Articulo
                        modelo.edit(precioArticuloNuevo);
                        JOptionPane.showMessageDialog(null, "Precio modificado");
                    }
                    
                }else{
                    precioArticuloNuevo.setPrecio(0F);
                }
            }
        }
        btn_cancelar();
    }
    
    
    /**
     * cancela las operaciones
     */
    public void btn_cancelar(){
        //Limpia campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);

        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
        
        
        llenarJcomboboxListaDePrecio();        
        
        llenarTabla(vista.getTablaArticulos(), this.lpBuscada);
            
        cargarArticuloInicio();
        
        //habilita el boton volver
        vista.getJbtn_Volver().setEnabled(true);
        
        vista.getJbtn_Aceptar().setVisible(false);
        vista.getJbtn_Cancelar().setVisible(false);
               
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
        
        //inhabilita bo
        vista.getJcb_ListaDePrecio().setEnabled(false);
        
        vista.getJtf_precio().setBackground(null);
    }
    
    
    
    /**
     * Llena Jtable de cliente
 crea una lista de PreciosDeArticulos existentes en la base de datos. 
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD, ListaDePrecio lpBuscada){ 
        
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
        
        
        cargarArticulosEnArray(columna,  modeloT);
       
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
            //lista de precio Buscada lpBuscada toma el valor del jcombobox --> Jcb_ListaDePrecio
            this.lpBuscada = (ListaDePrecio) vista.getJcb_ListaDePrecio().getSelectedItem();
    }
    
    
    
    /**
     * Verifica el cambio de estado en los JComboBox de Lista de Precio
     * @param e Evento de cambio de JCOMBOBOX
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        
        
        if (e.getStateChange() == ItemEvent.SELECTED) {
            articuloEncontrado = null;
            precioArticuloEncontrado = null;
            
            // cada vez que se registra un cambio en Jcb_ListaDePrecio se almacena en lpBuscada
            this.lpBuscada = (ListaDePrecio) vista.getJcb_ListaDePrecio().getSelectedItem();
            
            llenarTabla(vista.getTablaArticulos(), this.lpBuscada);
            
            if(!articulosCatalogo.isEmpty()){
                //posiciona en foco de la lista en el Articulo 
                vista.getTablaArticulos().changeSelection(0, 1, false, false);                 
                
                //Posiciona el cursor en el 1er elemento de la Tabla de Articulos
                int seleccion = 0;
                vista.getJtf_ID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
                
                
                for (Articulo articulo : articulosCatalogo) {
                    if (articulo.getId().toString().equals(vista.getJtf_ID().getText())) {
                        articuloEncontrado = articulo;
                        precioArticuloEncontrado = modeloPrecioArticulo.buscarPrecioDeArticuloConListaDePrecio(articulo, lpBuscada);
                        
                    }
                }
                
                
                
                vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion());

                //Setea JCBox de Categoria en articulo
                vista.getJcb_Categoria().removeAllItems();
                vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeCatalogo().getDescripcion());

                //Setea JCBox de Proveedor en articulo
                vista.getJcb_Proveedor().removeAllItems();
                vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());
                
                
                
                if(precioArticuloEncontrado != null){
                    vista.getJtf_precio().setText("");
                    vista.getJtf_precio().setText(Float.toString(precioArticuloEncontrado.getPrecio()));
                    vista.getJtf_precio().setBackground(Color.GREEN);
                    vista.getJbtn_Modificar().setEnabled(true);
                    vista.getJbtn_Agregar().setEnabled(false);
                }else{
                    vista.getJtf_precio().setText("Sin Precio Asignado");                    
                    vista.getJtf_precio().setBackground(Color.RED);
                    vista.getJbtn_Agregar().setEnabled(true);
                    vista.getJbtn_Modificar().setEnabled(false);
                }
                
                
                
            }else{
                JOptionPane.showMessageDialog(null, "Lista Vacia");
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
    public void mouseClicked(MouseEvent e) 
    {
        this.lpBuscada = (ListaDePrecio) vista.getJcb_ListaDePrecio().getSelectedItem();
        
        this.articuloEncontrado = null;
        this.precioArticuloEncontrado= null;
        
        int seleccion = vista.getTablaArticulos().rowAtPoint(e.getPoint());
        vista.getJtf_ID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
        
        
        for (Articulo articulo : articulosCatalogo) {
            if (articulo.getId().toString().equals(vista.getJtf_ID().getText())) {
                articuloEncontrado = articulo;
                precioArticuloEncontrado = modeloPrecioArticulo.buscarPrecioDeArticuloConListaDePrecio(articuloEncontrado, lpBuscada);
            }
        }
        
        
        vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion());
        
        //Setea JCBox de Categoria en articulo
        vista.getJcb_Categoria().removeAllItems();
        vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeCatalogo().getDescripcion());

        //Setea JCBox de Proveedor en articulo
        vista.getJcb_Proveedor().removeAllItems();
        vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());
        
        
        if(precioArticuloEncontrado != null){
            vista.getJtf_precio().setText("");
            vista.getJtf_precio().setText(Float.toString(precioArticuloEncontrado.getPrecio()));
            vista.getJtf_precio().setBackground(Color.GREEN);
            vista.getJbtn_Modificar().setEnabled(true);
            vista.getJbtn_Agregar().setEnabled(false);
        }else{
            vista.getJtf_precio().setText("Sin Precio Asignado");                    
            vista.getJtf_precio().setBackground(Color.RED);
            vista.getJbtn_Agregar().setEnabled(true);
            vista.getJbtn_Modificar().setEnabled(false);
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
    
    
    
    /**
     * Carga los articulos en array para poder ser usados en la vista
     * @param columna
     * @param modeloT 
     */
    public void cargarArticulosEnArray(Object [] columna, DefaultTableModel modeloT){
        int numero = 0;
        boolean agregado = false;
        articulosCatalogo = null;
        articulosprecios = null;
        
        PrecioArticulo precioArticulo = null;
        
        articulosCatalogo = new ArrayList<Articulo>();
        articulosprecios = new ArrayList<PrecioArticulo>();
        
        if(lpBuscada!=null){
            for (Articulo articuloBuscado : modeloArticuloCatalogo.findArticuloEntities()) {

                precioArticulo = modeloPrecioArticulo.buscarPrecioDeArticuloConListaDePrecio(articuloBuscado, lpBuscada);

                if(precioArticulo != null){
                    if (agregado == false) {
                        articulosprecios.add(precioArticulo);

                         //Guarda en Lista de articulos  
                        articulosCatalogo.add(articuloBuscado);
                        numero = numero + 1;
                        columna[0] = String.valueOf(numero);   
                        columna[1] = articuloBuscado.getId();            
                        columna[2] = articuloBuscado.getDescripcion();   

                        modeloT.addRow(columna);
                        //tiene precio
                        agregado = true;

                    }
                    if(agregado==false){
                        articulosCatalogo.add(articuloBuscado);
                        numero = numero + 1;
                        columna[0] = String.valueOf(numero);   
                        columna[1] = articuloBuscado.getId();            
                        columna[2] = articuloBuscado.getDescripcion();   

                        modeloT.addRow(columna);
                        agregado = true;
                    }

                }else{
                    articulosCatalogo.add(articuloBuscado);
                    numero = numero + 1;
                    columna[0] = String.valueOf(numero);   
                    columna[1] = articuloBuscado.getId();            
                    columna[2] = articuloBuscado.getDescripcion();   
                    modeloT.addRow(columna);
                }
                agregado = false;
            }
        }else{
            JOptionPane.showMessageDialog(null, "No existen listas de precios");
        }
        
    }
    
    /**
     * Carga los articulos si hay elementos en el catalo, posicionandose en el 1 er elemento y mostrandolo
     */
    public void cargarArticuloInicio(){
        if(!articulosCatalogo.isEmpty()){
                
                //posiciona en foco de la lista en el Articulo 
                vista.getTablaArticulos().changeSelection(0, 1, false, false); 
                
                this.articuloEncontrado = null;
                this.precioArticuloEncontrado = null;
                
                int seleccion = 0;
                vista.getJtf_ID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));
                
                
                
                for (Articulo articulo : articulosCatalogo) {
                    if (articulo.getId().toString().equals(vista.getJtf_ID().getText())) {
                        articuloEncontrado = articulo;
                        precioArticuloEncontrado = modeloPrecioArticulo.buscarPrecioDeArticuloConListaDePrecio(articulo, lpBuscada);
                        
                    }
                }
                vista.getJtf_Descripcion().setText(articuloEncontrado.getDescripcion());

                //Setea JCBox de Categoria en articulo
                vista.getJcb_Categoria().removeAllItems();
                vista.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeCatalogo().getDescripcion());

                //Setea JCBox de Proveedor en articulo
                vista.getJcb_Proveedor().removeAllItems();
                vista.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());
                
                
                if(precioArticuloEncontrado != null){
                    vista.getJtf_precio().setText("");
                    vista.getJtf_precio().setText(Float.toString(precioArticuloEncontrado.getPrecio()));
                    vista.getJtf_precio().setBackground(Color.GREEN);
                }else{
                    vista.getJtf_precio().setText("Sin Precio Asignado");                    
                    vista.getJtf_precio().setBackground(Color.RED);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Lista Vacia");
            }
    
    }
    
    
    public void politicaValidacionDeCampos(){
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtf_Descripcion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_Descripcion(), 30);

        precioSoloFloat();
        vista.getValidador().LimitarCaracteres(vista.getJtf_precio(), 8);
                
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
    
    public void precioSoloFloat(){
        vista.getValidador().validarNumeroDecimales(vista.getJtf_precio());
    } 
    
    
    
    public int sizeTabla(){
        int posicion =0;
        for (PrecioArticulo precio : modelo.findPrecioArticuloEntities()) {            
            posicion++;
        }        
        return posicion-1;
    }  
}
