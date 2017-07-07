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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
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
import model.JPAController.ListaDePrecioJpaController;
import model.JPAController.PagoJpaController;
import model.JPAController.PrecioArticuloJpaController;
import model.JPAController.StockArticuloJpaController;
import model.JPAController.TalonarioComprobanteJpaController;
import model.JPAController.UnidadJpaController;
import model.JPAController.VentaJpaController;
import model.ListaDePrecio;
import model.Pago;
import model.PrecioArticulo;
import model.StockArticulo;
import model.TalonarioComprobante;
import model.TipoCliente;
import model.Unidad;
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
    JDialogBuscarUsuario buscarUsuario = null;

    //modelos necesarios para el controlador.
    private TalonarioComprobanteJpaController modeloTalonarioComprobante;
    private ListaDePrecioJpaController modeloListaDePrecio;
    private UnidadJpaController modeloUnidad;
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
    Unidad unidad = null;
    TalonarioComprobante talonario = null;
    Deposito deposito = null;
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
    Cliente clienteSeleccionado = null;
    int numeroComprobanteActual = 0;
    Date fechaFactura = new Date();
    /**
     * Constructor CatalogoCategoriaArticuloController
     *
     * @param vista PanelRegistroCatalogoArticulo
     * @param modelo ArticuloJpaController
     */
    public VentaController(PanelRegistroVenta vista, VentaJpaController modelo) {
        
        //Instancias de modelos necesarios para el controlador.
        modeloTalonarioComprobante = new TalonarioComprobanteJpaController(Conexion.getEmf());
        modeloUnidad = new UnidadJpaController(Conexion.getEmf());
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

        inhabilitarTodoPanelVenta(false);
        vista.getJrb_Codigo().setEnabled(true);
        vista.getJrb_CuitDni().setEnabled(true);
        vista.getJrb_Nombre().setEnabled(true);
        vista.getJbtn_Buscar().setEnabled(true);
        vista.getJbtn_SalirSinGrabar().setEnabled(true);
        
    }

    
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        
        
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

        //Boton Salir Sin Grabar
        if (e.getSource() == vista.getJbtn_SalirSinGrabar()) {
            btn_salirSinGrabar();
        }
        
        //Boton Grabar E Imprimir
        if (e.getSource() == vista.getJbtn_GrabarEImprimir()) {
            btn_grabarEImprimir();
        }
        
        //Boton Grabar E Imprimir
        if(clienteSeleccionado!=null){
            if (e.getSource() == buscarUsuario.getJbtn_Agregar()) {
                try {
                    btn_agregarBuscarUsuario();
                } catch (IOException ex) {
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }

    public void btn_buscar(){
        clienteSeleccionado = null;
        buscarUsuario = new JDialogBuscarUsuario(null, true);
        buscarUsuario.setLocationRelativeTo(JframePrincipal.jPanelContenido);
        buscarUsuario.setResizable(false);
        buscarUsuario.setControlador(this);
        
        limpiarTodosLosCamposBusquedaUsuario();
        inhabilitarTodoBusquedaUsuario(false);
        
        limpiarTodosLosCamposPanelVenta();
        inhabilitarTodoPanelVenta(false);
        vista.getJbtn_Buscar().setEnabled(true);
        vista.getJrb_Codigo().setEnabled(true);
        vista.getJrb_CuitDni().setEnabled(true);
        vista.getJrb_Nombre().setEnabled(true);
        vista.getJbtn_SalirSinGrabar().setEnabled(true);
        
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
    
        
    public void btn_salirSinGrabar(){
        //Limpia campos
        limpiarTodosLosCamposPanelVenta();

        //inhabilitar Campos
        inhabilitarTodoPanelVenta(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotonesPanelVenta(false);

        
        //Habilita el Arbol de seleccion
        JframePrincipal.habilitarArbol(true);

                
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
            columna[2] = cli.getCuitCuil()
                    ;
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
    
    public void btn_agregarBuscarUsuario()throws IOException{
        if(clienteSeleccionado != null){
            buscarUsuario.dispose();
            
            //cargar datos en JPanel
            
            limpiarTodosLosCamposPanelVenta();
            vista.getJtf_ID().setText(clienteSeleccionado.getId().toString());
            vista.getJtf_cuitCuil().setText(clienteSeleccionado.getCuitCuil());
            vista.getJtf_Nombre().setText(clienteSeleccionado.getNombre());
            vista.getJtf_Apellido().setText(clienteSeleccionado.getApellido());

            //Si posee datos de direccion se cargan en la vista
            if (clienteSeleccionado.getDireccion() != null) {
                if (clienteSeleccionado.getDireccion().getLocalidad() != null) {


                    vista.getJcb_zona_direccion().removeAllItems();
                    vista.getJcb_zona_direccion().addItem(clienteSeleccionado.getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                    vista.getJcb_provincia_direccion().removeAllItems();
                    vista.getJcb_provincia_direccion().addItem(clienteSeleccionado.getDireccion().getLocalidad().getProvincia().getNombre());

                    vista.getJcb_localidad_direccion().removeAllItems();
                    vista.getJcb_localidad_direccion().addItem(clienteSeleccionado.getDireccion().getLocalidad().getNombre());

                    if (clienteSeleccionado.getTipocliente() != null) {
                        vista.getJcb_tipoCliente().removeAllItems();
                        vista.getJcb_tipoCliente().addItem(clienteSeleccionado.getTipocliente().getDescripcion());
                    }
                    if (clienteSeleccionado.getCuentaCorriente() != null) {
                        vista.getJtf_ctaCte().setText(Float.toString(clienteSeleccionado.getCuentaCorriente().getSaldo()));
                    } else {
                        vista.getJtf_ctaCte().setText("SIN CUENTA CORRIENTE INICIADA");
                    }

                    vista.getJtf_calle_direccion().setText(clienteSeleccionado.getDireccion().getCalle());
                    vista.getJtf_numero_direccion().setText(clienteSeleccionado.getDireccion().getNumero());
                    vista.getJtf_piso_direccion().setText(clienteSeleccionado.getDireccion().getPiso());
                    vista.getJtf_departamento_direccion().setText(clienteSeleccionado.getDireccion().getDepartamento());
                    
                    //carga Tipo de Comprobante
                    cargarTipoComprobante(clienteSeleccionado.getTipocliente());
                    
                    //Carga numeración actual del comprobante
                    cargarNumeroComprobanteActual(talonario);
                    
                    //Carga la unidad a la que pertenece el talonario
                    cargarUnidad(talonario);
                    
                    //Carga la fecha actual para la factura
                    cargarFechaActual();
                    
                    
                    llenarJcomboboxDeposito(unidad);
                    System.out.println(unidad.getNombre());

                } else {
                    JOptionPane.showMessageDialog(null, "cliente no tiene Localidad asignada");
                }

            } else {
                JOptionPane.showMessageDialog(null, "cliente no tiene Direccion asignada");
            }
        
            //cargarLista de Precio
            
            
        
        
        
        }else{
            System.out.println("no existe");
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
                limpiarTodosLosCamposBusquedaUsuario();
                buscarUsuario.getJbtn_Agregar().setEnabled(false);
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
        limpiarTodosLosCamposBusquedaUsuario();
        buscarUsuario.getJbtn_Agregar().setEnabled(true);
        //carga los datos en la vista si cualquiera de las variables es verdadera
        //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
        int seleccion = buscarUsuario.getTablaClientes().rowAtPoint(e.getPoint());
        buscarUsuario.getJtf_ID().setText(String.valueOf(buscarUsuario.getTablaClientes().getValueAt(seleccion, 1)));
        buscarUsuario.getJtf_cuitCuil().setText(String.valueOf(buscarUsuario.getTablaClientes().getValueAt(seleccion, 2)));
        buscarUsuario.getJtf_Nombre().setText(String.valueOf(buscarUsuario.getTablaClientes().getValueAt(seleccion, 3)));
        buscarUsuario.getJtf_Apellido().setText(String.valueOf(buscarUsuario.getTablaClientes().getValueAt(seleccion, 4)));

        //Si posee datos de direccion se cargan en la vista
        for (Cliente cliente : clientes) {
            if (cliente.getId().toString().equals(buscarUsuario.getJtf_ID().getText())) {
                if (cliente.getDireccion() != null) {
                    if (cliente.getDireccion().getLocalidad() != null) {
                        
                        clienteSeleccionado = cliente;
                        buscarUsuario.getJcb_zona_direccion().removeAllItems();
                        buscarUsuario.getJcb_zona_direccion().addItem(clienteSeleccionado.getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                        buscarUsuario.getJcb_provincia_direccion().removeAllItems();
                        buscarUsuario.getJcb_provincia_direccion().addItem(clienteSeleccionado.getDireccion().getLocalidad().getProvincia().getNombre());

                        buscarUsuario.getJcb_localidad_direccion().removeAllItems();
                        buscarUsuario.getJcb_localidad_direccion().addItem(clienteSeleccionado.getDireccion().getLocalidad().getNombre());

                        if (clienteSeleccionado.getTipocliente() != null) {
                            buscarUsuario.getJcb_tipoCliente().removeAllItems();
                            buscarUsuario.getJcb_tipoCliente().addItem(clienteSeleccionado.getTipocliente().getDescripcion());
                        }
                        if (clienteSeleccionado.getCuentaCorriente() != null) {
                            buscarUsuario.getJtf_ctaCte().setText(Float.toString(clienteSeleccionado.getCuentaCorriente().getSaldo()));
                        } else {
                            buscarUsuario.getJtf_ctaCte().setText("SIN CUENTA CORRIENTE INICIADA");
                        }

                        buscarUsuario.getJtf_calle_direccion().setText(clienteSeleccionado.getDireccion().getCalle());
                        buscarUsuario.getJtf_numero_direccion().setText(clienteSeleccionado.getDireccion().getNumero());
                        buscarUsuario.getJtf_piso_direccion().setText(clienteSeleccionado.getDireccion().getPiso());
                        buscarUsuario.getJtf_departamento_direccion().setText(clienteSeleccionado.getDireccion().getDepartamento());

                    } else {
                        JOptionPane.showMessageDialog(null, "cliente no tiene Localidad asignada");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "cliente no tiene Direccion asignada");
                }
            }
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == buscarUsuario.getJtf_BuscarCliente()) {
                limpiarTodosLosCamposBusquedaUsuario();
                buscarUsuario.getJbtn_Agregar().setEnabled(false);
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
    public void focusLost(FocusEvent e) {
        
    }
    
    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCamposPanelVenta() {
        //Limpia todos los JtextField
        vista.limpiarCampo(vista.getJtf_NumeroComprobante());
        vista.limpiarCampo(vista.getJtf_ID());
        vista.limpiarCampo(vista.getJtf_Nombre());
        vista.limpiarCampo(vista.getJtf_cuitCuil());
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

        vista.getJdc_fechaComprobante().setDate(null);
    }

    /**
     * Modifica la habilitación de los Botones de la vista en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotonesPanelVenta(boolean estado) {
        //Inhabilita Botones 
        vista.habilitarBoton(estado, vista.getJbtn_Buscar());
        vista.habilitarBoton(estado, vista.getJbtn_CargarItem());
        vista.habilitarBoton(estado, vista.getJbtn_ModificarItem());
        vista.habilitarBoton(estado, vista.getJbtn_EliminarItem());
        vista.habilitarBoton(estado, vista.getJbtn_SalirSinGrabar());
        vista.habilitarBoton(estado, vista.getJbtn_GrabarEImprimir());

        vista.getJdc_fechaComprobante().setEnabled(estado);
    }
    
    /**
     * Inhabilita todos los componentes del panel de Registro de Venta
     * @param estado 
     */
    public void inhabilitarTodoPanelVenta(boolean estado){
    //Limpia todos los JtextField
        vista.getJtf_NumeroComprobante().setEnabled(estado);
        vista.getJtf_ID().setEnabled(estado);
        vista.getJtf_Nombre().setEnabled(estado);
        vista.getJtf_cuitCuil().setEnabled(estado);
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
        vista.getJbtn_SalirSinGrabar().setEnabled(estado);
        vista.getJbtn_GrabarEImprimir().setEnabled(estado);
        
        vista.getJrb_Codigo().setEnabled(estado);
        vista.getJrb_CuitDni().setEnabled(estado);
        vista.getJrb_Nombre().setEnabled(estado);
        
        vista.getJdc_fechaComprobante().setEnabled(estado);
    }

    /**
     * Establece la politica de datos que contendran los elemmentos de la vista.
     */
    public void politicaValidacionDeCampos() {
        
    }
    
    
    
    //Buscar Usuario
     /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCamposBusquedaUsuario() {
        //Limpia todos los JtextField
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_ID());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_Nombre());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_cuitCuil());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_Apellido());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_ctaCte());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_calle_direccion());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_numero_direccion());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_piso_direccion());
        buscarUsuario.limpiarCampo(buscarUsuario.getJtf_departamento_direccion());

        //Limpia todos los JComboBox
        buscarUsuario.limpiarCombobox(buscarUsuario.getJcb_tipoCliente());
        buscarUsuario.limpiarCombobox(buscarUsuario.getJcb_zona_direccion());
        buscarUsuario.limpiarCombobox(buscarUsuario.getJcb_provincia_direccion());
        buscarUsuario.limpiarCombobox(buscarUsuario.getJcb_localidad_direccion());

    }

    /**
     * Modifica la habilitación de los Botones de la vista en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotonesBusquedaUsuario(boolean estado) {
        //Inhabilita Botones 
        buscarUsuario.habilitarBoton(estado, buscarUsuario.getJbtn_Agregar());

    }
    
    /**
     * Inhabilita todos los componentes del panel de Registro de Venta
     * @param estado 
     */
    public void inhabilitarTodoBusquedaUsuario(boolean estado){
    //Limpia todos los JtextField
        buscarUsuario.getJtf_ID().setEnabled(estado);
        buscarUsuario.getJtf_Nombre().setEnabled(estado);
        buscarUsuario.getJtf_cuitCuil().setEnabled(estado);
        buscarUsuario.getJtf_Apellido().setEnabled(estado);
        buscarUsuario.getJtf_ctaCte().setEnabled(estado);
        buscarUsuario.getJtf_calle_direccion().setEnabled(estado);
        buscarUsuario.getJtf_numero_direccion().setEnabled(estado);
        buscarUsuario.getJtf_piso_direccion().setEnabled(estado);
        buscarUsuario.getJtf_departamento_direccion().setEnabled(estado);

        //Limpia todos los JComboBox
        buscarUsuario.getJcb_tipoCliente().setEnabled(estado);
        buscarUsuario.getJcb_zona_direccion().setEnabled(estado);
        buscarUsuario.getJcb_provincia_direccion().setEnabled(estado);
        buscarUsuario.getJcb_localidad_direccion().setEnabled(estado);
        
        //Inhabilita Botones 
        buscarUsuario.getJbtn_Agregar().setEnabled(estado);
    }

    /**
     * Carga el talonario correspondiente al cliente
     * @param unTipoCliente
     * @param unaUnidad 
     */
    public void cargarTipoComprobante(TipoCliente unTipoCliente){
        for (TalonarioComprobante lp : modeloTalonarioComprobante.findTalonarioComprobanteEntities()) {
            if(lp.getId_TipoCliente().equals(unTipoCliente.getId())){
                talonario = lp;
                vista.getJcb_Comprobante().removeAllItems();
                vista.getJcb_Comprobante().addItem(talonario.getDescripcion());
            }
        }
    }
    
    /**
     * Carga el numero de comprobante actual
     * @param unTalonario 
     */
    public void cargarNumeroComprobanteActual(TalonarioComprobante unTalonario){
        numeroComprobanteActual = unTalonario.getNumeracion_Actual()+1;
        vista.getJtf_NumeroComprobante().setText(Integer.toString(unTalonario.getNumeracion_Actual()+1));
    }
    
    public void cargarUnidad(TalonarioComprobante unTalonario){
        for (Unidad u : modeloUnidad.findUnidadEntities()) {
            if(u.getId().equals(unTalonario.getId_Unidad())){
                unidad = u;
                vista.getJcb_Unidad().removeAllItems();
                vista.getJcb_Unidad().addItem(unidad.getNombre());
            }
        }
    }
    
    public void cargarFechaActual(){
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        vista.getJdc_fechaComprobante().setDate(fechaFactura);
        formatoFecha.format(fechaFactura);
        
    }
    
    public void llenarJcomboboxDeposito(Unidad unaUnidad) {
        vista.getJcb_Deposito().setEnabled(true);
        vista.getJcb_Deposito().removeAllItems();
        modeloDeposito = new DepositoJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloDeposito.buscarDepositosPorUnidad(unidad));
        vista.getJcb_Deposito().setModel(mdl);
    }
}
