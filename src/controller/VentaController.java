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
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import model.Articulo;
import model.Cliente;
import model.Comprobante;
import model.CuentaCorriente;
import model.Deposito;
import model.Empleado;
import model.FijarPrecioVentaMayorista;
import model.FijarPrecioVentaMinorista;
import model.IEstrategiaFijarPreciosVenta;
import model.JPAController.ArticuloJpaController;
import model.JPAController.ClienteJpaController;
import model.JPAController.ComprobanteJpaController;
import model.JPAController.CuentaCorrienteJpaController;
import model.JPAController.DepositoJpaController;
import model.JPAController.EmpleadoJpaController;
import model.JPAController.LineaDeVentaJpaController;
import model.JPAController.ListaDePrecioJpaController;
import model.JPAController.PagoJpaController;
import model.JPAController.PrecioArticuloJpaController;
import model.JPAController.StockArticuloJpaController;
import model.JPAController.TalonarioComprobanteJpaController;
import model.JPAController.TipoClienteJpaController;
import model.JPAController.UnidadJpaController;
import model.JPAController.UsuarioJpaController;
import model.JPAController.VentaJpaController;
import model.LineaDeVenta;
import model.ListaDePrecio;
import model.Pago;
import model.PrecioArticulo;
import model.StockArticulo;
import model.TalonarioComprobante;
import model.TipoCliente;
import model.TipoUsuario;
import model.Unidad;
import model.Usuario;
import model.Venta;
import view.JDialogBuscarArticulo;
import view.JDialogBuscarDeposito;
import view.JDialogBuscarCliente;
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
    JDialogBuscarCliente buscarCliente = null;
    JDialogBuscarDeposito buscarDeposito = null;
    JDialogBuscarArticulo buscarArticulo = null;

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
    private TipoClienteJpaController modeloTipoCliente;
    private UsuarioJpaController modeloUsuario;
    private LineaDeVentaJpaController modeloLineaDeVenta;

    private ArticuloJpaController modeloArticuloCatalogo;
    private StockArticuloJpaController modeloStockArticulo;
    private PrecioArticuloJpaController modeloPrecioArticulo;

    //Entidades necesarias para el controlador
    //Comprobante comprobante = null;
    //Pago pago = null;
    //Empleado empleado = null;
    //Cliente cliente = null;
    CuentaCorriente cuentaCorrienteCliente = null;

    List<Articulo> articulosCatalogo;
    List<StockArticulo> articulosStocks;
    List<PrecioArticulo> preciosArticulos;

    //Articulo Encontrado
    Articulo articuloEncontrado = null;
    StockArticulo stockArticuloEncontrado = null;
    PrecioArticulo precioArticuloEncontrado = null;

    //Variables
    List<Cliente> clientes;
    List<Deposito> depositos;
    ArrayList<LineaDeVenta> lineasDeVenta;
    Venta nuevaVenta = null;
    Cliente clienteElegido = null;
    Cliente clienteSeleccionado = null;
    Deposito depositoSeleccionado = null;
    Unidad unidadSeleccionada = null;
    TalonarioComprobante talonarioSeleccionado = null;
    StockArticulo stockArticuloSeleccionado = null;
    LineaDeVenta lineaDeVentaSeleccionada = null;
    ListaDePrecio listaDePrecioSeleccionada = null;
    TipoCliente tipoClienteSeleccionado = null;

    int numeroComprobanteActual = 0;
    Date fechaFactura = new Date();
    int numeroLineaDeVenta = 0;
    int seleccionLdv = 0;
    IEstrategiaFijarPreciosVenta politicaDePrecio = null;
    Usuario user = null;
    Empleado empleadoSeleccionado = null;
    
    Comprobante comprobante = null;
    
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
        modeloTipoCliente = new TipoClienteJpaController(Conexion.getEmf());
        modeloUsuario = new UsuarioJpaController(Conexion.getEmf());
        modeloLineaDeVenta = new LineaDeVentaJpaController(Conexion.getEmf());
                
        modeloArticuloCatalogo = new ArticuloJpaController(Conexion.getEmf());
        modeloStockArticulo = new StockArticuloJpaController(Conexion.getEmf());
        modeloPrecioArticulo = new PrecioArticuloJpaController(Conexion.getEmf());
        modeloListaDePrecio = new ListaDePrecioJpaController(Conexion.getEmf());  
        
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
    public void actionPerformed(ActionEvent e) {

        //Boton Buscar
        if (e.getSource() == vista.getJbtn_Buscar()) {
            btn_buscarCliente();
        }

        //Boton Cargar Item
        if (e.getSource() == vista.getJbtn_CargarItem()) {
            btn_cargarItem();
        }


        //Boton Salir Sin Grabar
        if (e.getSource() == vista.getJbtn_SalirSinGrabar()) {
            btn_salirSinGrabar();
        }

        //Boton Grabar E Imprimir
        if (e.getSource() == vista.getJbtn_GrabarEImprimir()&& nuevaVenta!=null) {
            btn_grabarEImprimir();
        }

        //Boton Agregar
        if (clienteElegido != null) {
            //Boton Agregar Buscar Cliente
            if (e.getSource() == buscarCliente.getJbtn_Agregar()) {
                try {
                    btn_agregarBuscarCliente();
                } catch (IOException ex) {
                    Logger.getLogger(VentaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            //Boton Agregar Buscar Deposito
            if (unidadSeleccionada != null) {
                if (buscarDeposito != null) {
                    if (e.getSource() == buscarDeposito.getJbtn_AgregarDeposito()) {
                        btn_agregarBuscarDeposito();
                    }
                }
            }
        }

        //Boton agregar Item Buscar Articulo
        if (clienteSeleccionado != null) {
            if (unidadSeleccionada != null) {
                if (depositoSeleccionado != null) {
                    if (articuloEncontrado != null) {
                        if (e.getSource() == buscarArticulo.getJbtn_Agregar()) {
                            btn_agregarBuscarArticulo();
                        }
                    }
                }
            }
        }

        //Boton vista Buscar Deposito
        if (clienteSeleccionado != null) {
            if (e.getSource() == vista.getJbtn_BuscarDeposito()) {
                btn_SeleccionarDepositoUnidad();
            }
        }

    }

    //--------------------------------------------------------------------------
    //Controlador  JPanel Registro de Venta
    //--------------------------------------------------------------------------
    public void btn_buscarCliente() {
        clienteElegido = null;
        clienteSeleccionado = null;
        depositoSeleccionado = null;
        buscarCliente = null;
        buscarArticulo = null;
        buscarDeposito = null;

        buscarCliente = new JDialogBuscarCliente(null, true);
        buscarCliente.setLocationRelativeTo(JframePrincipal.jPanelContenido);
        buscarCliente.setResizable(false);
        buscarCliente.setControlador(this);

        limpiarTodosLosCamposBusquedaCliente();
        inhabilitarTodoBusquedaCliente(false);

        limpiarTodosLosCamposPanelVenta();
        inhabilitarTodoPanelVenta(false);
        vista.getJbtn_Buscar().setEnabled(true);
        vista.getJrb_Codigo().setEnabled(true);
        vista.getJrb_CuitDni().setEnabled(true);
        vista.getJrb_Nombre().setEnabled(true);
        vista.getJbtn_SalirSinGrabar().setEnabled(true);

        if (vista.getJrb_Nombre().isSelected()) {
            buscarCliente.getJlbl_TituloBusqueda().setText("Nombre Cliente: ");
        }
        if (vista.getJrb_Codigo().isSelected()) {
            buscarCliente.getJlbl_TituloBusqueda().setText("Codigo Cliente: ");
        }
        if (vista.getJrb_CuitDni().isSelected()) {
            buscarCliente.getJlbl_TituloBusqueda().setText("CUIT-DNI Cliente: ");
        }
        buscarCliente.setVisible(true);
    }

    public void btn_SeleccionarDepositoUnidad() {
        vista.getJtf_Total().setText("");
        buscarArticulo = null;
        buscarDeposito = null;
        depositoSeleccionado = null;
        articuloEncontrado = null;

        buscarDeposito = new JDialogBuscarDeposito(null, true);
        buscarDeposito.setLocationRelativeTo(JframePrincipal.jPanelContenido);
        buscarDeposito.setResizable(false);
        buscarDeposito.setControlador(this);

        inhabilitarTodoBusquedaDeposito(false);
        buscarDeposito.getJtf_BuscarDeposito().setEnabled(true);
        buscarDeposito.setVisible(true);
    }

    public void btn_cargarItem() {
        buscarArticulo = new JDialogBuscarArticulo(null, true);
        buscarArticulo.setLocationRelativeTo(JframePrincipal.jPanelContenido);
        buscarArticulo.setResizable(false);
        buscarArticulo.setControlador(this);

        inhabilitarTodoBusquedaArticulo(false);
        buscarArticulo.getJtf_BuscarArticulo().setEnabled(true);
        buscarArticulo.getJcb_Deposito().removeAllItems();
        buscarArticulo.getJcb_Deposito().addItem(depositoSeleccionado.getDescripcion());
        buscarArticulo.setVisible(true);
    }

    public void btn_salirSinGrabar() {
        //Limpia campos
        limpiarTodosLosCamposPanelVenta();

        //inhabilitar Campos
        inhabilitarTodoPanelVenta(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotonesPanelVenta(false);

        //Habilita el Arbol de seleccion
        JframePrincipal.habilitarArbol(true);

    }

    public void btn_grabarEImprimir() {
        nuevaVenta.setEsCompleta(true);
        modelo.create(nuevaVenta);
        
        for (LineaDeVenta ldv : lineasDeVenta) {
            modeloLineaDeVenta.create(ldv);
        }
        
        politicaDePrecio.crearComprobante(nuevaVenta, comprobante);
        
        
        
        btn_salirSinGrabar();
    }

    //Funciones especificas
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
        vista.limpiarCampo(vista.getJtf_Total());

        //Limpia todos los JComboBox
        vista.limpiarCombobox(vista.getJcb_Unidad());
        vista.limpiarCombobox(vista.getJcb_Comprobante());
        vista.limpiarCombobox(vista.getJcb_tipoCliente());
        vista.limpiarCombobox(vista.getJcb_zona_direccion());
        vista.limpiarCombobox(vista.getJcb_provincia_direccion());
        vista.limpiarCombobox(vista.getJcb_localidad_direccion());
        vista.limpiarCombobox(vista.getJcb_Deposito());

        vista.getJdc_fechaComprobante().setDate(null);

        vaciarTabla(vista.getTablaLineaDeVenta());

    }

    /**
     * Vacia la tabla de venta
     *
     * @param tablaD
     */
    public void vaciarTabla(JTable tablaD) {
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
        vista.habilitarBoton(estado, vista.getJbtn_SalirSinGrabar());
        vista.habilitarBoton(estado, vista.getJbtn_GrabarEImprimir());
        vista.habilitarBoton(estado, vista.getJbtn_BuscarDeposito());

        vista.getJdc_fechaComprobante().setEnabled(estado);
    }

    /**
     * Inhabilita todos los componentes del panel de Registro de Venta
     *
     * @param estado
     */
    public void inhabilitarTodoPanelVenta(boolean estado) {
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
        vista.getJtf_Total().setEnabled(estado);

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
        vista.getJbtn_SalirSinGrabar().setEnabled(estado);
        vista.getJbtn_GrabarEImprimir().setEnabled(estado);
        vista.getJbtn_BuscarDeposito().setEnabled(estado);

        vista.getJrb_Codigo().setEnabled(estado);
        vista.getJrb_CuitDni().setEnabled(estado);
        vista.getJrb_Nombre().setEnabled(estado);

        vista.getJdc_fechaComprobante().setEnabled(estado);
    }

    /**
     * Carga el talonarioSeleccionado correspondiente al cliente
     *
     * @param unTipoCliente
     * @param unaUnidad
     */
    public void cargarTipoComprobante(TipoCliente unTipoCliente) {
        for (TalonarioComprobante lp : modeloTalonarioComprobante.findTalonarioComprobanteEntities()) {
            if (lp.getId_TipoCliente().equals(unTipoCliente.getId())) {
                talonarioSeleccionado = lp;
                vista.getJcb_Comprobante().removeAllItems();
                vista.getJcb_Comprobante().addItem(talonarioSeleccionado.getDescripcion());
            }
        }
    }

    /**
     * Carga el numero de comprobante actual
     *
     * @param unTalonario
     */
    public void cargarNumeroComprobanteActual(TalonarioComprobante unTalonario) {
        numeroComprobanteActual = unTalonario.getNumeracion_Actual() + 1;
        vista.getJtf_NumeroComprobante().setText(Integer.toString(unTalonario.getNumeracion_Actual() + 1));
    }

    /**
     * Carga Unidad del Comprobante
     *
     * @param unTalonario
     */
    public void cargarUnidad(TalonarioComprobante unTalonario) {
        for (Unidad u : modeloUnidad.findUnidadEntities()) {
            if (u.getId().equals(unTalonario.getId_Unidad())) {
                unidadSeleccionada = u;
                vista.getJcb_Unidad().removeAllItems();
                vista.getJcb_Unidad().addItem(unidadSeleccionada.getNombre());
            }
        }
    }

    /**
     * Carga Fecha actual del comprobante
     */
    public void cargarFechaActual() {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/YYYY");
        vista.getJdc_fechaComprobante().setDate(fechaFactura);
        formatoFecha.format(fechaFactura);
    }

    /**
     * Llena la tabla de linea de Venta
     *
     * @param tablaD
     */
    public void llenarTablaLineaDeVenta(JTable tablaD) {
        numeroLineaDeVenta = 0;
        if(numeroLineaDeVenta>=1){
            vista.getJbtn_GrabarEImprimir().setEnabled(true);
        }else{
            vista.getJbtn_GrabarEImprimir().setEnabled(false);
        }
        lineasDeVenta = new ArrayList<LineaDeVenta>();
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

        //Crea la nueva venta
        nuevaVenta = new Venta();
        nuevaVenta.setCliente(clienteSeleccionado);
        nuevaVenta.setUnidad(unidadSeleccionada);
        nuevaVenta.setFecha(vista.getJdc_fechaComprobante().getDate());
        nuevaVenta.setHora(vista.getJdc_fechaComprobante().getDate());
        nuevaVenta.setEsCompleta(false);
        
        
        llenarTipoCliente();
        
        llenarListaDePrecio();
        
        llenarTipoUsuario();
        
        agregarEmpleadoAVenta();        
        
        nuevaVenta.setEmpleado(empleadoSeleccionado);
        
        politicaDePrecio = crearPoliticaDePrecio(tipoClienteSeleccionado.getId().intValue());
        
    }
    
    public IEstrategiaFijarPreciosVenta crearPoliticaDePrecio (int tipoCliente){
        IEstrategiaFijarPreciosVenta politicaDePrecio = null;
        if(tipoCliente==1){
            politicaDePrecio = new FijarPrecioVentaMayorista();
        }
        if(tipoCliente==2){
            politicaDePrecio = new FijarPrecioVentaMinorista();
        }
        return politicaDePrecio;
    }

    public void llenarTipoCliente(){
        for (TipoCliente tp : modeloTipoCliente.findTipoClienteEntities()) {
            if (clienteSeleccionado.getTipocliente().getId().equals(tp.getId())) {
                tipoClienteSeleccionado = tp;
            }
        }
    }
    
    public void llenarListaDePrecio(){
        for (ListaDePrecio lp : modeloListaDePrecio.findListaDePrecioEntities()) {
            if (lp.getTipoCliente().getId().equals(tipoClienteSeleccionado.getId())) {
                listaDePrecioSeleccionada = lp;
            }
        }
    }
    
    public void llenarTipoUsuario(){
        for (Usuario u : modeloUsuario.findUsuarioEntities()) {
            if (u.equals(LoginController.getInstance())) {
                user = u;
            }
        }
    }
    
    public void agregarEmpleadoAVenta(){
        for (Empleado emp : modeloEmpleado.findEmpleadoEntities()) {
            if (emp.getUnUsuario().equals(user)) {
                empleadoSeleccionado = emp;
            }
        }
    }
    
    //--------------------------------------------------------------------------
    //Fin Controlador  JPanel Registro de Venta
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //Controlador  JDialog Buscar Cliente
    //--------------------------------------------------------------------------
    /**
     * Llena la tabla de Clientes en funcion a la opcion seleccionada
     *
     * @param tablaD
     * @param datoABuscar
     * @param opcion
     */
    public void llenarTablaBuscarCliente(JTable tablaD, String datoABuscar, int opcion) {
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
        modeloT.addColumn("ID");
        modeloT.addColumn("CUIT-CUIL");
        modeloT.addColumn("NOMBRE");
        modeloT.addColumn("APELLIDO");
        modeloT.addColumn("TIPO-CLIENTE");

        //Cantidad de columnas 
        Object[] columna = new Object[6];

        for (Cliente cli : modeloCliente.findClienteEntities()) {
            if (cli.getApellido().toString().indexOf(datoABuscar) != -1 && opcion == 1) {
                //Guarda en Lista de clientes  
                clientes.add(cli);
                columna[0] = cli.getId();
                columna[1] = cli.getCuitCuil();;
                columna[2] = cli.getNombre();
                columna[3] = cli.getApellido();
                columna[4] = cli.getTipocliente().getDescripcion();
                modeloT.addRow(columna);
            }

            if (cli.getId().toString().indexOf(datoABuscar) != -1 && opcion == 2) {
                //Guarda en Lista de clientes  
                clientes.add(cli);
                columna[0] = cli.getId();
                columna[1] = cli.getCuitCuil();;
                columna[2] = cli.getNombre();
                columna[3] = cli.getApellido();
                columna[4] = cli.getTipocliente().getDescripcion();
                modeloT.addRow(columna);
            }

            if (cli.getCuitCuil().toString().indexOf(datoABuscar) != -1 && opcion == 3) {
                //Guarda en Lista de clientes  
                clientes.add(cli);
                columna[0] = cli.getId();
                columna[1] = cli.getCuitCuil();;
                columna[2] = cli.getNombre();
                columna[3] = cli.getApellido();
                columna[4] = cli.getTipocliente().getDescripcion();
                modeloT.addRow(columna);
            }
        }
    }

    /**
     * Boton AgregarBuscar JDialog BuscarCliente
     *
     * @throws IOException
     */
    public void btn_agregarBuscarCliente() throws IOException {
        if (clienteElegido != null) {
            buscarCliente.dispose();
            clienteSeleccionado = clienteElegido;
            //cargar datos en JPanel
            limpiarTodosLosCamposPanelVenta();
            vista.getJtf_ID().setText(clienteElegido.getId().toString());
            vista.getJtf_cuitCuil().setText(clienteElegido.getCuitCuil());
            vista.getJtf_Nombre().setText(clienteElegido.getNombre());
            vista.getJtf_Apellido().setText(clienteElegido.getApellido());

            //Si posee datos de direccion se cargan en la vista
            if (clienteElegido.getDireccion() != null) {
                if (clienteElegido.getDireccion().getLocalidad() != null) {

                    vista.getJcb_zona_direccion().removeAllItems();
                    vista.getJcb_zona_direccion().addItem(clienteElegido.getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                    vista.getJcb_provincia_direccion().removeAllItems();
                    vista.getJcb_provincia_direccion().addItem(clienteElegido.getDireccion().getLocalidad().getProvincia().getNombre());

                    vista.getJcb_localidad_direccion().removeAllItems();
                    vista.getJcb_localidad_direccion().addItem(clienteElegido.getDireccion().getLocalidad().getNombre());

                    if (clienteElegido.getTipocliente() != null) {
                        vista.getJcb_tipoCliente().removeAllItems();
                        vista.getJcb_tipoCliente().addItem(clienteElegido.getTipocliente().getDescripcion());
                    }
                    if (clienteElegido.getCuentaCorriente() != null) {
                        vista.getJtf_ctaCte().setText(Float.toString(clienteElegido.getCuentaCorriente().getSaldo()));
                    } else {
                        vista.getJtf_ctaCte().setText("SIN CUENTA CORRIENTE INICIADA");
                    }

                    vista.getJtf_calle_direccion().setText(clienteElegido.getDireccion().getCalle());
                    vista.getJtf_numero_direccion().setText(clienteElegido.getDireccion().getNumero());
                    vista.getJtf_piso_direccion().setText(clienteElegido.getDireccion().getPiso());
                    vista.getJtf_departamento_direccion().setText(clienteElegido.getDireccion().getDepartamento());

                    //carga Tipo de Comprobante
                    cargarTipoComprobante(clienteElegido.getTipocliente());

                    //Carga numeración actual del comprobante
                    cargarNumeroComprobanteActual(talonarioSeleccionado);

                    //Carga la unidadSeleccionada a la que pertenece el talonarioSeleccionado
                    cargarUnidad(talonarioSeleccionado);

                    //Carga la fecha actual para la factura
                    cargarFechaActual();

                    //Habilitar Boton de Buscar Depostito
                    vista.getJbtn_BuscarDeposito().setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "cliente no tiene Localidad asignada");
                }
            } else {
                JOptionPane.showMessageDialog(null, "cliente no tiene Direccion asignada");
            }
            //cargarLista de Precio
        } else {
            System.out.println("no existe");
        }
    }

    /**
     * limpia todos los campos de la vista BuscarCliente
     */
    public void limpiarTodosLosCamposBusquedaCliente() {
        //Limpia todos los JtextField
        buscarCliente.limpiarCampo(buscarCliente.getJtf_ID());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_Nombre());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_cuitCuil());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_Apellido());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_ctaCte());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_calle_direccion());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_numero_direccion());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_piso_direccion());
        buscarCliente.limpiarCampo(buscarCliente.getJtf_departamento_direccion());

        //Limpia todos los JComboBox
        buscarCliente.limpiarCombobox(buscarCliente.getJcb_tipoCliente());
        buscarCliente.limpiarCombobox(buscarCliente.getJcb_zona_direccion());
        buscarCliente.limpiarCombobox(buscarCliente.getJcb_provincia_direccion());
        buscarCliente.limpiarCombobox(buscarCliente.getJcb_localidad_direccion());
    }

    /**
     * Modifica la habilitación de los Botones de la BuscarCliente en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotonesBusquedaCliente(boolean estado) {
        //Inhabilita Botones 
        buscarCliente.habilitarBoton(estado, buscarCliente.getJbtn_Agregar());
    }

    /**
     * Inhabilita todos los componentes del JDialog de Buscar Cliente
     *
     * @param estado
     */
    public void inhabilitarTodoBusquedaCliente(boolean estado) {
        //Limpia todos los JtextField
        buscarCliente.getJtf_ID().setEnabled(estado);
        buscarCliente.getJtf_Nombre().setEnabled(estado);
        buscarCliente.getJtf_cuitCuil().setEnabled(estado);
        buscarCliente.getJtf_Apellido().setEnabled(estado);
        buscarCliente.getJtf_ctaCte().setEnabled(estado);
        buscarCliente.getJtf_calle_direccion().setEnabled(estado);
        buscarCliente.getJtf_numero_direccion().setEnabled(estado);
        buscarCliente.getJtf_piso_direccion().setEnabled(estado);
        buscarCliente.getJtf_departamento_direccion().setEnabled(estado);

        //Limpia todos los JComboBox
        buscarCliente.getJcb_tipoCliente().setEnabled(estado);
        buscarCliente.getJcb_zona_direccion().setEnabled(estado);
        buscarCliente.getJcb_provincia_direccion().setEnabled(estado);
        buscarCliente.getJcb_localidad_direccion().setEnabled(estado);

        //Inhabilita Botones 
        buscarCliente.getJbtn_Agregar().setEnabled(estado);
    }

    //--------------------------------------------------------------------------
    //Fin Controlador  JDialog Buscar Cliente
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //Controlador  JDialog Buscar Deposito
    //--------------------------------------------------------------------------
    /**
     * llena la tabla de Deposito
     *
     * @param tablaD
     * @param unaUnidad
     */
    public void llenarTablaBuscarDeposito(JTable tablaD, Unidad unaUnidad, String datoABuscar) {
        depositos = new ArrayList<Deposito>();
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
        modeloT.addColumn("Descripción");
        modeloT.addColumn("Unidad");
        modeloT.addColumn("Empresa");

        //Cantidad de columnas 
        Object[] columna = new Object[6];

        int numero = 0;

        for (Deposito depo : modeloDeposito.findDepositoEntities()) {
            if (depo.getDescripcion().toString().indexOf(datoABuscar) != -1 && depo.getUnaUnidad().equals(unaUnidad)) {
                //Guarda en Lista de Depositos  
                depositos.add(depo);
                numero = numero + 1;
                columna[0] = String.valueOf(numero);
                columna[1] = depo.getId();
                columna[2] = depo.getDescripcion();
                columna[3] = depo.getUnaUnidad().getNombre();
                columna[4] = depo.getUnaUnidad().getUnaEmpresa().getRazonSocial();
                modeloT.addRow(columna);
            }
        }
    }

    /**
     * Boton AgregarBuscar JDialog BuscarDeposito
     *
     * @throws IOException
     */
    public void btn_agregarBuscarDeposito() {
        if (unidadSeleccionada != null) {
            if (depositoSeleccionado != null) {
                vista.getJcb_Deposito().removeAllItems();
                vista.getJcb_Deposito().addItem(depositoSeleccionado.getDescripcion());
                vista.getJbtn_CargarItem().setEnabled(true);
                llenarTablaLineaDeVenta(vista.getTablaLineaDeVenta());
                vista.getTablaLineaDeVenta().setEnabled(true);
                
                
                if (clienteElegido.getTipocliente().getId()==1) {
                    vista.getjSpinnerDescuento().setModel(new SpinnerNumberModel(1, 1, 100, 1));
                    vista.getjSpinnerDescuento().setEditor(new JSpinner.NumberEditor(vista.getjSpinnerDescuento()));
                    JFormattedTextField tf = ((JSpinner.DefaultEditor) vista.getjSpinnerDescuento().getEditor()).getTextField();
                    tf.setEditable(false);
                    vista.getjSpinnerDescuento().setEnabled(true);
                } else {
                    vista.getjSpinnerDescuento().setEnabled(false);
                    vista.getjSpinnerDescuento().setModel(new SpinnerNumberModel(0, 0, 0, 0));
                    vista.getjSpinnerDescuento().setEditor(new JSpinner.NumberEditor(vista.getjSpinnerDescuento()));
                }
                buscarDeposito.dispose();
            }
        }
    }

    /**
     * Limpia todos los campos de JDialog BuscarDeposito
     */
    public void limpiarTodosLosCamposBusquedaDeposito() {
        //Limpia todos los JtextField
        buscarDeposito.limpiarCampo(buscarDeposito.getJtf_ID());
        buscarDeposito.limpiarCampo(buscarDeposito.getJtf_descripcion());

        //Limpia todos los JComboBox
        buscarDeposito.limpiarCombobox(buscarDeposito.getJcb_Unidad());
    }

    /**
     *
     * @param estado
     */
    public void inhabilitarTodosLosBotonesBusquedaDeposito(boolean estado) {
        //Inhabilita Botones 
        buscarDeposito.habilitarBoton(estado, buscarDeposito.getJbtn_AgregarDeposito());
    }

    /**
     *
     * @param estado
     */
    public void inhabilitarTodoBusquedaDeposito(boolean estado) {
        //Limpia todos los JtextField
        buscarDeposito.getJtf_ID().setEnabled(estado);
        buscarDeposito.getJtf_descripcion().setEnabled(estado);
        buscarDeposito.getJtf_BuscarDeposito().setEnabled(estado);

        //Limpia todos los JComboBox
        buscarDeposito.getJcb_Unidad().setEnabled(estado);

        //Inhabilita Botones 
        buscarDeposito.getJbtn_AgregarDeposito().setEnabled(estado);
    }

    //--------------------------------------------------------------------------
    //Fin Controlador  JDialog Buscar Deposito
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //Controlador  JDialog Buscar Articulo
    //--------------------------------------------------------------------------
    public void llenarTablaLineaDeVenta(JTable tablaD, ArrayList<LineaDeVenta> ldvtas) {

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
        modeloT.addColumn("CODIGO");
        modeloT.addColumn("CANTIDAD");
        modeloT.addColumn("PRECIO UNITARIO");
        modeloT.addColumn("DESCRIPCIÓN");
        modeloT.addColumn("SUB-TOTAL");

        //Cantidad de columnas 
        Object[] columna = new Object[6];

        if (ldvtas.size()>0) {
            for (LineaDeVenta ldv : ldvtas) {
                columna[0] = ldv.getArticulo().getId();
                columna[1] = ldv.getCantidad();
                columna[2] = modeloPrecioArticulo.buscarPrecioDeArticuloConListaDePrecio(ldv.getArticulo(), listaDePrecioSeleccionada).getPrecio();
                columna[3] = ldv.getArticulo().getDescripcion();
                columna[4] = ldv.getSubTotal();
                modeloT.addRow(columna);
            }
        }
        
    }

    /**
     *
     */
    public void btn_agregarBuscarArticulo() {
        boolean repetido = false;
        int cantidadRepetido = 0;
        int contador = -1;
        lineaDeVentaSeleccionada = new LineaDeVenta();
        lineaDeVentaSeleccionada.setArticulo(articuloEncontrado);
        lineaDeVentaSeleccionada.setCantidad(Integer.parseInt(buscarArticulo.getjSpinnerArticulo().getValue().toString()));
        lineaDeVentaSeleccionada.setVenta(nuevaVenta);
        
        if (lineasDeVenta.size()>0) {
            for (LineaDeVenta ldv : lineasDeVenta) {
                contador = contador +1;
                if (ldv.getArticulo().getId().equals(lineaDeVentaSeleccionada.getArticulo().getId())) {
                    repetido= true;
                    cantidadRepetido = ldv.getCantidad();
                }
            }
            
            if (repetido) {
                if ((lineaDeVentaSeleccionada.getCantidad()+cantidadRepetido )>modeloStockArticulo.buscarStockDeArticuloEnDeposito(lineaDeVentaSeleccionada.getArticulo(), depositoSeleccionado).getStockActual()) {
                        JOptionPane.showMessageDialog(null, "ID:"+lineaDeVentaSeleccionada.getArticulo().getId()+" "+lineaDeVentaSeleccionada.getArticulo().getDescripcion()+ " supera la cantidad permitida. Modificar cantidad");  
                    }else{
                        
                        lineasDeVenta.remove(contador);
                        lineasDeVenta.add(lineaDeVentaSeleccionada);
                        politicaDePrecio.getSubTotal(lineasDeVenta, modeloPrecioArticulo, listaDePrecioSeleccionada);
                        politicaDePrecio.aplicarDescuento(lineasDeVenta, Integer.parseInt(vista.getjSpinnerDescuento().getValue().toString()));
                        
                        llenarTablaLineaDeVenta(vista.getTablaLineaDeVenta(), lineasDeVenta);
                        vista.getJtf_Total().setText(Float.toString(politicaDePrecio.getTotal(lineasDeVenta)));
                        buscarArticulo.dispose();
                    }
            }else{
                lineasDeVenta.add(lineaDeVentaSeleccionada);
                politicaDePrecio.getSubTotal(lineasDeVenta, modeloPrecioArticulo, listaDePrecioSeleccionada);
                politicaDePrecio.aplicarDescuento(lineasDeVenta, Integer.parseInt(vista.getjSpinnerDescuento().getValue().toString()));
                llenarTablaLineaDeVenta(vista.getTablaLineaDeVenta(), lineasDeVenta);
                vista.getJtf_Total().setText(Float.toString(politicaDePrecio.getTotal(lineasDeVenta)));
                numeroLineaDeVenta = numeroLineaDeVenta + 1;
                buscarArticulo.dispose();
            }
            
        }else{
            lineasDeVenta.add(lineaDeVentaSeleccionada);
            politicaDePrecio.getSubTotal(lineasDeVenta, modeloPrecioArticulo, listaDePrecioSeleccionada);
            politicaDePrecio.aplicarDescuento(lineasDeVenta, Integer.parseInt(vista.getjSpinnerDescuento().getValue().toString()));
            llenarTablaLineaDeVenta(vista.getTablaLineaDeVenta(), lineasDeVenta);
            vista.getJtf_Total().setText(Float.toString(politicaDePrecio.getTotal(lineasDeVenta)));
            numeroLineaDeVenta = numeroLineaDeVenta + 1;
            buscarArticulo.dispose();
        }
        
        if(numeroLineaDeVenta>=1){
            vista.getJbtn_GrabarEImprimir().setEnabled(true);
        }else{
            vista.getJbtn_GrabarEImprimir().setEnabled(false);
        }
    }

    /**
     * Llena la tabla de Buscar Articulo
     *
     * @param tablaD
     * @param unDeposito
     * @param datoABuscar
     * @param opcion
     */
    public void llenarTablaBuscarArticulo(JTable tablaD, Deposito unDeposito, String datoABuscar, int opcion) {
        articulosCatalogo = new ArrayList<Articulo>();
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
        modeloT.addColumn("ID°");
        modeloT.addColumn("Descripcion");
        modeloT.addColumn("Categoria");
        modeloT.addColumn("Proveedor");
        modeloT.addColumn("Stock");

        //Cantidad de columnas 
        Object[] columna = new Object[6];

        for (Articulo art : modeloArticuloCatalogo.findArticuloEntities()) {

            if (art.getId().toString().indexOf(datoABuscar) != -1 && opcion == 1) {
                //Guarda en Lista de articulos  
                articulosCatalogo.add(art);
                columna[0] = art.getId();
                columna[1] = art.getDescripcion();
                columna[2] = art.getUnCategoriaDeCatalogo().getDescripcion();
                columna[3] = art.getUnProveedor().getRazonSocial();
                if (modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, unDeposito) != null) {
                    columna[4] = modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, unDeposito).getStockActual();
                } else {
                    columna[4] = "Sin Stock";
                }
                modeloT.addRow(columna);
            }

            if (art.getDescripcion().toString().indexOf(datoABuscar) != -1 && opcion == 2) {
                //Guarda en Lista de articulos  
                articulosCatalogo.add(art);
                columna[0] = art.getId();
                columna[1] = art.getDescripcion();
                columna[2] = art.getUnCategoriaDeCatalogo().getDescripcion();
                columna[3] = art.getUnProveedor().getRazonSocial();
                if (modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, unDeposito) != null) {
                    columna[4] = modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, unDeposito).getStockActual();
                } else {
                    columna[4] = "Sin Stock";
                }
                modeloT.addRow(columna);
            }

            if (art.getUnProveedor().getRazonSocial().indexOf(datoABuscar) != -1 && opcion == 3) {
                //Guarda en Lista de articulos  
                articulosCatalogo.add(art);
                columna[0] = art.getId();
                columna[1] = art.getDescripcion();
                columna[2] = art.getUnCategoriaDeCatalogo().getDescripcion();
                columna[3] = art.getUnProveedor().getRazonSocial();
                if (modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, unDeposito) != null) {
                    columna[4] = modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, unDeposito).getStockActual();
                } else {
                    columna[4] = "Sin Stock";
                }
                modeloT.addRow(columna);
            }
        }
    }

    /**
     * Limpia todos los campos de JDialog BuscarArticulo
     */
    public void limpiarTodosLosCamposBusquedaArticulo() {
        //Limpia todos los JtextField
        buscarArticulo.limpiarCampo(buscarArticulo.getJtf_ID());
        buscarArticulo.limpiarCampo(buscarArticulo.getJtf_descripcion());
        buscarArticulo.limpiarCampo(buscarArticulo.getJtf_stockActual());
        buscarArticulo.limpiarCampo(buscarArticulo.getJtf_BuscarArticulo());

        //Limpia todos los JComboBox
        buscarArticulo.limpiarCombobox(buscarArticulo.getJcb_Unidad());
        buscarArticulo.limpiarCombobox(buscarArticulo.getJcb_Deposito());
        buscarArticulo.limpiarCombobox(buscarArticulo.getJcb_Categoria());
        buscarArticulo.limpiarCombobox(buscarArticulo.getJcb_Proveedor());
    }

    /**
     * Inhabilita todos los botones de Buscar Articulo
     *
     * @param estado
     */
    public void inhabilitarTodosLosBotonesBusquedaArticulo(boolean estado) {
        //Inhabilita Botones 
        buscarArticulo.habilitarBoton(estado, buscarArticulo.getJbtn_Agregar());
    }

    /**
     * Inhabilita todos los elementos de Buscar Articulo
     *
     * @param estado
     */
    public void inhabilitarTodoBusquedaArticulo(boolean estado) {
        //Limpia todos los JtextField
        buscarArticulo.getJtf_ID().setEnabled(estado);
        buscarArticulo.getJtf_descripcion().setEnabled(estado);
        buscarArticulo.getJtf_stockActual().setEnabled(estado);

        //Limpia todos los JComboBox
        buscarArticulo.getJcb_Unidad().setEnabled(estado);
        buscarArticulo.getJcb_Deposito().setEnabled(estado);
        buscarArticulo.getJcb_Categoria().setEnabled(estado);
        buscarArticulo.getJcb_Proveedor().setEnabled(estado);
        buscarArticulo.getjSpinnerArticulo().setEnabled(estado);
        buscarArticulo.getJbtn_Agregar().setEnabled(estado);
    }

    //--------------------------------------------------------------------------
    //Fin Controlador  JDialog Buscar Articulo
    //--------------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == buscarCliente.getJtf_BuscarCliente()) {
            limpiarTodosLosCamposBusquedaCliente();
            buscarCliente.getJbtn_Agregar().setEnabled(false);
            if (vista.getJrb_Nombre().isSelected()) {
                llenarTablaBuscarCliente(buscarCliente.getTablaClientes(), buscarCliente.getJtf_BuscarCliente().getText(), 1);
            }
            if (vista.getJrb_Codigo().isSelected()) {
                llenarTablaBuscarCliente(buscarCliente.getTablaClientes(), buscarCliente.getJtf_BuscarCliente().getText(), 2);
            }
            if (vista.getJrb_CuitDni().isSelected()) {
                llenarTablaBuscarCliente(buscarCliente.getTablaClientes(), buscarCliente.getJtf_BuscarCliente().getText(), 3);
            }
        }
        if (clienteSeleccionado != null && depositoSeleccionado == null && clienteElegido != null && buscarDeposito != null) {
            if (e.getSource() == buscarDeposito.getJtf_BuscarDeposito()) {
                buscarDeposito.getJbtn_AgregarDeposito().setEnabled(false);
                llenarTablaBuscarDeposito(buscarDeposito.getTablaListaDeDeposito(), unidadSeleccionada, buscarDeposito.getJtf_BuscarDeposito().getText());
            }
        }
        if (depositoSeleccionado != null && buscarArticulo != null) {
            if (e.getSource() == buscarArticulo.getJtf_BuscarArticulo()) {
                buscarArticulo.getjSpinnerArticulo().setEnabled(false);
                buscarArticulo.getJbtn_Agregar().setEnabled(false);

                if (buscarArticulo.getJrb_Codigo().isSelected()) {
                    llenarTablaBuscarArticulo(buscarArticulo.getTablaListaDeArticulo(), depositoSeleccionado, buscarArticulo.getJtf_BuscarArticulo().getText(), 1);
                }
                if (buscarArticulo.getJrb_Descripcion().isSelected()) {
                    llenarTablaBuscarArticulo(buscarArticulo.getTablaListaDeArticulo(), depositoSeleccionado, buscarArticulo.getJtf_BuscarArticulo().getText(), 2);
                }
                if (buscarArticulo.getJrb_Proveedor().isSelected()) {
                    llenarTablaBuscarArticulo(buscarArticulo.getTablaListaDeArticulo(), depositoSeleccionado, buscarArticulo.getJtf_BuscarArticulo().getText(), 3);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clienteElegido = null;
        
        if (clienteElegido == null && buscarCliente != null && buscarArticulo == null ) {
            limpiarTodosLosCamposBusquedaCliente();
            buscarCliente.getJbtn_Agregar().setEnabled(true);
            //carga los datos en la vista si cualquiera de las variables es verdadera
            //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
            int seleccion = buscarCliente.getTablaClientes().rowAtPoint(e.getPoint());
            buscarCliente.getJtf_ID().setText(String.valueOf(buscarCliente.getTablaClientes().getValueAt(seleccion, 0)));

            //Si posee datos de direccion se cargan en la vista
            for (Cliente cliente : clientes) {
                if (cliente.getId().toString().equals(buscarCliente.getJtf_ID().getText())) {
                    if (cliente.getDireccion() != null) {
                        if (cliente.getDireccion().getLocalidad() != null) {
                            clienteElegido = cliente;
                            buscarCliente.getJtf_cuitCuil().setText(clienteElegido.getCuitCuil());
                            buscarCliente.getJtf_Nombre().setText(clienteElegido.getNombre());
                            buscarCliente.getJtf_Apellido().setText(clienteElegido.getApellido());

                            buscarCliente.getJcb_zona_direccion().removeAllItems();
                            buscarCliente.getJcb_zona_direccion().addItem(clienteElegido.getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                            buscarCliente.getJcb_provincia_direccion().removeAllItems();
                            buscarCliente.getJcb_provincia_direccion().addItem(clienteElegido.getDireccion().getLocalidad().getProvincia().getNombre());

                            buscarCliente.getJcb_localidad_direccion().removeAllItems();
                            buscarCliente.getJcb_localidad_direccion().addItem(clienteElegido.getDireccion().getLocalidad().getNombre());

                            if (clienteElegido.getTipocliente() != null) {
                                buscarCliente.getJcb_tipoCliente().removeAllItems();
                                buscarCliente.getJcb_tipoCliente().addItem(clienteElegido.getTipocliente().getDescripcion());
                            }
                            if (clienteElegido.getCuentaCorriente() != null) {
                                buscarCliente.getJtf_ctaCte().setText(Float.toString(clienteElegido.getCuentaCorriente().getSaldo()));
                            } else {
                                buscarCliente.getJtf_ctaCte().setText("SIN CUENTA CORRIENTE INICIADA");
                            }

                            buscarCliente.getJtf_calle_direccion().setText(clienteElegido.getDireccion().getCalle());
                            buscarCliente.getJtf_numero_direccion().setText(clienteElegido.getDireccion().getNumero());
                            buscarCliente.getJtf_piso_direccion().setText(clienteElegido.getDireccion().getPiso());
                            buscarCliente.getJtf_departamento_direccion().setText(clienteElegido.getDireccion().getDepartamento());

                        } else {
                            JOptionPane.showMessageDialog(null, "cliente no tiene Localidad asignada");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "cliente no tiene Direccion asignada");
                    }
                }
            }
        }
        if (clienteSeleccionado != null) {//Tiene que existir el cliente
            if (unidadSeleccionada != null) {//Tiene que existir la unidad 
                if (buscarDeposito != null && depositoSeleccionado == null) {//JDialogDeposito debe estar activo y el depositoSeleccionado nulo
                    limpiarTodosLosCamposBusquedaDeposito();
                    buscarDeposito.getJbtn_AgregarDeposito().setEnabled(true);
                    int seleccion = buscarDeposito.getTablaListaDeDeposito().rowAtPoint(e.getPoint());
                    buscarDeposito.getJtf_ID().setText(String.valueOf(buscarDeposito.getTablaListaDeDeposito().getValueAt(seleccion, 1)));
                    for (Deposito deposito : modeloDeposito.findDepositoEntities()) {
                        if (deposito.getId().toString().equals(buscarDeposito.getJtf_ID().getText())) {
                            depositoSeleccionado = deposito;
                            buscarDeposito.getJtf_descripcion().setText(depositoSeleccionado.getDescripcion());
                            buscarDeposito.getJcb_Unidad().removeAllItems();
                            buscarDeposito.getJcb_Unidad().addItem(depositoSeleccionado.getUnaUnidad().getNombre());
                        }
                    }
                }
            }
        }

        if (clienteSeleccionado != null) {//Tiene que existir el cliente
            if (unidadSeleccionada != null) {//Tiene que existir la unidad 
                if (buscarArticulo != null && depositoSeleccionado != null) {//JDialogDeposito debe estar activo y el depositoSeleccionado nulo
                    limpiarTodosLosCamposBusquedaDeposito();
                    int seleccion = buscarArticulo.getTablaListaDeArticulo().rowAtPoint(e.getPoint());
                    buscarArticulo.getJtf_ID().setText(String.valueOf(buscarArticulo.getTablaListaDeArticulo().getValueAt(seleccion, 0)));
                    for (Articulo art : modeloArticuloCatalogo.findArticuloEntities()) {
                        if (art.getId().toString().equals(buscarArticulo.getJtf_ID().getText())) {
                            articuloEncontrado = art;
                            buscarArticulo.getJtf_descripcion().setText(articuloEncontrado.getDescripcion());
                            buscarArticulo.getJcb_Unidad().removeAllItems();
                            buscarArticulo.getJcb_Unidad().addItem(unidadSeleccionada.getNombre());
                            buscarArticulo.getJcb_Categoria().removeAllItems();
                            buscarArticulo.getJcb_Categoria().addItem(articuloEncontrado.getUnCategoriaDeCatalogo().getDescripcion());
                            buscarArticulo.getJcb_Proveedor().removeAllItems();
                            buscarArticulo.getJcb_Proveedor().addItem(articuloEncontrado.getUnProveedor().getRazonSocial());

                            if (modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, depositoSeleccionado) != null) {
                                stockArticuloEncontrado = modeloStockArticulo.buscarStockDeArticuloEnDeposito(art, depositoSeleccionado);
                                buscarArticulo.getJtf_stockActual().setText(Integer.toString(stockArticuloEncontrado.getStockActual()));
                                buscarArticulo.getjSpinnerArticulo().setModel(new SpinnerNumberModel(1, 1, stockArticuloEncontrado.getStockActual(), 1));
                                buscarArticulo.getjSpinnerArticulo().setEditor(new JSpinner.NumberEditor(buscarArticulo.getjSpinnerArticulo()));
                                JFormattedTextField tf = ((JSpinner.DefaultEditor) buscarArticulo.getjSpinnerArticulo().getEditor()).getTextField();
                                tf.setEditable(false);
                                buscarArticulo.getjSpinnerArticulo().setEnabled(true);
                                buscarArticulo.getJbtn_Agregar().setEnabled(true);
                            } else {
                                buscarArticulo.getjSpinnerArticulo().setEnabled(false);
                                buscarArticulo.getjSpinnerArticulo().setModel(new SpinnerNumberModel(0, 0, 0, 0));
                                buscarArticulo.getjSpinnerArticulo().setEditor(new JSpinner.NumberEditor(buscarArticulo.getjSpinnerArticulo()));
                                buscarArticulo.getJtf_stockActual().setText("Sin Stock Actual Asignado");
                                buscarArticulo.getJbtn_Agregar().setEnabled(false);
                            }
                        }
                    }
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
        if (e.getSource() == buscarCliente.getJtf_BuscarCliente()) {
            limpiarTodosLosCamposBusquedaCliente();
            buscarCliente.getJbtn_Agregar().setEnabled(false);
            if (vista.getJrb_Nombre().isSelected()) {
                llenarTablaBuscarCliente(buscarCliente.getTablaClientes(), buscarCliente.getJtf_BuscarCliente().getText(), 1);
            }
            if (vista.getJrb_Codigo().isSelected()) {
                llenarTablaBuscarCliente(buscarCliente.getTablaClientes(), buscarCliente.getJtf_BuscarCliente().getText(), 2);
            }
            if (vista.getJrb_CuitDni().isSelected()) {
                llenarTablaBuscarCliente(buscarCliente.getTablaClientes(), buscarCliente.getJtf_BuscarCliente().getText(), 3);
            }
        }
        if (clienteSeleccionado != null) {
            if (unidadSeleccionada != null) {
                if (buscarDeposito != null) {

                    limpiarTodosLosCamposBusquedaDeposito();
                    buscarDeposito.getJbtn_AgregarDeposito().setEnabled(false);
                    llenarTablaBuscarDeposito(buscarDeposito.getTablaListaDeDeposito(), unidadSeleccionada, buscarDeposito.getJtf_BuscarDeposito().getText());
                }
            }
        }

        if (clienteSeleccionado != null) {//Tiene que existir el cliente
            if (unidadSeleccionada != null) {//Tiene que existir la unidad 
                if (buscarArticulo != null && depositoSeleccionado != null) {//JDialogDeposito debe estar activo y el depositoSeleccionado nulo

                    limpiarTodosLosCamposBusquedaArticulo();
                    buscarArticulo.getJbtn_Agregar().setEnabled(false);
                    buscarArticulo.getJcb_Deposito().removeAll();
                    buscarArticulo.getJcb_Deposito().addItem(depositoSeleccionado.getDescripcion());
                    llenarTablaBuscarArticulo(buscarArticulo.getTablaListaDeArticulo(), depositoSeleccionado, buscarArticulo.getJtf_BuscarArticulo().getText(), numeroComprobanteActual);
                }
            }
        }
        
        
    }

    @Override
    public void focusLost(FocusEvent e) {
        
    }

    
}
