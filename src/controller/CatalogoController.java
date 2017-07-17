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
import model.Articulo;
import model.CategoriaDeCatalogo;
import model.Empresa;
import model.JPAController.ArticuloJpaController;
import model.JPAController.CategoriaArticuloJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.ProveedorJpaController;
import model.Proveedor;
import view.JframePrincipal;
import view.PanelRegistroCatalogoArticulo;

/**
 * Clase controladora de Articulos de categorias en el Catalogo
 *
 * @author Ariel
 */
public class CatalogoController extends Controller {

    private PanelRegistroCatalogoArticulo vista;
    private ArticuloJpaController modelo;

    private EmpresaJpaController modeloEmpresa;
    private CategoriaArticuloJpaController modeloCategoriaArticulo;
    private ProveedorJpaController modeloProveedor;

    boolean bloquearAceptarCrear = false;
    boolean bloquearAceptarEliminar = false;
    boolean bloquearAceptarModificar = false;

    Empresa empresa = null;

    boolean zSeleccionada = false;
    boolean pSeleccionada = false;
    boolean lSeleccionada = false;
    boolean tcSeleccionada = false;

    String dniModificado = null;

    int ultimoIndiceSeleccionado = 0;
    List<Articulo> articulos;

    /**
     * Constructor CatalogoCategoriaArticuloController
     *
     * @param vista PanelRegistroCatalogoArticulo
     * @param modelo ArticuloJpaController
     */
    public CatalogoController(PanelRegistroCatalogoArticulo vista, ArticuloJpaController modelo) {
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
        //Posiciona la seleccion en el Panel datos articulos. 
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
        vista.getTablaArticulos().changeSelection(sizeTabla(), 1, false, false);

        //inicializa el JcomboBox de Categoria
        llenarJcomboboxCategoria();

        //Llena El JComboBox de Proveedor
        llenarJcomboboxProveedor();

        //desbloquea el boton nuevo
        bloquearAceptarCrear = true;

        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
    }

    /**
     * Controla el Boton Modificar Desbloquea el Boton AceptarModificar para
     * poder modificar
     */
    public void btn_modificar() {

        //Posiciona la seleccion en el Panel datos articulos. 
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
        Articulo articuloModificado = new Articulo();

        //setea cliente en funcion al ID de la vista
        articuloModificado = modelo.findArticulo(Long.parseLong(vista.getJtfID().getText()));

        //Llena el combobox de Categoria
        llenarJcomboboxCategoria();
        //Posiciona dentro del combobox zona al objeto zona que posee el cliente.
        vista.getJcb_Categoria().setSelectedItem(articuloModificado.getUnCategoriaDeCatalogo());

        //Llena el combobox de Proveedor
        llenarJcomboboxProveedor();
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del cliente.
        vista.getJcb_Proveedor().setSelectedItem(articuloModificado.getUnProveedor());

        //desbloquea el boton modificar
        bloquearAceptarModificar = true;

        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
    }

    /**
     * Contola el Boton Eliminar Desbloquea el Boton Aceptareliminar para poder
     * eliminar
     */
    public void btn_eliminar() {

        //Posiciona la seleccion en el Panel datos articulos. 
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

        //Crea instancia de Articulo
        Articulo articuloAEliminar = new Articulo();

        //setea Articulo en funcion al ID de la vista
        articuloAEliminar = modelo.findArticulo(Long.parseLong(vista.getJtfID().getText()));

        //Llena el combobox de Categoria
        llenarJcomboboxCategoria();
        //Posiciona dentro del combobox zona al objeto zona que posee el cliente.
        vista.getJcb_Categoria().setSelectedItem(articuloAEliminar.getUnCategoriaDeCatalogo());

        //Llena el combobox de Proveedor
        llenarJcomboboxProveedor();
        //Posiciona dentro del combobox Provincia al objeto Provincia que posee el la zona del cliente.
        vista.getJcb_Proveedor().setSelectedItem(articuloAEliminar.getUnProveedor());

        //Habilita boton Aceptar Eliminar Bloqueado
        bloquearAceptarEliminar = true;

        //Limpia la lista            
        vista.getTablaArticulos().setModel(new DefaultTableModel());
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
        llenarTabla(vista.getTablaArticulos());

        //Setea ancho de columna
        setAnchoColumna();

        //Carga el primer elemento si la lista es mayo a 1
        if (sizeTabla() >= 0) {
            //Si posee datos de direccion se cargan en la vista

            //Posicionar el cursor de la lista en el primer Elemento
            vista.getJtfID().setText(articulos.get(0).getId().toString());
            vista.getJtfDescripcion().setText(articulos.get(0).getDescripcion());

            if (articulos.get(0).getId().toString().equals(vista.getJtfID().getText())) {
                if (articulos.get(0).getUnCategoriaDeCatalogo() != null) {
                    vista.getJcb_Categoria().addItem(articulos.get(0).getUnCategoriaDeCatalogo().getDescripcion());
                } else {
                    System.out.println("no tiene categoria");
                }
                if (articulos.get(0).getUnProveedor() != null) {
                    vista.getJcb_Proveedor().addItem(articulos.get(0).getUnProveedor().getRazonSocial());
                } else {
                    System.out.println("no tiene proveedor");
                }

            }

            //posiciona en foco de la lista en el primer Empleado 
            vista.getTablaArticulos().changeSelection(0, 1, false, false);
        } else {
            //Habilita Botones
            vista.habilitarBoton(false, vista.getJbtn_Modificar());
            vista.habilitarBoton(false, vista.getJbtn_Eliminar());
            limpiarTodosLosCampos();
            JOptionPane.showMessageDialog(null, "No hay Articulos que listar");
        }

        //Posiciona la seleccion en el Panel datos articulos. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }

    /**
     * Controla el Boton Aceptar cuando se este creando crea instancia de
     * cliente, slo si existe localidad crea instancia de direccion y persiste,
     * solamente si el cliente no existe asocia la direccion al cliente persiste
     * cliente
     */
    public void btn_aceptarCrear() {
        boolean articuloCreado = false;

        if (!articuloCreado) {
            //Crear Instancia de Empleado
            Articulo articulo = new Articulo();

            //setea Descripcion de Articulo
            articulo.setDescripcion(vista.getJtfDescripcion().getText());

            //Instancia de unaCategoriaArticulo
            CategoriaDeCatalogo unaCategoriaArticulo = new CategoriaDeCatalogo();

            //Setea unaCategoriaArticulo con lo que tiene seleccionado el JCB de unaCategoriaArticulo
            unaCategoriaArticulo = (CategoriaDeCatalogo) vista.getJcb_Categoria().getSelectedItem();

            //Agrega la unaCategoriaArticulo al Articulo
            articulo.setUnCategoriaDeCatalogo(unaCategoriaArticulo);

            //Instancia de Proveedor
            Proveedor unproveedor = new Proveedor();

            //Setea unproveedor con lo que tiene seleccionado el JCB de unproveedor
            unproveedor = (Proveedor) vista.getJcb_Proveedor().getSelectedItem();

            //Agrega  unproveedor al Articulo
            articulo.setUnProveedor(unproveedor);

            //Persiste Empleado
            modelo.create(articulo);

            //Bandera de cliente creado a verdadero
            articuloCreado = true;

            //Mensaje de cliente Guardado
            JOptionPane.showMessageDialog(null, "Articulo Guardado");
        }

        if (articuloCreado) {
            //llena la tabla de Empleados
            llenarTabla(vista.getTablaArticulos());

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
            vista.getTablaArticulos().changeSelection(sizeTabla(), 1, false, false);

            //Da valor al ID en la tabla
            vista.getJtfID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(sizeTabla(), 1)));

            //Posiciona la seleccion en el Panel datos articulos. 
            vista.getjTabbedPaneContenedor().setSelectedIndex(0);

            //Todos los botones de aceptar Bloqueados
            bloquearAceptarCrear = false;
            bloquearAceptarEliminar = false;
            bloquearAceptarModificar = false;
        }

    }

    /**
     * Controla el Boton Aceptar cuando se este modificando crea instancia de
     * cliente, en funcion al ID seleccionado crea una instancia de direccion
     * del cliente verifica que el DNI del cliente es modificado, si lo es setea
     * el dni del cliente instanciado edita direccion edita cliente
     */
    public void btn_aceptarModificar() {

        boolean empleadoModifocado = false;

        //instancia de cliente igual al objeto guardado en Base de datos
        Articulo articuloModificado = modelo.findArticulo(Long.parseLong(vista.getJtfID().getText()));

        //setea Descripcion Articulo con nuevos valores
        articuloModificado.setDescripcion(vista.getJtfDescripcion().getText());

        if (!empleadoModifocado) {
            //verificar que el DNI sea igual que el DNI actual        
            //Instancia de unaCategoriaArticulo
            CategoriaDeCatalogo unaCategoriaArticulo = new CategoriaDeCatalogo();

            //Setea unaCategoriaArticulo con lo que tiene seleccionado el JCB de unaCategoriaArticulo
            unaCategoriaArticulo = (CategoriaDeCatalogo) vista.getJcb_Categoria().getSelectedItem();

            //Agrega la unaCategoriaArticulo al Articulo
            articuloModificado.setUnCategoriaDeCatalogo(unaCategoriaArticulo);

            //Instancia de Proveedor
            Proveedor unproveedor = new Proveedor();

            //Setea unproveedor con lo que tiene seleccionado el JCB de unproveedor
            unproveedor = (Proveedor) vista.getJcb_Proveedor().getSelectedItem();

            //Agrega  unproveedor al Articulo
            articuloModificado.setUnProveedor(unproveedor);

            //El DNI es igual se mantiene sin cambio            
            try {
                //Persiste Empleado
                modelo.edit(articuloModificado);
                JOptionPane.showMessageDialog(null, "Articulo modificado");

                //Bandera de cliente creado a verdadero
                empleadoModifocado = true;

            } catch (Exception ex) {
                Logger.getLogger(CatalogoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Si el DNI no es igual que el DNI actual
            //Si el DNI no es igual que el DNI actual
            //Si el DNI no es igual que el DNI actual
            //Si el DNI no es igual que el DNI actual

        }
        if (empleadoModifocado) {

            //llena la tabla de Empleados
            llenarTabla(vista.getTablaArticulos());

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
            vista.getTablaArticulos().changeSelection(buscarPosicionEnTabla(articuloModificado.getId()), 1, false, false);

            //Todos los botones de aceptar Bloqueados
            bloquearAceptarCrear = false;
            bloquearAceptarEliminar = false;
            bloquearAceptarModificar = false;

        }

    }

    /**
     * Controla el Boton Aceptar cuando se este eliminando crea instancia de
     * cliente, en funcion al ID seleccionado crea una instancia de direccion
     * del cliente verifica que el cliente tenga dreccion, si tiene direccion se
     * elimia la direccion del cliente, elimina direccion elimina cliente
     */
    public void btn_aceptarEliminar() {
        boolean articuloEliminado = false;

        if (!articuloEliminado) {
            //instancia de cliente igual al objeto guardado en Base de datos
            Articulo articuloAEliminar = modelo.findArticulo(Long.parseLong(vista.getJtfID().getText()));

            try {
                //Se elimina Empleado antes que la direccion por integridad referencia
                modelo.destroy(articuloAEliminar.getId());
                JOptionPane.showMessageDialog(null, "Articulo eliminado");

            } catch (Exception ex) {
                Logger.getLogger(CatalogoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            articuloEliminado = true;
        }
        if (articuloEliminado) {
            //llena la tabla de Empleados
            llenarTabla(vista.getTablaArticulos());

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

            //posiciona en foco de la lista en el Articulo del modificado
            //vista.getTablaArticulos().changeSelection(buscarPosicionEnTabla(articuloAEliminar.getId()), 1, false, false);
            btn_listar();

            //Todos los botones de aceptar Bloqueados
            bloquearAceptarCrear = false;
            bloquearAceptarEliminar = false;
            bloquearAceptarModificar = false;

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
        vista.getTablaArticulos().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.habilitarArbol(true);

        //Todos los botones de aceptar Bloqueados
        bloquearAceptarCrear = false;
        bloquearAceptarEliminar = false;
        bloquearAceptarModificar = false;
    }

    /**
     * Llena Jtable de cliente crea una lista de articulos existentes en la base
     * de datos.
     *
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD) {
        articulos = new ArrayList<Articulo>();
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
        modeloT.addColumn("Descripcion");

        //Cantidad de columnas 
        Object[] columna = new Object[4];

        int numero = 0;

        for (Articulo articulo : modelo.findArticuloEntities()) {
            //Guarda en Lista de articulos  
            articulos.add(articulo);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);
            columna[1] = articulo.getId();
            columna[2] = articulo.getDescripcion();

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
        vista.habilitarCampo(estado, vista.getJtfDescripcion());

        vista.habilitarCombobox(estado, vista.getJcb_Categoria());
        vista.habilitarCombobox(estado, vista.getJcb_Proveedor());
    }

    /**
     * limpia todos los campos de la vista
     */
    public void limpiarTodosLosCampos() {
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfDescripcion());

        //vista.limpiarCampo(vista.getJtfDireccion());
        vista.limpiarCombobox(vista.getJcb_Categoria());
        vista.limpiarCombobox(vista.getJcb_Proveedor());
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
        vista.getValidador().validarSoloLetras(vista.getJtfDescripcion());
        vista.getValidador().LimitarCaracteres(vista.getJtfDescripcion(), 30);
    }

    /**
     * Establece el Ancho de cada columna de la tabla cliente de la vista.
     */
    public void setAnchoColumna() {
        TableColumnModel columnModel = vista.getTablaArticulos().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(25);
        columnModel.getColumn(1).setPreferredWidth(25);
        columnModel.getColumn(2).setPreferredWidth(350);
    }

    /**
     * Busca la posicion que ocupa un cliente en la tabla cliente
     *
     * @param id de Empleado
     * @return posicion de cliente
     */
    public int buscarPosicionEnTabla(Long id) {
        int posicion = 0;
        for (Articulo articulo : modelo.findArticuloEntities()) {
            if (id.equals(articulo.getId())) {
                return posicion;
            }
            posicion++;
        }

        return posicion;
    }

    /**
     * informa el tamaño de la tabla articulos
     *
     * @return tamaño de la tabla
     */
    public int sizeTabla() {
        int posicion = 0;
        for (Articulo articulo : modelo.findArticuloEntities()) {
            posicion++;
        }
        return posicion - 1;
    }

    /**
     * llena el JcomboBox de Zona con objetos Zona de la base de datos
     */
    public void llenarJcomboboxCategoria() {
        modeloCategoriaArticulo = new CategoriaArticuloJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloCategoriaArticulo.findCategoriaArticuloEntities());
        vista.getJcb_Categoria().setModel(mdl);
    }

    /**
     * Llena el JComboBox Zona
     */
    public void llenarJcomboboxProveedor() {
        modeloProveedor = new ProveedorJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloProveedor.findProveedorEntities());
        vista.getJcb_Proveedor().setModel(mdl);
    }

    /**
     * Verifica el cambio de estado en los JComboBox
     *
     * @param e Evento de cambio de JCOMBOBOX
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    /**
     * Verifica el Foco ganado por los elementos de la vista
     *
     * @param e Evento de foco Ganado
     */
    @Override
    public void focusGained(FocusEvent e) {

    }

    /**
     * Verifica el Foco perdido por los elementos de la vista
     *
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
     * Verifica los eventos de click realizados en la tabla de articulos si
     * cambia se completan los datos del cliente
     *
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //carga los datos en la vista si cualquiera de las variables es verdadera
        //if (bloquearAceptarCrear || bloquearAceptarModificar || bloquearAceptarEliminar) {
        int seleccion = vista.getTablaArticulos().rowAtPoint(e.getPoint());
        vista.getJtfID().setText(String.valueOf(vista.getTablaArticulos().getValueAt(seleccion, 1)));

        for (Articulo articulo : articulos) {
            if (articulo.getId().toString().equals(vista.getJtfID().getText())) {
                //Setea Descripcion de Articulo
                vista.getJtfDescripcion().setText(articulo.getDescripcion());

                //Setea JCBox de Categoria en articulo
                vista.getJcb_Categoria().removeAllItems();
                vista.getJcb_Categoria().addItem(articulo.getUnCategoriaDeCatalogo().getDescripcion());

                //Setea JCBox de Proveedor en articulo
                vista.getJcb_Proveedor().removeAllItems();
                vista.getJcb_Proveedor().addItem(articulo.getUnProveedor().getRazonSocial());
            }
        }
        //Si posee datos de direccion se cargan en la vista

        //Posiciona la seleccion en el Panel datos articulos. 
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
