package controller;

import model.JPAController.Conexion;
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
import model.Cliente;
import model.CuentaCorriente;
import model.Direccion;
import model.Empresa;
import model.JPAController.ClienteJpaController;
import model.JPAController.CuentaCorrienteJpaController;
import model.JPAController.DireccionJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.LocalidadJpaController;
import model.JPAController.ProvinciaJpaController;
import model.JPAController.TipoClienteJpaController;
import model.JPAController.ZonaJpaController;
import model.Localidad;
import model.Provincia;
import model.TipoCliente;
import model.Zona;
import view.JframePrincipal;
import view.PanelRegistroCliente;

/**
 * Clase controladora de Cliente
 *
 * @author Ariel
 */
public class ClienteController extends Controller {

    private PanelRegistroCliente vista;
    private ClienteJpaController modelo;

    private ZonaJpaController modeloZona;
    private DireccionJpaController modeloDireccion;
    private ProvinciaJpaController modeloProvincia;
    private LocalidadJpaController modeloLocalidad;
    private TipoClienteJpaController modeloTipoCliente;
    private CuentaCorrienteJpaController modeloCuentaCorriente;
    private EmpresaJpaController modeloEmpresa;

    boolean bloquearAceptarCrear = false;
    boolean bloquearAceptarEliminar = false;
    boolean bloquearAceptarModificar = false;

    Zona zBuscada = null;
    Provincia pBuscada = null;
    Localidad lBuscada = null;
    Empresa empresa = null;

    TipoCliente tcBuscada = null;

    boolean zSeleccionada = false;
    boolean pSeleccionada = false;
    boolean lSeleccionada = false;
    boolean tcSeleccionada = false;

    String cuitCuilModificado = null;

    int ultimoIndiceSeleccionado = 0;
    List<Cliente> clientes;

    /**
     * Constructor Cliente
     *
     * @param vista PanelRegistroCliente
     * @param modelo ClienteJpaController
     */
    public ClienteController(PanelRegistroCliente vista, ClienteJpaController modelo) {
        modeloEmpresa = new EmpresaJpaController(Conexion.getEmf());
        empresa = modeloEmpresa.findEmpresa(1L);

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
     * ActionPerformed Controla los eventos que suceden en la vista al presionar
     * los Botones de CRUD
     *
     * @param e recepcion de Evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Boton Agregar
        if (e.getSource() == vista.getJbtn_Agregar()) {
            btn_agregar();
        }

        //Boton Modificar
        if (e.getSource() == vista.getJbtn_Modificar()) {
            btn_modificar();
        }

        //Boton Eliminar
        if (e.getSource() == vista.getJbtn_Eliminar()) {
            btn_eliminar();
        }

        //Boton Listar
        if (e.getSource() == vista.getJbtn_Listar()) {
            btn_listar();
        }

        //Boton Aceptar
        if (bloquearAceptarCrear && !bloquearAceptarEliminar && !bloquearAceptarModificar) {
            if (e.getSource() == vista.getJbtn_Aceptar()) {
                btn_aceptarCrear();
            }
        }
        if (!bloquearAceptarCrear && !bloquearAceptarEliminar && bloquearAceptarModificar) {
            if (e.getSource() == vista.getJbtn_Aceptar()) {
                btn_aceptarModificar();
            }
        }
        if (!bloquearAceptarCrear && bloquearAceptarEliminar && !bloquearAceptarModificar) {
            if (e.getSource() == vista.getJbtn_Aceptar()) {
                btn_aceptarEliminar();
            }
        }

        //Boton Cancelar
        if (e.getSource() == vista.getJbtn_Cancelar()) {
            btn_cancelar();
        }

        //Boton Volver
        if (e.getSource() == vista.getJbtn_Volver()) {
            btn_volver();
        }

    }

    /**
     * Controla el Boton Agregar Desbloquea el Boton AceptarCrear para poder
     * crear
     */
    public void btn_agregar() {
        //Posiciona la seleccion en el Panel datos clientes. 
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
        vista.habilitarCampo(false, vista.getJtf_ctaCte());

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Listar());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Eliminar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());
        vista.habilitarBoton(false, vista.getJbtn_Listar());

        //posiciona en foco de la lista en el ultimo Empleado creado                    
        vista.getTablaEmpleados().changeSelection(sizeTabla(), 1, false, false);

        //inicializa el JcomboBox
        llenarJcomboboxZona();
        llenarJcomboboxProvincia(zBuscada);
        llenarJcomboboxLocalidad(pBuscada);

        //Llena El JComboBox de TipoCliente
        llenarJcomboboxTipoCliente();

        //desbloquea el boton nuevo
        bloquearAceptarCrear = true;

        //Limpia la lista            
        vista.getTablaEmpleados().setModel(new DefaultTableModel());
    }

    /**
     * Controla el Boton Modificar Desbloquea el Boton AceptarModificar para
     * poder modificar
     */
    public void btn_modificar() {

        //Posiciona la seleccion en el Panel datos clientes. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);
        vista.habilitarCampo(false, vista.getJtfID());
        vista.habilitarCampo(false, vista.getJtf_ctaCte());

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
        Cliente clienteModificado = new Cliente();

        //setea cliente en funcion al ID de la vista
        clienteModificado = modelo.findCliente(Long.parseLong(vista.getJtfID().getText()));

        //Llena el combobox de Zona
        llenarJcomboboxZona();
        //Posiciona dentro del combobox zona al objeto zona que posee el cliente.
        vista.getJcb_zona_direccion().setSelectedItem(clienteModificado.getDireccion().getLocalidad().getProvincia().getZona());

        //Llena el combobox de provincia
        llenarJcomboboxProvincia(clienteModificado.getDireccion().getLocalidad().getProvincia().getZona());
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del cliente.
        vista.getJcb_provincia_direccion().setSelectedItem(clienteModificado.getDireccion().getLocalidad().getProvincia());

        //Llena el combobox de Localidad
        llenarJcomboboxLocalidad(clienteModificado.getDireccion().getLocalidad().getProvincia());
        //Posiciona dentro del combobox Localidad al objeto Localidad dentro de Provincia dentro zona que posee el cliente.
        vista.getJcb_localidad_direccion().setSelectedItem(clienteModificado.getDireccion().getLocalidad());

        //Llena El JComboBox de TipoCliente
        llenarJcomboboxTipoCliente();

        //Posiciona dentro del combobox Unidad al objeto Unidad  que posee el cliente.
        vista.getJcb_tipoCliente().setSelectedItem(clienteModificado.getTipocliente());

        //dniModificado incializa
        cuitCuilModificado = vista.getJtfCuitCuil().getText();

        //desbloquea el boton modificar
        bloquearAceptarModificar = true;

        //Limpia la lista            
        vista.getTablaEmpleados().setModel(new DefaultTableModel());
    }

    /**
     * Contola el Boton Eliminar Desbloquea el Boton Aceptareliminar para poder
     * eliminar
     */
    public void btn_eliminar() {

        //Posiciona la seleccion en el Panel datos clientes. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
        bloquearAceptarCrear = false;

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //inHabilitar Campos            
        inhabilitarTodosLosCampos(false);
        vista.habilitarCampo(false, vista.getJtfID());
        vista.habilitarCampo(false, vista.getJtf_ctaCte());

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
        Cliente clienteAEliminar = new Cliente();

        //setea cliente en funcion al ID de la vista
        clienteAEliminar = modelo.findCliente(Long.parseLong(vista.getJtfID().getText()));

        //Llena el combobox de Zona
        llenarJcomboboxZona();
        //Posiciona dentro del combobox zona al objeto zona que posee el cliente.
        vista.getJcb_zona_direccion().setSelectedItem(clienteAEliminar.getDireccion().getLocalidad().getProvincia().getZona());

        //Llena el combobox de provincia
        llenarJcomboboxProvincia(clienteAEliminar.getDireccion().getLocalidad().getProvincia().getZona());
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del cliente.
        vista.getJcb_provincia_direccion().setSelectedItem(clienteAEliminar.getDireccion().getLocalidad().getProvincia());

        //Llena el combobox de Localidad
        llenarJcomboboxLocalidad(clienteAEliminar.getDireccion().getLocalidad().getProvincia());
        //Posiciona dentro del combobox Localidad al objeto Localidad dentro de Provincia dentro zona que posee el cliente.
        vista.getJcb_localidad_direccion().setSelectedItem(clienteAEliminar.getDireccion().getLocalidad());

        //Llena El JComboBox de unidad
        llenarJcomboboxTipoCliente();
        //Posiciona dentro del combobox Unidad al objeto Unidad  que posee el cliente.
        vista.getJcb_tipoCliente().setSelectedItem(clienteAEliminar.getTipocliente());

        //dniModificado incializa
        cuitCuilModificado = vista.getJtfCuitCuil().getText();

        //dniModificado incializa
        cuitCuilModificado = vista.getJtfCuitCuil().getText();

        //Habilita boton Aceptar Eliminar Bloqueado
        bloquearAceptarEliminar = true;

        //Limpia la lista            
        vista.getTablaEmpleados().setModel(new DefaultTableModel());
    }

    /**
     * Controla el boton Listar llena la tabla de la vista si la tabla es mayor
     * que 0 (cero), completa los JtextFields con el primer objeto de la tabla
     * si la tabla esta avcia no muestra nada
     */
    public void btn_listar() {
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
        llenarTabla(vista.getTablaEmpleados());

        //Setea ancho de columna
        setAnchoColumna();

        //Carga el primer elemento si la lista es mayo a 1
        if (sizeTabla() >= 0) {
            //Si posee datos de direccion se cargan en la vista

            //Posicionar el cursor de la lista en el primer Elemento
            vista.getJtfID().setText(clientes.get(0).getId().toString());
            vista.getJtfCuitCuil().setText(clientes.get(0).getCuitCuil());
            vista.getJtfNombre().setText(clientes.get(0).getNombre());
            vista.getJtfApellido().setText(clientes.get(0).getApellido());
            if (clientes.get(0).getId().toString().equals(vista.getJtfID().getText())) {
                if (clientes.get(0).getDireccion() != null) {
                    if (clientes.get(0).getDireccion().getLocalidad() != null) {

                        vista.getJcb_zona_direccion().removeAllItems();
                        vista.getJcb_zona_direccion().addItem(clientes.get(0).getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                        vista.getJcb_provincia_direccion().removeAllItems();
                        vista.getJcb_provincia_direccion().addItem(clientes.get(0).getDireccion().getLocalidad().getProvincia().getNombre());

                        vista.getJcb_localidad_direccion().removeAllItems();
                        vista.getJcb_localidad_direccion().addItem(clientes.get(0).getDireccion().getLocalidad().getNombre());

                        vista.getJtf_calle_direccion().setText(clientes.get(0).getDireccion().getCalle());
                        vista.getJtf_numero_direccion().setText(clientes.get(0).getDireccion().getNumero());
                        vista.getJtf_piso_direccion().setText(clientes.get(0).getDireccion().getPiso());
                        vista.getJtf_departamento_direccion().setText(clientes.get(0).getDireccion().getDepartamento());

                        if (clientes.get(0).getTipocliente() != null) {
                            vista.getJcb_tipoCliente().addItem(clientes.get(0).getTipocliente().getDescripcion());
                        }

                        if (clientes.get(0).getCuentaCorriente() != null) {
                            vista.getJtf_ctaCte().setText(Float.toString(clientes.get(0).getCuentaCorriente().getSaldo()));
                        } else {
                            vista.getJtf_ctaCte().setText("SIN CUENTA CORRIENTE INICIADA");
                        }

                    } else {
                        System.out.println("No tiene Localidad");
                    }

                } else {
                    System.out.println("no tiene direccion");
                }
            }

            //posiciona en foco de la lista en el primer Empleado 
            vista.getTablaEmpleados().changeSelection(0, 1, false, false);
        } else {
            //Habilita Botones
            vista.habilitarBoton(false, vista.getJbtn_Modificar());
            vista.habilitarBoton(false, vista.getJbtn_Eliminar());
            limpiarTodosLosCampos();
            JOptionPane.showMessageDialog(null, "No hay cliente que listar");
        }

        //Posiciona la seleccion en el Panel datos clientes. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }

    /**
     * Controla el Boton Aceptar cuando se este creando crea instancia de
     * cliente, slo si existe localidad crea instancia de direccion y persiste,
     * solamente si el cliente no existe asocia la direccion al cliente persiste
     * cliente
     */
    public void btn_aceptarCrear() {
        boolean clienteCreado = false;

        if (vista.getJcb_localidad_direccion().getSelectedIndex() >= 0) {

            //Crear Instancia de Empleado
            Cliente cli = new Cliente();

            //setea DNI cliente
            cli.setCuitCuil(vista.getJtfCuitCuil().getText());

            //Crea Instancia de Direccion
            Direccion direccion = new Direccion();

            //setea Direccion
            direccion.setCalle(vista.getJtf_calle_direccion().getText());
            direccion.setNumero(vista.getJtf_numero_direccion().getText());
            direccion.setPiso(vista.getJtf_piso_direccion().getText());
            direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());

            //Instancia DireccionJpaController
            modeloDireccion = new DireccionJpaController(Conexion.getEmf());

            //Setea direccion con el campo JcomboBox de Objeto localidad
            direccion.setLocalidad((Localidad) vista.getJcb_localidad_direccion().getSelectedItem());

            //Instancia de TipoCliente
            TipoCliente unTipoCliente = new TipoCliente();

            //Instancia TipoClienteJpaController
            modeloTipoCliente = new TipoClienteJpaController(Conexion.getEmf());

            //Setea TipoClinte con lo que tiene seleccionado el JCB de TipoCliente
            unTipoCliente = (TipoCliente) vista.getJcb_tipoCliente().getSelectedItem();

            //Instancia una Cuenta Corriente
            CuentaCorriente unaCtaCorriente = new CuentaCorriente();

            //Intancia CuentaCorrienteJPAController
            modeloCuentaCorriente = new CuentaCorrienteJpaController(Conexion.getEmf());

            //Setea CuentaCorriente a saldo 
            unaCtaCorriente.setSaldo(0f);

            //Verificar si el DNI ingresado ya existe en la base de datos
            if (modelo.buscarClienteCuitCuil(cli) == null) {

                if (!clienteCreado) {
                    //Setea Datos de Empleado
                    cli.setNombre(vista.getJtfNombre().getText());
                    cli.setCuitCuil(vista.getJtfCuitCuil().getText());
                    cli.setApellido(vista.getJtfApellido().getText());

                    //Agrega la unidad al cliente
                    cli.setTipocliente(unTipoCliente);

                    //Persiste la Direccion
                    modeloDireccion.create(direccion);

                    //Agrega la direccion al cliente
                    cli.setDireccion(direccion);

                    //Persiste Cta Cte
                    modeloCuentaCorriente.create(unaCtaCorriente);

                    //Agrega CtaCte a cliente
                    cli.setCuentaCorriente(unaCtaCorriente);

                    //Agrega la empresa a la que pertenece el Cliente
                    cli.setUnaEmpresa(empresa);

                    //Persiste Empleado
                    modelo.create(cli);

                    //Bandera de cliente creado a verdadero
                    clienteCreado = true;

                    //Mensaje de cliente Guardado
                    JOptionPane.showMessageDialog(null, "Cliente Guardado");
                }

                if (clienteCreado) {
                    //llena la tabla de Empleados
                    llenarTabla(vista.getTablaEmpleados());

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
                    vista.getTablaEmpleados().changeSelection(sizeTabla(), 1, false, false);

                    //Da valor al ID en la tabla
                    vista.getJtfID().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(sizeTabla(), 1)));

                    //Posiciona la seleccion en el Panel datos clientes. 
                    vista.getjTabbedPaneContenedor().setSelectedIndex(0);

                    //Todos los botones de aceptar Bloqueados
                    bloquearAceptarCrear = false;
                    bloquearAceptarEliminar = false;
                    bloquearAceptarModificar = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "El Cliente ya existe o el DNI es invalido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar una Localidad");
        }

    }

    /**
     * Controla el Boton Aceptar cuando se este modificando crea instancia de
     * cliente, en funcion al ID seleccionado crea una instancia de direccion
     * del cliente verifica que el DNI del cliente es modificado, si lo es setea
     * el dni del cliente instanciado edita direccion edita cliente
     */
    public void btn_aceptarModificar() {
        boolean clienteModifocado = false;

        if (vista.getJcb_localidad_direccion().getSelectedIndex() >= 0) {
            //instancia de cliente igual al objeto guardado en Base de datos
            Cliente cli = modelo.findCliente(Long.parseLong(vista.getJtfID().getText()));

            //setea DNI cliente con nuevos valores
            cli.setCuitCuil(vista.getJtfCuitCuil().getText());

            //Instancia de Direccion
            Direccion direccion = new Direccion();

            //setea Direccion
            direccion.setId(cli.getDireccion().getId());
            direccion.setCalle(vista.getJtf_calle_direccion().getText());
            direccion.setNumero(vista.getJtf_numero_direccion().getText());
            direccion.setPiso(vista.getJtf_piso_direccion().getText());
            direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());

            //Instancia DireccionJpaController
            modeloDireccion = new DireccionJpaController(Conexion.getEmf());

            //Setea direccion con el campo JcomboBox de Objeto localidad
            direccion.setLocalidad((Localidad) vista.getJcb_localidad_direccion().getSelectedItem());

            //Instancia de Unidad
            TipoCliente unTipoCliente = new TipoCliente();

            //Instancia UnidadJpaController
            modeloTipoCliente = new TipoClienteJpaController(Conexion.getEmf());

            //Setea la Unidad con lo que tiene seleccionado el JCB de unidad
            unTipoCliente = (TipoCliente) vista.getJcb_tipoCliente().getSelectedItem();

            //Instancia TipoEClienteJpaController
            modeloTipoCliente = new TipoClienteJpaController(Conexion.getEmf());

            if (!clienteModifocado) {
                //verificar que el DNI sea igual que el DNI actual        
                if (cli.getCuitCuil().equals(cuitCuilModificado)) {
                    //El DNI es igual se mantiene sin cambio            
                    try {
                        //Setea Datos de Empleado
                        cli.setApellido(vista.getJtfApellido().getText());
                        cli.setNombre(vista.getJtfNombre().getText());

                        //Agrega unidad a cli
                        cli.setTipocliente(unTipoCliente);

                        //Edita Direccion
                        modeloDireccion.edit(direccion);

                        //agrega direccion a cli
                        cli.setDireccion(direccion);

                        //Persiste Empleado
                        modelo.edit(cli);
                        JOptionPane.showMessageDialog(null, "cliente modificado con DNI igual");

                        //Bandera de cliente creado a verdadero
                        clienteModifocado = true;

                    } catch (Exception ex) {
                        Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Si el DNI no es igual que el DNI actual
                } else //Verifica si existe el DNI, si no existe se procede
                if (modelo.buscarClienteCuitCuil(cli) == null) {
                    //Se modifica el DNI
                    cli.setCuitCuil(vista.getJtfCuitCuil().getText());
                    try {
                        //Setea Datos de Empleado
                        cli.setApellido(vista.getJtfApellido().getText());
                        cli.setNombre(vista.getJtfNombre().getText());

                        //Agrega unidad a cli
                        cli.setTipocliente(unTipoCliente);

                        //Edita Direccion
                        modeloDireccion.edit(direccion);

                        //agrega direccion a cli
                        cli.setDireccion(direccion);

                        //Persiste Empleado
                        modelo.edit(cli);
                        JOptionPane.showMessageDialog(null, "cliente y DNI modificado");

                        //Bandera de cliente creado a verdadero
                        clienteModifocado = true;

                    } catch (Exception ex) {
                        Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El DNI Ya existe, modifiquelo");
                }
            }
            if (clienteModifocado) {

                //llena la tabla de Empleados
                llenarTabla(vista.getTablaEmpleados());

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
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(cli.getId()), 1, false, false);

                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear = false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;

            }
        } else {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar una Localidad");
        }
    }

    /**
     * Controla el Boton Aceptar cuando se este eliminando crea instancia de
     * cliente, en funcion al ID seleccionado crea una instancia de direccion
     * del cliente verifica que el cliente tenga dreccion, si tiene direccion se
     * elimia la direccion del cliente, elimina direccion elimina cliente
     */
    public void btn_aceptarEliminar() {
        //Instancia DireccionJpaController
        modeloDireccion = new DireccionJpaController(Conexion.getEmf());

        //Instancia CuentaCorrienteJpaController
        modeloCuentaCorriente = new CuentaCorrienteJpaController(Conexion.getEmf());

        //instancia de cliente igual al objeto guardado en Base de datos
        Cliente cli = modelo.findCliente(Long.parseLong(vista.getJtfID().getText()));

        //Instancia direccion
        Direccion direccion = null;

        //Instancia cuentaCorriente
        CuentaCorriente cuentaCorriente = null;

        for (Cliente cliente : clientes) {
            if (cliente.equals(cli)) {
                direccion = cliente.getDireccion();
                cuentaCorriente = cliente.getCuentaCorriente();
            }
        }

        try {
            //elimina cliente
            if (direccion != null) {

                //Se elimina Empleado antes que la direccion por integridad referencia
                modelo.destroy(cli.getId());

                //Se elimina direccion sin asociacion
                modeloDireccion.destroy(direccion.getId());

                if (cuentaCorriente != null) {
                    //Se elimina la cuentaCorrienteAsociada
                    modeloCuentaCorriente.destroy(cuentaCorriente.getId());
                }

                JOptionPane.showMessageDialog(null, "cliente con direccion eliminados");

                //llena la tabla de Empleados
                llenarTabla(vista.getTablaEmpleados());

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
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(cli.getId()), 1, false, false);

                btn_listar();
                cuitCuilModificado = vista.getJtfCuitCuil().getText();

                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear = false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;
            } else {
                modelo.destroy(cli.getId());

                if (cuentaCorriente != null) {
                    //Se elimina la cuentaCorrienteAsociada
                    modeloCuentaCorriente.destroy(cuentaCorriente.getId());
                }

                JOptionPane.showMessageDialog(null, "cliente sin Direccion eliminado");

                //llena la tabla de Empleados
                llenarTabla(vista.getTablaEmpleados());

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
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(cli.getId()), 1, false, false);

                btn_listar();
                cuitCuilModificado = vista.getJtfCuitCuil().getText();

                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear = false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Controla el Boton Cancelar Bloquea el Boton AceptarCrear para poder crear
     * Bloquea el Boton AceptarEliminar para poder eliminar Bloquea el Boton
     * AceptarModificar para poder modificar
     */
    public void btn_cancelar() {
        //Limpiar Campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Volver());
        vista.habilitarBoton(true, vista.getJbtn_Listar());

        btn_listar();

        //Todos los botones de aceptar Bloqueados
        bloquearAceptarCrear = false;
        bloquearAceptarEliminar = false;
        bloquearAceptarModificar = false;
    }

    /**
     * Controla el Boton Volver Bloquea el Boton AceptarCrear para poder crear
     * Bloquea el Boton AceptarEliminar para poder eliminar Bloquea el Boton
     * AceptarModificar para poder modificar Habilita el Arbol del Panel
     * Principal
     */
    public void btn_volver() {
        //Limpia campos
        limpiarTodosLosCampos();

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Inhabilita Botones
        inhabilitarTodosLosBotones(false);

        //Limpia la lista            
        vista.getTablaEmpleados().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.habilitarArbol(true);

        //Todos los botones de aceptar Bloqueados
        bloquearAceptarCrear = false;
        bloquearAceptarEliminar = false;
        bloquearAceptarModificar = false;
    }

    /**
     * Llena Jtable de cliente crea una lista de clientes existentes en la base
     * de datos.
     *
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD) {
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

        for (Cliente cli : modelo.findClienteEntities()) {
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

    /**
     * Modifica la habilitación de los JtextField de la vista en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosCampos(boolean estado) {
        //inhabilita campos
        vista.habilitarCampo(estado, vista.getJtfID());
        vista.habilitarCampo(estado, vista.getJtfNombre());
        vista.habilitarCampo(estado, vista.getJtfApellido());
        vista.habilitarCampo(estado, vista.getJtfCuitCuil());
        vista.habilitarCampo(estado, vista.getJtf_ctaCte());

        vista.habilitarCampo(estado, vista.getJtf_calle_direccion());
        vista.habilitarCampo(estado, vista.getJtf_numero_direccion());
        vista.habilitarCampo(estado, vista.getJtf_piso_direccion());
        vista.habilitarCampo(estado, vista.getJtf_departamento_direccion());

        vista.habilitarCombobox(estado, vista.getJcb_zona_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_provincia_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_localidad_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_tipoCliente());
    }

    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos() {
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfNombre());
        vista.limpiarCampo(vista.getJtfApellido());
        vista.limpiarCampo(vista.getJtfCuitCuil());
        vista.limpiarCampo(vista.getJtf_ctaCte());

        vista.limpiarCampo(vista.getJtf_calle_direccion());
        vista.limpiarCampo(vista.getJtf_numero_direccion());
        vista.limpiarCampo(vista.getJtf_piso_direccion());
        vista.limpiarCampo(vista.getJtf_departamento_direccion());

        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_zona_direccion());
        vista.limpiarCombobox(vista.getJcb_provincia_direccion());
        vista.limpiarCombobox(vista.getJcb_localidad_direccion());
        vista.limpiarCombobox(vista.getJcb_tipoCliente());

    }

    /**
     * Modifica la habilitación de los Botones de la vista en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado) {
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
    public void politicaValidacionDeCampos() {
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtfApellido());
        vista.getValidador().LimitarCaracteres(vista.getJtfApellido(), 30);

        vista.getValidador().validarSoloLetras(vista.getJtfNombre());
        vista.getValidador().LimitarCaracteres(vista.getJtfNombre(), 30);

        vista.getValidador().validarSoloNumero(vista.getJtfCuitCuil());
        vista.getValidador().LimitarCaracteres(vista.getJtfCuitCuil(), 10);

        vista.getValidador().LimitarCaracteres(vista.getJtf_calle_direccion(), 30);

        //vista.getValidador().validarSoloNumero(vista.getJtf_numero_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_numero_direccion(), 5);

        //vista.getValidador().validarSoloNumero(vista.getJtf_piso_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_piso_direccion(), 2);

        vista.getValidador().LimitarCaracteres(vista.getJtf_departamento_direccion(), 2);
    }

    /**
     * Establece el Ancho de cada columna de la tabla cliente de la vista.
     */
    public void setAnchoColumna() {
        TableColumnModel columnModel = vista.getTablaEmpleados().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(150);
    }

    /**
     * Busca la posicion que ocupa un cliente en la tabla cliente
     *
     * @param id de Empleado
     * @return posicion de cliente
     */
    public int buscarPosicionEnTabla(Long id) {
        int posicion = 0;
        for (Cliente cli : modelo.findClienteEntities()) {
            if (id.equals(cli.getId())) {
                return posicion;
            }
            posicion++;
        }

        return posicion;
    }

    /**
     * informa el tamaño de la tabla clientes
     *
     * @return tamaño de la tabla
     */
    public int sizeTabla() {
        int posicion = 0;
        for (Cliente cli : modelo.findClienteEntities()) {
            posicion++;
        }
        return posicion - 1;
    }

    /**
     * llena el JcomboBox de Zona con objetos Zona de la base de datos
     */
    public void llenarJcomboboxZona() {
        modeloZona = new ZonaJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloZona.findZonaEntities());
        vista.getJcb_zona_direccion().setModel(mdl);
        this.zBuscada = (Zona) vista.getJcb_zona_direccion().getSelectedItem();
    }

    /**
     * llena el JcomboBox de Provincia con objetos Provincia de la base de datos
     * en funcion a un objeto Zona
     *
     * @param z Zona
     */
    public void llenarJcomboboxProvincia(Zona z) {
        modeloProvincia = new ProvinciaJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloProvincia.buscarProvinciasPorZona(z));
        vista.getJcb_provincia_direccion().setModel(mdl);
        this.pBuscada = (Provincia) vista.getJcb_provincia_direccion().getSelectedItem();
    }

    /**
     * llena el JcomboBox de Localidad con objetos Localidad de la base de datos
     * en funcion a un objeto Provincia
     *
     * @param p Provincia
     */
    public void llenarJcomboboxLocalidad(Provincia p) {
        modeloLocalidad = new LocalidadJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloLocalidad.buscarLocalidadPorProvincia(p));
        vista.getJcb_localidad_direccion().setModel(mdl);
    }

    public void llenarJcomboboxTipoCliente() {
        modeloTipoCliente = new TipoClienteJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloTipoCliente.findTipoClienteEntities());
        vista.getJcb_tipoCliente().setModel(mdl);
        this.tcBuscada = (TipoCliente) vista.getJcb_tipoCliente().getSelectedItem();
    }

    /**
     * Verifica el cambio de estado en los JComboBox
     *
     * @param e Evento de cambio de JCOMBOBOX
     */
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

    /**
     * Verifica el Foco ganado por los elementos de la vista
     *
     * @param e Evento de foco Ganado
     */
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
        if (e.getSource().equals(vista.getJtfCuitCuil())) {
            cuitCuilModificado = vista.getJtfCuitCuil().getText();
        }
        if (e.getSource() == vista.getJcb_tipoCliente()) {
            this.tcSeleccionada = true;
        }

    }

    /**
     * Verifica el Foco perdido por los elementos de la vista
     *
     * @param e Evento de Foco Perdido
     */
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
        if (e.getSource().equals(vista.getJcb_tipoCliente())) {
            tcSeleccionada = false;
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

    /**
     * Verifica los eventos de click realizados en la tabla de clientes si
     * cambia se completan los datos del cliente
     *
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //carga los datos en la vista si cualquiera de las variables es verdadera
        //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
        int seleccion = vista.getTablaEmpleados().rowAtPoint(e.getPoint());
        vista.getJtfID().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 1)));
        vista.getJtfCuitCuil().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 2)));
        vista.getJtfNombre().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 3)));
        vista.getJtfApellido().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 4)));

        //Si posee datos de direccion se cargan en la vista
        for (Cliente cliente : clientes) {
            if (cliente.getId().toString().equals(vista.getJtfID().getText())) {
                if (cliente.getDireccion() != null) {
                    if (cliente.getDireccion().getLocalidad() != null) {
                        vista.getJcb_zona_direccion().removeAllItems();
                        vista.getJcb_zona_direccion().addItem(cliente.getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                        vista.getJcb_provincia_direccion().removeAllItems();
                        vista.getJcb_provincia_direccion().addItem(cliente.getDireccion().getLocalidad().getProvincia().getNombre());

                        vista.getJcb_localidad_direccion().removeAllItems();
                        vista.getJcb_localidad_direccion().addItem(cliente.getDireccion().getLocalidad().getNombre());

                        if (cliente.getTipocliente() != null) {
                            vista.getJcb_tipoCliente().removeAllItems();
                            vista.getJcb_tipoCliente().addItem(cliente.getTipocliente().getDescripcion());
                        }
                        if (cliente.getCuentaCorriente() != null) {
                            vista.getJtf_ctaCte().setText(Float.toString(cliente.getCuentaCorriente().getSaldo()));
                        } else {
                            vista.getJtf_ctaCte().setText("SIN CUENTA CORRIENTE INICIADA");
                        }

                        vista.getJtf_calle_direccion().setText(cliente.getDireccion().getCalle());
                        vista.getJtf_numero_direccion().setText(cliente.getDireccion().getNumero());
                        vista.getJtf_piso_direccion().setText(cliente.getDireccion().getPiso());
                        vista.getJtf_departamento_direccion().setText(cliente.getDireccion().getDepartamento());

                    } else {
                        JOptionPane.showMessageDialog(null, "cliente no tiene Localidad asignada");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "cliente no tiene Direccion asignada");
                }
            }
        }
        //Posiciona la seleccion en el Panel datos clientes. 
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
