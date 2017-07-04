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
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.Articulo;
import model.Cliente;
import model.Comprobante;
import model.CuentaCorriente;
import model.Deposito;
import model.Empleado;
import model.JPAController.ArticuloJpaController;
import model.JPAController.ClienteJpaController;
import model.JPAController.ComprobanteJpaController;
import model.JPAController.CuentaCorrienteJpaController;
import model.JPAController.DepositoJpaController;
import model.JPAController.EmpleadoJpaController;
import model.JPAController.PagoJpaController;
import model.JPAController.PrecioArticuloJpaController;
import model.JPAController.StockArticuloJpaController;
import model.JPAController.VentaJpaController;
import model.Pago;
import model.PrecioArticulo;
import model.StockArticulo;
import view.JDialogBuscarUsuario;
import view.JframePrincipal;
import view.PanelRegistroVenta;

/**
 *
 * @author Ariel
 */
public class VentaController extends Controller{
    
    private PanelRegistroVenta vista;
    private VentaJpaController modelo;

    //Dialog dependientes
    JDialogBuscarUsuario buscarUsuario;

    //modelos necesarios para el controlador.
    private DepositoJpaController modeloDeposito;
    private ComprobanteJpaController modeloComprobante;
    private PagoJpaController modeloPago;
    private EmpleadoJpaController modeloEmpleado;
    private ClienteJpaController modeloCliente;
    private CuentaCorrienteJpaController modeloCuentaCorriente;
    
    private ArticuloJpaController modeloArticuloCatalogo;
    private StockArticuloJpaController modeloStockArticulo;
    private PrecioArticuloJpaController modeloPrecioArticulo;

    //Entidades necesarias para el controlador    
    Deposito depositoBuscado = null;
    Comprobante comprobante = null;
    Pago pago = null;
    Empleado empleado = null;
    Cliente cliente = null;
    CuentaCorriente cuentaCorrienteCliente= null;

    List<Articulo> articulosCatalogo;
    List<StockArticulo> articulosStocks;
    List<PrecioArticulo> preciosArticulos;

    
    //Articulo Encontrado
    Articulo articuloEncontrado;
    StockArticulo stockArticuloEncontrado;
    PrecioArticulo precioArticuloEncontrado;

    //Variables
    List<Cliente> clientes;
    
    
    /**
     * Constructor CatalogoCategoriaArticuloController
     *
     * @param vista PanelRegistroCatalogoArticulo
     * @param modelo ArticuloJpaController
     */
    public VentaController(PanelRegistroVenta vista, VentaJpaController modelo) {
        
        //Instancias de modelos necesarios para el controlador.
        modeloDeposito = new DepositoJpaController(Conexion.getEmf());
        modeloComprobante = new ComprobanteJpaController(Conexion.getEmf());
        modeloPago = new PagoJpaController(Conexion.getEmf());
        modeloEmpleado = new EmpleadoJpaController(Conexion.getEmf());
        modeloCliente = new ClienteJpaController(Conexion.getEmf());
        modeloCuentaCorriente = new CuentaCorrienteJpaController(Conexion.getEmf());

        modeloArticuloCatalogo = new ArticuloJpaController(Conexion.getEmf());
        modeloStockArticulo = new StockArticuloJpaController(Conexion.getEmf());
        modeloPrecioArticulo = new PrecioArticuloJpaController(Conexion.getEmf());
        

        this.vista = vista;
        this.modelo = modelo;

        
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
         //Boton Buscar
        if (e.getSource() == vista.getJbtn_Buscar()) {
            btn_buscar();
        }

        //Boton Cargar Item
        if (e.getSource() == vista.getJbtn_CargarItem()) {
            btn_cargarItem();
        }

        //Boton Modificar Item
        if (e.getSource() == vista.getJbtn_ModificarItem()) {
            btn_modificarItem();
        }

        //Boton Eliminar Item
        if (e.getSource() == vista.getJbtn_EliminarItem()) {
            btn_eliminarItem();
        }

        //Boton Volver
        if (e.getSource() == vista.getJbtn_Volver()) {
            btn_volver();
        }

        //Boton Salir Sin Grabar
        if (e.getSource() == vista.getJbtn_SalirSinGrabar()) {
            btn_salirSinGrabar();
        }
        
        //Boton Grabar E Imprimir
        if (e.getSource() == vista.getJbtn_GrabarEImprimir()) {
            btn_grabarEImprimir();
        }
        
        
        
    }

    public void btn_buscar(){
        buscarUsuario = new JDialogBuscarUsuario(null, true);
        buscarUsuario.setLocationRelativeTo(JframePrincipal.jPanelContenido);
        buscarUsuario.setResizable(false);
        buscarUsuario.setControlador(this);
        
        
        if(vista.getJrb_Nombre().isSelected()){
            buscarUsuario.getJlbl_TituloBusqueda().setText("Nombre Cliente: ");
        }
        if(vista.getJrb_Codigo().isSelected()){
            buscarUsuario.getJlbl_TituloBusqueda().setText("Codigo Cliente: ");
        }
        if(vista.getJrb_CuitDni().isSelected()){
            buscarUsuario.getJlbl_TituloBusqueda().setText("CUIT-DNI Cliente: ");
        }
        buscarUsuario.setVisible(true);
    }
        
    public void btn_cargarItem(){        
    }
    
    public void btn_modificarItem(){        
    }
            
    public void btn_eliminarItem(){        
    }
    
    public void btn_volver(){        
    }
        
    public void btn_salirSinGrabar(){        
    }
    
    public void btn_grabarEImprimir(){        
    }
    
    //Controlador del JDialog BuscarCliente
    
    public void llenarTabla(JTable tablaD, String datoABuscar, int opcion) {
        clientes = new ArrayList<Cliente>();
        //Celdas no editables
        DefaultTableModel modeloT = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        //Inmovilizar Columnas
        tablaD.getTableHeader().setReorderingAllowed(false);

        //Inhabilitar redimension de columnas
        tablaD.getTableHeader().setResizingAllowed(false);

        //Permite Seleccionar solamente una fila
        tablaD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaD.setModel(modeloT);

        //Setea las cabeceras de la tabla
        modeloT.addColumn("N°");
        modeloT.addColumn("ID");
        modeloT.addColumn("CUIT-CUIL");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");

        //Cantidad de columnas 
        Object[] columna = new Object[6];

        int numero = 0;

        for (Cliente cli : modeloCliente.findClienteEntities()) {
            
            if(cli.getApellido().toString().indexOf(datoABuscar) != -1 && opcion == 1){
                //Guarda en Lista de clientes  
            clientes.add(cli);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);
            columna[1] = cli.getId();
            columna[2] = cli.getCuitCuil();
            columna[3] = cli.getNombre();
            columna[4] = cli.getApellido();
            modeloT.addRow(columna);
            }
            
            if(cli.getId().toString().indexOf(datoABuscar) != -1 && opcion == 2){
                //Guarda en Lista de clientes  
            clientes.add(cli);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);
            columna[1] = cli.getId();
            columna[2] = cli.getCuitCuil();
            columna[3] = cli.getNombre();
            columna[4] = cli.getApellido();
            modeloT.addRow(columna);
            }
            
            if(cli.getCuitCuil().toString().indexOf(datoABuscar) != -1 && opcion == 3){
                //Guarda en Lista de clientes  
            clientes.add(cli);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);
            columna[1] = cli.getId();
            columna[2] = cli.getCuitCuil();
            columna[3] = cli.getNombre();
            columna[4] = cli.getApellido();
            modeloT.addRow(columna);
            }
            
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
         
        if (e.getSource() == buscarUsuario.getJtf_BuscarCliente()) {
           
                if(vista.getJrb_Nombre().isSelected()){
                    llenarTabla(buscarUsuario.getTablaClientes(), buscarUsuario.getJtf_BuscarCliente().getText(), 1);
                }
                if(vista.getJrb_Codigo().isSelected()){
                    llenarTabla(buscarUsuario.getTablaClientes(), buscarUsuario.getJtf_BuscarCliente().getText(), 2);
                }
                if(vista.getJrb_CuitDni().isSelected()){
                    llenarTabla(buscarUsuario.getTablaClientes(), buscarUsuario.getJtf_BuscarCliente().getText(), 3);
                }
        }
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
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }
    
    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos() {
        //Limpia todos los JtextField
        vista.limpiarCampo(vista.getJtf_NumeroComprobante());
        vista.limpiarCampo(vista.getJtf_ID());
        vista.limpiarCampo(vista.getJtf_Nombre());
        vista.limpiarCampo(vista.getJtf_DNI());
        vista.limpiarCampo(vista.getJtf_Apellido());
        vista.limpiarCampo(vista.getJtf_ctaCte());
        vista.limpiarCampo(vista.getJtf_calle_direccion());
        vista.limpiarCampo(vista.getJtf_numero_direccion());
        vista.limpiarCampo(vista.getJtf_piso_direccion());
        vista.limpiarCampo(vista.getJtf_departamento_direccion());

        //Limpia todos los JComboBox
        vista.limpiarCombobox(vista.getJcb_Unidad());
        vista.limpiarCombobox(vista.getJcb_Comprobante());
        vista.limpiarCombobox(vista.getJcb_tipoCliente());
        vista.limpiarCombobox(vista.getJcb_zona_direccion());
        vista.limpiarCombobox(vista.getJcb_provincia_direccion());
        vista.limpiarCombobox(vista.getJcb_localidad_direccion());
        vista.limpiarCombobox(vista.getJcb_Deposito());

    }

    /**
     * Modifica la habilitación de los Botones de la vista en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado) {
        //Inhabilita Botones 
        vista.habilitarBoton(estado, vista.getJbtn_Buscar());
        vista.habilitarBoton(estado, vista.getJbtn_CargarItem());
        vista.habilitarBoton(estado, vista.getJbtn_ModificarItem());
        vista.habilitarBoton(estado, vista.getJbtn_EliminarItem());
        vista.habilitarBoton(estado, vista.getJbtn_Volver());
        vista.habilitarBoton(estado, vista.getJbtn_SalirSinGrabar());
        vista.habilitarBoton(estado, vista.getJbtn_GrabarEImprimir());

    }
    
    public void inhabilitarTodo(boolean estado){
    //Limpia todos los JtextField
        vista.getJtf_NumeroComprobante().setEnabled(estado);
        vista.getJtf_ID().setEnabled(estado);
        vista.getJtf_Nombre().setEnabled(estado);
        vista.getJtf_DNI().setEnabled(estado);
        vista.getJtf_Apellido().setEnabled(estado);
        vista.getJtf_ctaCte().setEnabled(estado);
        vista.getJtf_calle_direccion().setEnabled(estado);
        vista.getJtf_numero_direccion().setEnabled(estado);
        vista.getJtf_piso_direccion().setEnabled(estado);
        vista.getJtf_departamento_direccion().setEnabled(estado);

        //Limpia todos los JComboBox
        vista.getJcb_Unidad().setEnabled(estado);
        vista.getJcb_Comprobante().setEnabled(estado);
        vista.getJcb_tipoCliente().setEnabled(estado);
        vista.getJcb_zona_direccion().setEnabled(estado);
        vista.getJcb_provincia_direccion().setEnabled(estado);
        vista.getJcb_localidad_direccion().setEnabled(estado);
        vista.getJcb_Deposito().setEnabled(estado);
        
        //Inhabilita Botones 
        vista.getJbtn_Buscar().setEnabled(estado);
        vista.getJbtn_CargarItem().setEnabled(estado);
        vista.getJbtn_ModificarItem().setEnabled(estado);
        vista.getJbtn_EliminarItem().setEnabled(estado);
        vista.getJbtn_Volver().setEnabled(estado);
        vista.getJbtn_SalirSinGrabar().setEnabled(estado);
        vista.getJbtn_GrabarEImprimir().setEnabled(estado);
        
        vista.getJrb_Codigo().setEnabled(estado);
        vista.getJrb_CuitDni().setEnabled(estado);
        vista.getJrb_Nombre().setEnabled(estado);
    }

    /**
     * Establece la politica de datos que contendran los elemmentos de la vista.
     */
    public void politicaValidacionDeCampos() {
        
    }
    
}
