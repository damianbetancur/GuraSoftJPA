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
    
    ListaDePrecio lpBuscada = null;
    boolean lpSeleccionada = false;
    
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
        
      
    }
    
    /**
     * ActionPerformed
     * Controla los eventos que suceden en la vista al presionar los Botones de CRUD
     * @param e  recepcion de Evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {        
        
      
        
    }


    
   
    
    /**
     * Controla el boton Listar
     * llena la tabla de la vista
     * si la tabla es mayor que 0 (cero), completa los JtextFields con el primer objeto de la tabla
     * si la tabla esta avcia no muestra nada
     */
    public void listarArticulosDeListaDePrecios(){
          
        
    }
    
    
    /**
     * Controla el Boton Cancelar
     * Bloquea el Boton AceptarCrear para poder crear
     * Bloquea el Boton AceptarEliminar para poder eliminar
     * Bloquea el Boton AceptarModificar para poder modificar
     */
    public void btn_cancelar(){
       
        
    }
    
    public void btn_aceptar(){
   
        
    }
    
    
    /**
     * Controla el Boton Volver
     * Bloquea el Boton AceptarCrear para poder crear
     * Bloquea el Boton AceptarEliminar para poder eliminar
     * Bloquea el Boton AceptarModificar para poder modificar
     * Habilita el Arbol del Panel Principal
     */
    public void btn_volver(){
       
        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);

        
        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);
        
     
    }
    
    
    
    /**
     * Llena Jtable de cliente
 crea una lista de PreciosDeArticulos existentes en la base de datos. 
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD, ListaDePrecio lpBuscada){       
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
     * Modifica la habilitación de los Botones de la vista en funcion al parametro de estado.
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado){
        
       
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
    

    
    
    public void buscarListaDePrecioDeArticulo(){
    
    
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
        if (!Character.isDigit(e.getKeyChar())&&e.getKeyChar()!='.') {
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
