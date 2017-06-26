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
import model.Deposito;
import model.Empresa;
import model.JPAController.DepositoJpaController;
import model.JPAController.EmpresaJpaController;
import model.JPAController.UnidadJpaController;
import model.Unidad;
import view.JframePrincipal;
import view.PanelRegistroDeDeposito;

/**
 * Clase controladora de Articulos de categorias en el Catalogo
 *
 * @author Ariel
 */
public class DepositoController extends Controller {

    private PanelRegistroDeDeposito vista;
    private DepositoJpaController modelo;

    private boolean aceptar_agregar;
    private boolean aceptar_modificar;
    private boolean aceptar_eliminar;

    //modelos necesarios para el controlador.
    private EmpresaJpaController modeloEmpresa;
    private UnidadJpaController modeloUnidad;

    //Entidades necesarias para el controlador
    Empresa empresa = null;

    List<Deposito> depositos;

    //Deposito Encontrado
    Deposito depositoEncontrado;
    Unidad unidadEncontrada;

    //Deposito Nuevo
    Deposito depositoNuevo;
    Unidad unidadNueva;

    /**
     * Constructor CatalogoCategoriaArticuloController
     *
     * @param vista PanelRegistroCatalogoArticulo
     * @param modelo ArticuloJpaController
     */
    public DepositoController(PanelRegistroDeDeposito vista, DepositoJpaController modelo) {

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

        vista.getJbtn_Listar().setEnabled(true);

        //Limpia la lista            
        vista.getTablaListaDeDeposito().setModel(new DefaultTableModel());

        //habilita el boton volver
        vista.getJbtn_Volver().setEnabled(true);

        vista.getJbtn_Aceptar().setVisible(false);
        vista.getJbtn_Cancelar().setVisible(false);

        aceptar_agregar = false;
        aceptar_modificar = false;
        aceptar_eliminar = false;
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
            aceptar_agregar = true;//agregar habilitado
            aceptar_modificar = false;
            aceptar_eliminar = false;
        }

        //Boton Cancelar
        if (e.getSource() == vista.getJbtn_Cancelar()) {
            btn_cancelar();
            aceptar_agregar = false;
            aceptar_modificar = false;
            aceptar_eliminar = false;
        }

        //Boton Modificar
        if (e.getSource() == vista.getJbtn_Modificar()) {
            btn_modificar();
            aceptar_agregar = false;
            aceptar_modificar = true; //modificar habilitado        
            aceptar_eliminar = false;
        }

        //Boton Eliminar
        if (e.getSource() == vista.getJbtn_Eliminar()) {
            btn_eliminar();
            aceptar_agregar = false;
            aceptar_modificar = false;
            aceptar_eliminar = true;//eliminar habilitado            
        }

        //Boton Volver
        if (e.getSource() == vista.getJbtn_Volver()) {
            btn_volver();
            aceptar_agregar = false;
            aceptar_modificar = false;
            aceptar_eliminar = false;
        }

        //Boton Aceptar
        if (e.getSource() == vista.getJbtn_Aceptar()) {
            try {
                btn_aceptar();
                aceptar_agregar = false;
                aceptar_modificar = false;
                aceptar_eliminar = false;
            } catch (Exception ex) {
                Logger.getLogger(ListaDePreciosController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Boton Listar
        if (e.getSource() == vista.getJbtn_Listar()) {
            aceptar_agregar = false;
            aceptar_modificar = false;
            aceptar_eliminar = false;
            btn_listar();
        }
    }

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
        llenarTabla(vista.getTablaListaDeDeposito());

        //Setea ancho de columna
        setAnchoColumna();

        //Carga el primer elemento si la lista es mayo a 1
        if (sizeTabla() >= 0) {
            //Si posee datos de direccion se cargan en la vista

            //Posicionar el cursor de la lista en el primer Elemento
            vista.getJtfID().setText(depositos.get(0).getId().toString());
            vista.getJtfDescripcion().setText(depositos.get(0).getDescripcion());

            if (depositos.get(0).getId().toString().equals(vista.getJtfID().getText())) {

                vista.getJcb_Unidad().removeAllItems();
                vista.getJcb_Unidad().addItem(depositos.get(0).getUnaUnidad().toString());
            }

            //posiciona en foco de la lista en el primer Empleado 
            vista.getTablaListaDeDeposito().changeSelection(0, 1, false, false);
            depositoEncontrado = depositos.get(0);

        } else {
            //Habilita Botones
            vista.habilitarBoton(false, vista.getJbtn_Modificar());
            vista.habilitarBoton(false, vista.getJbtn_Eliminar());
            limpiarTodosLosCampos();
            JOptionPane.showMessageDialog(null, "No hay Depositos que listar");
        }

        //Posiciona la seleccion en el Panel datos empleados. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
    }

    /**
     * Boton agregar habilita el precio
     */
    public void btn_agregar() {

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

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());

        //Articulo depositoEncontrado;
        //PrecioArticulo precioArticuloEncontrado;
        //Limpia la lista            
        vista.getTablaListaDeDeposito().setModel(new DefaultTableModel());

        vista.getJtfID().setText("");
        vista.getJtfID().setEnabled(false);
        vista.getJtfDescripcion().setText("");
        llenarJcomboboxUnidad();

        aceptar_agregar = true;
        aceptar_modificar = false;
        aceptar_eliminar = false;

    }

    public void btn_modificar() {

        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Volver());

        vista.getJbtn_Aceptar().setVisible(true);
        vista.getJbtn_Cancelar().setVisible(true);

        //Limpiar Campos
        vista.getJtfID().setEnabled(false);

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());

        //Articulo depositoEncontrado;
        //PrecioArticulo precioArticuloEncontrado;
        //Limpia la lista            
        vista.getTablaListaDeDeposito().setModel(new DefaultTableModel());

        aceptar_agregar = false;
        aceptar_modificar = true;
        aceptar_eliminar = false;

        llenarJcomboboxUnidad();
    }

    public void btn_eliminar() {
        //habilitar Botones
        vista.habilitarBoton(true, vista.getJbtn_Aceptar());
        vista.habilitarBoton(true, vista.getJbtn_Cancelar());
        vista.habilitarBoton(false, vista.getJbtn_Volver());

        vista.getJbtn_Aceptar().setVisible(true);
        vista.getJbtn_Cancelar().setVisible(true);

        //Limpiar Campos
        vista.getJtfID().setEnabled(false);

        //Politica de validacion de Campos
        politicaValidacionDeCampos();

        //Habilitar Campos            
        inhabilitarTodosLosCampos(true);

        //Inhabilitar Botones
        vista.habilitarBoton(false, vista.getJbtn_Volver());
        vista.habilitarBoton(false, vista.getJbtn_Modificar());
        vista.habilitarBoton(false, vista.getJbtn_Agregar());

        //Articulo depositoEncontrado;
        //PrecioArticulo precioArticuloEncontrado;
        //Limpia la lista            
        vista.getTablaListaDeDeposito().setModel(new DefaultTableModel());

        aceptar_agregar = false;
        aceptar_modificar = false;
        aceptar_eliminar = true;

        llenarJcomboboxUnidad();
    }

    public void btn_aceptar() throws Exception {
        unidadNueva = new Unidad();
        depositoNuevo = new Deposito();

        modelo = new DepositoJpaController(Conexion.getEmf());

        if (aceptar_agregar) {
            unidadNueva = (Unidad) vista.getJcb_Unidad().getSelectedItem();
            depositoNuevo.setDescripcion(vista.getJtfDescripcion().getText());
            depositoNuevo.setUnaUnidad(unidadNueva);
            modelo.create(depositoNuevo);
            JOptionPane.showMessageDialog(null, "Deposito Creado");
        }
        if (aceptar_modificar) {
            depositoNuevo = depositoEncontrado;

            unidadNueva = (Unidad) vista.getJcb_Unidad().getSelectedItem();
            depositoNuevo.setDescripcion(vista.getJtfDescripcion().getText());
            depositoNuevo.setUnaUnidad(unidadNueva);
            modelo.edit(depositoNuevo);
            JOptionPane.showMessageDialog(null, "Deposito Modificado");
        }
        if (aceptar_eliminar) {
            depositoNuevo = depositoEncontrado;

            unidadNueva = (Unidad) vista.getJcb_Unidad().getSelectedItem();
            depositoNuevo.setDescripcion(vista.getJtfDescripcion().getText());
            depositoNuevo.setUnaUnidad(unidadNueva);
            modelo.destroy(depositoNuevo.getId());
            JOptionPane.showMessageDialog(null, "Deposito Eliminado");
        }

        btn_listar();
    }

    /**
     * cancela las operaciones
     */
    public void btn_cancelar() {
        btn_listar();
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
        vista.getTablaListaDeDeposito().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.modificarArbol(true);

    }

    /**
     * Llena Jtable de cliente crea una lista de PreciosDeArticulos existentes
     * en la base de datos.
     *
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD
    ) {

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

        cargarArticulosEnArray(columna, modeloT);

        setAnchoColumna();
    }

    /**
     * Establece el Ancho de cada columna de la tabla deposito de la vista.
     */
    public void setAnchoColumna() {
        TableColumnModel columnModel = vista.getTablaListaDeDeposito().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(450);
    }

    /**
     * Verifica el cambio de estado en los JComboBox de Lista de Precio
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
     * Verifica los eventos de click realizados en la tabla de
     * PreciosDeArticulos si cambia se completan los datos del cliente
     *
     * @param e Click de Mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        this.depositoEncontrado = null;

        int seleccion = vista.getTablaListaDeDeposito().rowAtPoint(e.getPoint());
        vista.getJtfID().setText(String.valueOf(vista.getTablaListaDeDeposito().getValueAt(seleccion, 1)));

        for (Deposito deposito : depositos) {
            if (deposito.getId().toString().equals(vista.getJtfID().getText())) {
                depositoEncontrado = deposito;
            }
        }

        vista.getJtfDescripcion().setText(depositoEncontrado.getDescripcion());

        if (depositoEncontrado.getUnaUnidad() != null) {
            vista.getJcb_Unidad().removeAllItems();
            vista.getJcb_Unidad().addItem(depositoEncontrado.getUnaUnidad().getNombre());
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
     * Modifica la habilitación de los Botones de la vista en funcion al
     * parametro de estado.
     *
     * @param estado de campos
     */
    public void inhabilitarTodosLosBotones(boolean estado) {
        //Inhabilita Botones CRUD
        vista.habilitarBoton(estado, vista.getJbtn_Agregar());
        vista.habilitarBoton(estado, vista.getJbtn_Modificar());
        vista.habilitarBoton(estado, vista.getJbtn_Eliminar());

        //Inhabilita Botones Aceptar-Cancelar
        vista.habilitarBoton(estado, vista.getJbtn_Cancelar());
        vista.habilitarBoton(estado, vista.getJbtn_Aceptar());

        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());

        //Inhabilita el boton listar
        vista.habilitarBoton(estado, vista.getJbtn_Listar());
    }

    /**
     * Carga los articulos en array para poder ser usados en la vista
     *
     * @param columna
     * @param modeloT
     */
    public void cargarArticulosEnArray(Object[] columna, DefaultTableModel modeloT) {
        int numero = 0;
        boolean agregado = false;
        depositos = null;

        depositos = new ArrayList<Deposito>();

        for (Deposito depositoBuscado : modelo.findDepositoEntities()) {

            if (agregado == false) {

                //Guarda en Lista de articulos  
                depositos.add(depositoBuscado);
                numero = numero + 1;
                columna[0] = String.valueOf(numero);
                columna[1] = depositoBuscado.getId();
                columna[2] = depositoBuscado.getDescripcion();

                modeloT.addRow(columna);
                //tiene precio
                agregado = true;

            }
            if (agregado == false) {
                depositos.add(depositoBuscado);
                numero = numero + 1;
                columna[0] = String.valueOf(numero);
                columna[1] = depositoBuscado.getId();
                columna[2] = depositoBuscado.getDescripcion();

                modeloT.addRow(columna);
                agregado = true;
            }

            agregado = false;
        }

    }

    /**
     * Carga los articulos si hay elementos en el catalo, posicionandose en el 1
     * er elemento y mostrandolo
     */
    public void cargarDepositoInicio() {
        if (!depositos.isEmpty()) {

            //posiciona en foco de la lista en el Articulo 
            vista.getTablaListaDeDeposito().changeSelection(0, 1, false, false);

            this.depositoEncontrado = null;

            int seleccion = 0;
            vista.getJtfID().setText(String.valueOf(vista.getTablaListaDeDeposito().getValueAt(seleccion, 1)));

            for (Deposito deposito : depositos) {
                if (deposito.getId().toString().equals(vista.getJtfID().getText())) {
                    depositoEncontrado = deposito;

                }
            }
            vista.getJtfDescripcion().setText(depositoEncontrado.getDescripcion());

        } else {
            JOptionPane.showMessageDialog(null, "Lista Vacia");
        }

    }

    public void llenarJcomboboxUnidad() {
        modeloUnidad = new UnidadJpaController(Conexion.getEmf());
        DefaultComboBoxModel mdl = new DefaultComboBoxModel((Vector) modeloUnidad.findUnidadEntities());
        vista.getJcb_Unidad().setModel(mdl);
    }

    public void politicaValidacionDeCampos() {
        //Politica de validacióón de Campos
        vista.getValidador().validarSoloLetras(vista.getJtfDescripcion());
        vista.getValidador().LimitarCaracteres(vista.getJtfDescripcion(), 30);
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

        vista.habilitarCombobox(estado, vista.getJcb_Unidad());

    }

    public void limpiarTodosLosCampos() {
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfDescripcion());

        vista.limpiarCombobox(vista.getJcb_Unidad());
    }

    public int sizeTabla() {
        int posicion = 0;
        for (Deposito deposito : modelo.findDepositoEntities()) {
            posicion++;
        }
        return posicion - 1;
    }
}
