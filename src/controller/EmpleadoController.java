package controller;

import model.JPAController.EmpleadoJpaController;
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
import model.Direccion;
import model.Empleado;
import model.JPAController.DireccionJpaController;
import model.JPAController.LocalidadJpaController;
import model.JPAController.ProvinciaJpaController;
import model.JPAController.TipoEmpleadoJpaController;
import model.JPAController.TipoUsuarioJpaController;
import model.JPAController.UnidadJpaController;
import model.JPAController.UsuarioJpaController;
import model.JPAController.ZonaJpaController;
import model.Localidad;
import model.Provincia;
import model.TipoEmpleado;
import model.TipoUsuario;
import model.Unidad;
import model.Usuario;
import model.Zona;
import view.JframePrincipal;
import view.PanelRegistroEmpleado;

/**
 * Clase controladora de Empleado
 *
 * @author Ariel
 */
public class EmpleadoController extends Controller {

    private PanelRegistroEmpleado vista;
    private EmpleadoJpaController modelo;

    private ZonaJpaController modeloZona;
    private DireccionJpaController modeloDireccion;
    private ProvinciaJpaController modeloProvincia;
    private LocalidadJpaController modeloLocalidad;
    private UnidadJpaController modeloUnidad;
    private TipoEmpleadoJpaController modeloTipoEmpleado;

    //Modelo Usuario y Tipo Usuario
    private TipoUsuario unTipoUsuario;
    private Usuario unUsuario;

    //JpaController TipoUsuario y Usuario
    private TipoUsuarioJpaController modeloTipoUsuario;
    private UsuarioJpaController modeloUsuario;

    boolean bloquearAceptarCrear = false;
    boolean bloquearAceptarEliminar = false;
    boolean bloquearAceptarModificar = false;

    Zona zBuscada = null;
    Provincia pBuscada = null;
    Localidad lBuscada = null;

    Unidad uBuscada = null;

    boolean zSeleccionada = false;
    boolean pSeleccionada = false;
    boolean lSeleccionada = false;
    boolean uSeleccionada = false;

    String dniModificado = null;

    int ultimoIndiceSeleccionado = 0;
    List<Empleado> empleados;

    /**
     * Constructor Empleado
     *
     * @param vista PanelRegistroEmpleado
     * @param modelo EmpleadoJpaController
     */
    public EmpleadoController(PanelRegistroEmpleado vista, EmpleadoJpaController modelo) {
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
        //Posiciona la seleccion en el Panel datos empleados. 
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
        vista.getTablaEmpleados().changeSelection(sizeTabla(), 1, false, false);

        //inicializa el JcomboBox
        llenarJcomboboxZona();
        llenarJcomboboxProvincia(zBuscada);
        llenarJcomboboxLocalidad(pBuscada);

        //Llena El JComboBox de unidad
        llenarJcomboboxUnidad();
        llenarJcomboboxTipoEmpleado(uBuscada);
        //desbloquea el boton nuevo
        bloquearAceptarCrear = true;

        //Llena Tipo Empleado
        llenarTipoUsuario();

        //Limpia la lista            
        vista.getTablaEmpleados().setModel(new DefaultTableModel());
    }

    /**
     * Controla el Boton Modificar Desbloquea el Boton AceptarModificar para
     * poder modificar
     */
    public void btn_modificar() {

        //Posiciona la seleccion en el Panel datos empleados. 
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

        //Crea instancia de empleado
        Empleado empModificado = new Empleado();

        //setea empleado en funcion al ID de la vista
        empModificado = modelo.findEmpleado(Long.parseLong(vista.getJtfID().getText()));

        //Llena el combobox de Zona
        llenarJcomboboxZona();
        //Posiciona dentro del combobox zona al objeto zona que posee el empleado.
        vista.getJcb_zona_direccion().setSelectedItem(empModificado.getDireccion().getLocalidad().getProvincia().getZona());

        //Llena el combobox de provincia
        llenarJcomboboxProvincia(empModificado.getDireccion().getLocalidad().getProvincia().getZona());
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del empleado.
        vista.getJcb_provincia_direccion().setSelectedItem(empModificado.getDireccion().getLocalidad().getProvincia());

        //Llena el combobox de Localidad
        llenarJcomboboxLocalidad(empModificado.getDireccion().getLocalidad().getProvincia());
        //Posiciona dentro del combobox Localidad al objeto Localidad dentro de Provincia dentro zona que posee el empleado.
        vista.getJcb_localidad_direccion().setSelectedItem(empModificado.getDireccion().getLocalidad());

        //Llena El JComboBox de unidad
        llenarJcomboboxUnidad();
        //Posiciona dentro del combobox Unidad al objeto Unidad  que posee el empleado.
        vista.getJcb_unidad().setSelectedItem(empModificado.getUnidad());

        //Llena El JComboBox de Tipo Empleado
        llenarJcomboboxTipoEmpleado(empModificado.getUnidad());
        //Posiciona dentro del combobox Unidad al objeto Unidad  que posee el empleado.
        vista.getJcb_empleado().setSelectedItem(empModificado.getTipoEmpleado());

        //dniModificado incializa
        dniModificado = vista.getJtfDNI().getText();

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

        //Posiciona la seleccion en el Panel datos empleados. 
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

        //Crea instancia de empleado
        Empleado empAEliminar = new Empleado();

        //setea empleado en funcion al ID de la vista
        empAEliminar = modelo.findEmpleado(Long.parseLong(vista.getJtfID().getText()));

        //Llena el combobox de Zona
        llenarJcomboboxZona();
        //Posiciona dentro del combobox zona al objeto zona que posee el empleado.
        vista.getJcb_zona_direccion().setSelectedItem(empAEliminar.getDireccion().getLocalidad().getProvincia().getZona());

        //Llena el combobox de provincia
        llenarJcomboboxProvincia(empAEliminar.getDireccion().getLocalidad().getProvincia().getZona());
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del empleado.
        vista.getJcb_provincia_direccion().setSelectedItem(empAEliminar.getDireccion().getLocalidad().getProvincia());

        //Llena el combobox de Localidad
        llenarJcomboboxLocalidad(empAEliminar.getDireccion().getLocalidad().getProvincia());
        //Posiciona dentro del combobox Localidad al objeto Localidad dentro de Provincia dentro zona que posee el empleado.
        vista.getJcb_localidad_direccion().setSelectedItem(empAEliminar.getDireccion().getLocalidad());

        //Llena El JComboBox de unidad
        llenarJcomboboxUnidad();
        //Posiciona dentro del combobox Unidad al objeto Unidad  que posee el empleado.
        vista.getJcb_unidad().setSelectedItem(empAEliminar.getUnidad());

        //Llena El JComboBox de Tipo Empleado
        llenarJcomboboxTipoEmpleado(empAEliminar.getUnidad());
        //Posiciona dentro del combobox Unidad al objeto Unidad  que posee el empleado.
        vista.getJcb_empleado().setSelectedItem(empAEliminar.getTipoEmpleado());

        //dniModificado incializa
        dniModificado = vista.getJtfDNI().getText();

        //dniModificado incializa
        dniModificado = vista.getJtfDNI().getText();

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
            vista.getJtfID().setText(empleados.get(0).getId().toString());
            vista.getJtfDNI().setText(empleados.get(0).getDni());
            vista.getJtfNombre().setText(empleados.get(0).getNombre());
            vista.getJtfApellido().setText(empleados.get(0).getApellido());
            if (empleados.get(0).getId().toString().equals(vista.getJtfID().getText())) {
                if (empleados.get(0).getDireccion() != null) {
                    if (empleados.get(0).getDireccion().getLocalidad() != null) {

                        vista.getJcb_zona_direccion().removeAllItems();
                        vista.getJcb_zona_direccion().addItem(empleados.get(0).getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                        vista.getJcb_provincia_direccion().removeAllItems();
                        vista.getJcb_provincia_direccion().addItem(empleados.get(0).getDireccion().getLocalidad().getProvincia().getNombre());

                        vista.getJcb_localidad_direccion().removeAllItems();
                        vista.getJcb_localidad_direccion().addItem(empleados.get(0).getDireccion().getLocalidad().getNombre());

                        vista.getJtf_calle_direccion().setText(empleados.get(0).getDireccion().getCalle());
                        vista.getJtf_numero_direccion().setText(empleados.get(0).getDireccion().getNumero());
                        vista.getJtf_piso_direccion().setText(empleados.get(0).getDireccion().getPiso());
                        vista.getJtf_departamento_direccion().setText(empleados.get(0).getDireccion().getDepartamento());

                        vista.getFechaIngreso().setDate(empleados.get(0).getFechaIngreso());

                        if (empleados.get(0).getUnidad() != null) {
                            vista.getJcb_unidad().addItem(empleados.get(0).getUnidad().getNombre());
                        }
                        if (empleados.get(0).getTipoEmpleado() != null) {
                            vista.getJcb_empleado().addItem(empleados.get(0).getTipoEmpleado().getDescripcion());
                        }
                        if (empleados.get(0).getUnUsuario() != null) {
                            vista.getJtf_Usuario().setText(empleados.get(0).getUnUsuario().getNombre());
                            vista.getJtf_Password().setText(empleados.get(0).getUnUsuario().getClave());

                            vista.getJcb_TipoUsuario().addItem(empleados.get(0).getUnUsuario().getTipoUsuario().getDescripcion());
                        } else {
                            System.out.println("No tiene Usuario Asignado");
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
            JOptionPane.showMessageDialog(null, "No hay empleados que listar");
        }

        //Posiciona la seleccion en el Panel datos empleados. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }

    /**
     * Controla el Boton Aceptar cuando se este creando crea instancia de
     * empleado, slo si existe localidad crea instancia de direccion y persiste,
     * solamente si el empleado no existe asocia la direccion al empleado
     * persiste empleado
     */
    public void btn_aceptarCrear() {
        boolean empleadoCreado = false;

        if (vista.getJcb_localidad_direccion().getSelectedIndex() >= 0) {

            //Crear Instancia de Empleado
            Empleado emp = new Empleado();

            //Crea Instacia de Usuario y Tipo Usuario
            Usuario nuevoUsuario = new Usuario();
            TipoUsuario nuevoTipoUsuario = new TipoUsuario();

            modeloUsuario = new UsuarioJpaController(Conexion.getEmf());

            nuevoTipoUsuario = (TipoUsuario) vista.getJcb_TipoUsuario().getSelectedItem();

            nuevoUsuario.setTipoUsuario(nuevoTipoUsuario);

            nuevoUsuario.setNombre(vista.getJtf_Usuario().getText());
            nuevoUsuario.setClave(vista.getJtf_Password().getText());

            //setea DNI empleado
            emp.setDni(vista.getJtfDNI().getText());

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

            //Instancia de Unidad
            Unidad unaUnidad = new Unidad();

            //Instancia UnidadJpaController
            modeloUnidad = new UnidadJpaController(Conexion.getEmf());

            //Setea la Unidad con lo que tiene seleccionado el JCB de unidad
            unaUnidad = (Unidad) vista.getJcb_unidad().getSelectedItem();

            //Instancia de TipoEmpleado
            TipoEmpleado unTipoEmpleado = new TipoEmpleado();

            //Instancia TipoEmpleadoJpaController
            modeloTipoEmpleado = new TipoEmpleadoJpaController(Conexion.getEmf());

            //Setea la Unidad con lo que tiene seleccionado el JCB de unidad
            unTipoEmpleado = (TipoEmpleado) vista.getJcb_empleado().getSelectedItem();

            //Verificar si el DNI ingresado ya existe en la base de datos
            if (modelo.buscarEmpleadoDNI(emp) == null) {

                if (!empleadoCreado) {
                    //Verificar si el objeto usuario no existe, en funcion del nombre
                    if (modeloUsuario.verificarUsuario(nuevoUsuario) == null) {

                        //Si la clave es vacia la completa con ceros
                        if (nuevoUsuario.getClave().equals("")) {
                            nuevoUsuario.setClave("00000000");
                        }
                        emp.setFechaIngreso(vista.getFechaIngreso().getDate());

                        //Setea Datos de Empleado
                        emp.setNombre(vista.getJtfNombre().getText());
                        emp.setDni(vista.getJtfDNI().getText());
                        emp.setApellido(vista.getJtfApellido().getText());

                        //Setea TipoEmpleado
                        emp.setTipoEmpleado(unTipoEmpleado);

                        //Agrega la unidad al empleado
                        emp.setUnidad(unaUnidad);

                        //Persiste la Direccion
                        modeloDireccion.create(direccion);

                        //Agrega la direccion al empleado
                        emp.setDireccion(direccion);

                        emp.setUnUsuario(nuevoUsuario);

                        //Persiste nuevo usuario
                        modeloUsuario.create(nuevoUsuario);

                        //Persiste Empleado
                        modelo.create(emp);

                        //Bandera de empleado creado a verdadero
                        empleadoCreado = true;

                        //Mensaje de empleado Guardado
                        JOptionPane.showMessageDialog(null, "Empleado Guardado");
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario ya existente");
                    }
                }

                //Si cumple con el IF anterior se cumple este IF
                if (empleadoCreado) {
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

                    //Posiciona la seleccion en el Panel datos empleados. 
                    vista.getjTabbedPaneContenedor().setSelectedIndex(0);

                    //Todos los botones de aceptar Bloqueados
                    bloquearAceptarCrear = false;
                    bloquearAceptarEliminar = false;
                    bloquearAceptarModificar = false;
                }

            } else {
                JOptionPane.showMessageDialog(null, "El Empleado ya existe o el DNI es invalido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tiene que ingresar una Localidad");
        }

    }

    /**
     * Controla el Boton Aceptar cuando se este modificando crea instancia de
     * empleado, en funcion al ID seleccionado crea una instancia de direccion
     * del empleado verifica que el DNI del empleado es modificado, si lo es
     * setea el dni del empleado instanciado edita direccion edita empleado
     */
    public void btn_aceptarModificar() {
        boolean empleadoModifocado = false;

        if (vista.getJcb_localidad_direccion().getSelectedIndex() >= 0) {
            //instancia de empleado igual al objeto guardado en Base de datos
            Empleado emp = modelo.findEmpleado(Long.parseLong(vista.getJtfID().getText()));

            modeloUsuario = new UsuarioJpaController(Conexion.getEmf());

            //setea DNI empleado con nuevos valores
            emp.setDni(vista.getJtfDNI().getText());

            //Instancia de Direccion
            Direccion direccion = new Direccion();

            //setea Direccion
            direccion.setId(emp.getDireccion().getId());
            direccion.setCalle(vista.getJtf_calle_direccion().getText());
            direccion.setNumero(vista.getJtf_numero_direccion().getText());
            direccion.setPiso(vista.getJtf_piso_direccion().getText());
            direccion.setDepartamento(vista.getJtf_departamento_direccion().getText());

            //Instancia DireccionJpaController
            modeloDireccion = new DireccionJpaController(Conexion.getEmf());

            //Setea direccion con el campo JcomboBox de Objeto localidad
            direccion.setLocalidad((Localidad) vista.getJcb_localidad_direccion().getSelectedItem());

            //Instancia de Unidad
            Unidad unaUnidad = new Unidad();

            //Instancia UnidadJpaController
            modeloUnidad = new UnidadJpaController(Conexion.getEmf());

            //Setea la Unidad con lo que tiene seleccionado el JCB de unidad
            unaUnidad = (Unidad) vista.getJcb_unidad().getSelectedItem();

            //Instancia de TipoEmpleado
            TipoEmpleado unTipoEmpleado = new TipoEmpleado();

            //Instancia TipoEmpleadoJpaController
            modeloTipoEmpleado = new TipoEmpleadoJpaController(Conexion.getEmf());

            //Setea la Unidad con lo que tiene seleccionado el JCB de unidad
            unTipoEmpleado = (TipoEmpleado) vista.getJcb_empleado().getSelectedItem();

            if (!empleadoModifocado) {
                //verificar que el DNI sea igual que el DNI actual        
                if (emp.getDni().equals(dniModificado)) {
                    //El DNI es igual se mantiene sin cambio            
                    try {

                        //Setea fecha de ingreso de Empleado
                        emp.setFechaIngreso(vista.getFechaIngreso().getDate());

                        //Setea Datos de Empleado
                        emp.setApellido(vista.getJtfApellido().getText());
                        emp.setNombre(vista.getJtfNombre().getText());

                        //Agrega tipo Empleado a emp
                        emp.setTipoEmpleado(unTipoEmpleado);

                        //Agrega unidad a emp
                        emp.setUnidad(unaUnidad);

                        //Edita Direccion
                        modeloDireccion.edit(direccion);

                        //agrega direccion a emp
                        emp.setDireccion(direccion);

                        //Persiste Empleado
                        modelo.edit(emp);

                        JOptionPane.showMessageDialog(null, "empleado modificado con DNI igual");

                        //Bandera de empleado creado a verdadero
                        empleadoModifocado = true;

                        //aca
                    } catch (Exception ex) {
                        Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //Si el DNI no es igual que el DNI actual
                } else //Verifica si existe el DNI, si no existe se procede
                if (modelo.buscarEmpleadoDNI(emp) == null) {
                    //Se modifica el DNI
                    emp.setDni(vista.getJtfDNI().getText());
                    try {
                        //Setea fecha de ingreso de empleado
                        emp.setFechaIngreso(vista.getFechaIngreso().getDate());

                        //Setea Datos de Empleado
                        emp.setApellido(vista.getJtfApellido().getText());
                        emp.setNombre(vista.getJtfNombre().getText());

                        //Agrega tipo Empleado a emp
                        emp.setTipoEmpleado(unTipoEmpleado);

                        //Agrega unidad a emp
                        emp.setUnidad(unaUnidad);

                        //Edita Direccion
                        modeloDireccion.edit(direccion);

                        //agrega direccion a emp
                        emp.setDireccion(direccion);

                        //Persiste Empleado
                        modelo.edit(emp);
                        JOptionPane.showMessageDialog(null, "empleado y DNI modificado");

                        //Bandera de empleado creado a verdadero
                        empleadoModifocado = true;

                    } catch (Exception ex) {
                        Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El DNI Ya existe, modifiquelo");
                }
            }
            if (empleadoModifocado) {

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
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

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
     * empleado, en funcion al ID seleccionado crea una instancia de direccion
     * del empleado verifica que el empleado tenga dreccion, si tiene direccion
     * se elimia la direccion del empleado, elimina direccion elimina empleado
     */
    public void btn_aceptarEliminar() {
        modeloDireccion = new DireccionJpaController(Conexion.getEmf());
        //instancia de empleado igual al objeto guardado en Base de datos
        Empleado emp = modelo.findEmpleado(Long.parseLong(vista.getJtfID().getText()));
        Direccion direccion = null;

        for (Empleado empleado : empleados) {
            if (empleado.equals(emp)) {
                direccion = empleado.getDireccion();
            }
        }

        try {
            //elimina empleado
            if (direccion != null) {
                //Se elimina Empleado antes que la direccion por integridad referencia
                modelo.destroy(emp.getId());
                //Se elimina direccion sin asociacion
                modeloDireccion.destroy(direccion.getId());

                JOptionPane.showMessageDialog(null, "empleado con direccion eliminados");

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
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

                btn_listar();
                dniModificado = vista.getJtfDNI().getText();

                //Todos los botones de aceptar Bloqueados
                bloquearAceptarCrear = false;
                bloquearAceptarEliminar = false;
                bloquearAceptarModificar = false;
            } else {
                modelo.destroy(emp.getId());
                JOptionPane.showMessageDialog(null, "empleado sin Direccion eliminado");

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
                vista.getTablaEmpleados().changeSelection(buscarPosicionEnTabla(emp.getId()), 1, false, false);

                btn_listar();
                dniModificado = vista.getJtfDNI().getText();

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
     * Llena Jtable de empleado crea una lista de empleados existentes en la
     * base de datos.
     *
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD) {
        empleados = new ArrayList<Empleado>();

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
        modeloT.addColumn("DNI");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellido");

        //Cantidad de columnas 
        Object[] columna = new Object[6];

        int numero = 0;

        for (Empleado emp : modelo.findEmpleadoEntities()) {
            //Guarda en Lista de empleados            
            empleados.add(emp);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);
            columna[1] = emp.getId();
            columna[2] = emp.getDni();
            columna[3] = emp.getNombre();
            columna[4] = emp.getApellido();

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
        vista.habilitarCampo(estado, vista.getJtfDNI());

        vista.habilitarCampo(estado, vista.getJtf_calle_direccion());
        vista.habilitarCampo(estado, vista.getJtf_numero_direccion());
        vista.habilitarCampo(estado, vista.getJtf_piso_direccion());
        vista.habilitarCampo(estado, vista.getJtf_departamento_direccion());

        vista.habilitarCombobox(estado, vista.getJcb_zona_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_provincia_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_localidad_direccion());
        vista.habilitarCombobox(estado, vista.getJcb_unidad());
        vista.habilitarCombobox(estado, vista.getJcb_empleado());

        vista.getFechaIngreso().setEnabled(estado);

        //Usuario y tipo de Usuario
        vista.habilitarCampo(estado, vista.getJtf_Usuario());
        vista.habilitarCampo(estado, vista.getJtf_Password());

        vista.habilitarCombobox(estado, vista.getJcb_TipoUsuario());

    }

    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos() {
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfNombre());
        vista.limpiarCampo(vista.getJtfApellido());
        vista.limpiarCampo(vista.getJtfDNI());

        vista.limpiarCampo(vista.getJtf_calle_direccion());
        vista.limpiarCampo(vista.getJtf_numero_direccion());
        vista.limpiarCampo(vista.getJtf_piso_direccion());
        vista.limpiarCampo(vista.getJtf_departamento_direccion());

        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_zona_direccion());
        vista.limpiarCombobox(vista.getJcb_provincia_direccion());
        vista.limpiarCombobox(vista.getJcb_localidad_direccion());
        vista.limpiarCombobox(vista.getJcb_unidad());
        vista.limpiarCombobox(vista.getJcb_empleado());

        vista.limpiarCampo(vista.getFechaIngreso());

        //Usuario y Tipo Usuario
        vista.limpiarCampo(vista.getJtf_Usuario());
        vista.limpiarCampo(vista.getJtf_Password());

        vista.limpiarCombobox(vista.getJcb_TipoUsuario());
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

        vista.getValidador().validarSoloNumero(vista.getJtfDNI());
        vista.getValidador().LimitarCaracteres(vista.getJtfDNI(), 8);

        vista.getValidador().LimitarCaracteres(vista.getJtf_calle_direccion(), 30);

        //vista.getValidador().validarSoloNumero(vista.getJtf_numero_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_numero_direccion(), 5);

        //vista.getValidador().validarSoloNumero(vista.getJtf_piso_direccion());
        vista.getValidador().LimitarCaracteres(vista.getJtf_piso_direccion(), 2);

        vista.getValidador().LimitarCaracteres(vista.getJtf_departamento_direccion(), 2);

        //Usuario
        vista.getValidador().LimitarCaracteres(vista.getJtf_Usuario(), 8);
        vista.getValidador().validarSoloLetras(vista.getJtf_Usuario());
        vista.getValidador().LimitarCaracteres(vista.getJtf_Password(), 8);

    }

    /**
     * Establece el Ancho de cada columna de la tabla empleado de la vista.
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
     * Busca la posicion que ocupa un empleado en la tabla empleado
     *
     * @param id de Empleado
     * @return posicion de empleado
     */
    public int buscarPosicionEnTabla(Long id) {
        int posicion = 0;
        for (Empleado emp : modelo.findEmpleadoEntities()) {
            if (id.equals(emp.getId())) {
                return posicion;
            }
            posicion++;
        }

        return posicion;
    }

    /**
     * informa el tamaño de la tabla empleados
     *
     * @return tamaño de la tabla
     */
    public int sizeTabla() {
        int posicion = 0;
        for (Empleado emp : modelo.findEmpleadoEntities()) {
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

    public void llenarJcomboboxUnidad() {
        modeloUnidad = new UnidadJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloUnidad.findUnidadEntities());
        vista.getJcb_unidad().setModel(mdl);
        this.uBuscada = (Unidad) vista.getJcb_unidad().getSelectedItem();
    }

    public void llenarJcomboboxTipoEmpleado(Unidad u) {
        modeloTipoEmpleado = new TipoEmpleadoJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloTipoEmpleado.buscarTipoEmpleadoPorUnidad(u));
        vista.getJcb_empleado().setModel(mdl);
    }

    public void llenarTipoUsuario() {
        modeloTipoUsuario = new TipoUsuarioJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloTipoUsuario.findTipoUsuarioEntities());
        vista.getJcb_TipoUsuario().setModel(mdl);
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
            if (this.uSeleccionada) {
                this.uBuscada = (Unidad) vista.getJcb_unidad().getSelectedItem();
                llenarJcomboboxTipoEmpleado(uBuscada);
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
        if (e.getSource().equals(vista.getJtfDNI())) {
            dniModificado = vista.getJtfDNI().getText();
        }
        if (e.getSource() == vista.getJcb_unidad()) {
            this.uSeleccionada = true;
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
        if (e.getSource().equals(vista.getJcb_unidad())) {
            uSeleccionada = false;
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
     * Verifica los eventos de click realizados en la tabla de empleados si
     * cambia se completan los datos del empleado
     *
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //carga los datos en la vista si cualquiera de las variables es verdadera
        //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
        int seleccion = vista.getTablaEmpleados().rowAtPoint(e.getPoint());
        vista.getJtfID().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 1)));
        vista.getJtfDNI().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 2)));
        vista.getJtfNombre().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 3)));
        vista.getJtfApellido().setText(String.valueOf(vista.getTablaEmpleados().getValueAt(seleccion, 4)));

        //Si posee datos de direccion se cargan en la vista
        for (Empleado empleado : empleados) {
            if (empleado.getId().toString().equals(vista.getJtfID().getText())) {
                if (empleado.getDireccion() != null) {
                    if (empleado.getDireccion().getLocalidad() != null) {
                        vista.getJcb_zona_direccion().removeAllItems();
                        vista.getJcb_zona_direccion().addItem(empleado.getDireccion().getLocalidad().getProvincia().getZona().getNombre());

                        vista.getJcb_provincia_direccion().removeAllItems();
                        vista.getJcb_provincia_direccion().addItem(empleado.getDireccion().getLocalidad().getProvincia().getNombre());

                        vista.getJcb_localidad_direccion().removeAllItems();
                        vista.getJcb_localidad_direccion().addItem(empleado.getDireccion().getLocalidad().getNombre());

                        vista.getJcb_unidad().removeAllItems();
                        vista.getJcb_unidad().addItem(empleado.getUnidad().getNombre());

                        vista.getJcb_empleado().removeAllItems();
                        vista.getJcb_empleado().addItem(empleado.getTipoEmpleado().getDescripcion());

                        vista.getJtf_calle_direccion().setText(empleado.getDireccion().getCalle());
                        vista.getJtf_numero_direccion().setText(empleado.getDireccion().getNumero());
                        vista.getJtf_piso_direccion().setText(empleado.getDireccion().getPiso());
                        vista.getJtf_departamento_direccion().setText(empleado.getDireccion().getDepartamento());

                        vista.getFechaIngreso().setDate(empleado.getFechaIngreso());

                        if (empleado.getUnUsuario() != null) {
                            vista.getJtf_Usuario().setText(empleado.getUnUsuario().getNombre());
                            vista.getJtf_Password().setText(empleado.getUnUsuario().getClave());
                            vista.getJcb_TipoUsuario().removeAllItems();
                            vista.getJcb_TipoUsuario().addItem(empleado.getUnUsuario().getTipoUsuario().getDescripcion());
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "empleado no tiene Localidad asignada");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "empleado no tiene Direccion asignada");
                }
            }
        }
        //Posiciona la seleccion en el Panel datos empleados. 
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
