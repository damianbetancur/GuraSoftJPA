package controller;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Empresa;
import model.JPAController.EmpresaJpaController;
import model.JPAController.UnidadJpaController;
import model.Unidad;
import view.JframePrincipal;
import view.PanelRegistroDeUnidad;

/**
 * Clase controladora de Articulos de categorias en el Catalogo
 *
 * @author Ariel
 */
public class UnidadController extends Controller {

    private PanelRegistroDeUnidad vista;
    private UnidadJpaController modelo;

    private boolean aceptar_agregar;
    private boolean aceptar_modificar;

    //modelos necesarios para el controlador.
    private EmpresaJpaController modeloEmpresa;

    //Entidades necesarias para el controlador
    Empresa empresa = null;

    List<Unidad> unidades;

    /**
     * Constructor CatalogoCategoriaArticuloController
     *
     * @param vista PanelRegistroCatalogoArticulo
     * @param modelo ArticuloJpaController
     */
    public UnidadController(PanelRegistroDeUnidad vista, UnidadJpaController modelo) {

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
        vista.getTablaListaDeUnidad().setModel(new DefaultTableModel());

        btn_listar();

    }

    /**
     * ActionPerformed Controla los eventos que suceden en la vista al presionar
     * los Botones de CRUD
     *
     * @param e recepcion de Evento
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //Boton Volver
        if (e.getSource() == vista.getJbtn_Volver()) {
            btn_volver();
            aceptar_modificar = false;
            aceptar_agregar = false;
        }

    }

    public void btn_listar() {

        //inhabilitar Campos
        inhabilitarTodosLosCampos(false);

        //Habilita Botones            
        vista.habilitarBoton(true, vista.getJbtn_Volver());

        //Llena la tabla
        llenarTabla(vista.getTablaListaDeUnidad());

        //Setea ancho de columna
        setAnchoColumna();

        //Carga el primer elemento si la lista es mayo a 1
        if (sizeTabla() >= 0) {
            //Si posee datos de direccion se cargan en la vista

            //Posicionar el cursor de la lista en el primer Elemento
            vista.getJtfID().setText(unidades.get(0).getId().toString());
            vista.getJtfDescripcion().setText(unidades.get(0).getNombre());

            //posiciona en foco de la lista en el primer Empleado 
            vista.getTablaListaDeUnidad().changeSelection(0, 1, false, false);
        } else {
            //Habilita Botones
            limpiarTodosLosCampos();
            JOptionPane.showMessageDialog(null, "No hay Unidades que listar");
        }

        //Posiciona la seleccion en el Panel datos articulos. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
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
        vista.getTablaListaDeUnidad().setModel(new DefaultTableModel());

        //Habilita el Arbol de seleccion
        JframePrincipal.habilitarArbol(true);

    }

    /**
     * Llena Jtable de cliente crea una lista de PreciosDeArticulos existentes
     * en la base de datos.
     *
     * @param tablaD Tabla Empleado
     */
    public void llenarTabla(JTable tablaD) {
        unidades = new ArrayList<Unidad>();
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

        for (Unidad unidad : modelo.findUnidadEntities()) {
            //Guarda en Lista de articulos  
            unidades.add(unidad);
            numero = numero + 1;
            columna[0] = String.valueOf(numero);
            columna[1] = unidad.getId();
            columna[2] = unidad.getNombre();

            modeloT.addRow(columna);
        };
        setAnchoColumna();
    }

    /**
     * Establece el Ancho de cada columna de la tabla articulo de la vista.
     */
    public void setAnchoColumna() {
        TableColumnModel columnModel = vista.getTablaListaDeUnidad().getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(2).setPreferredWidth(450);
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
        //carga los datos en la vista si cualquiera de las variables es verdadera        
        int seleccion = vista.getTablaListaDeUnidad().rowAtPoint(e.getPoint());
        vista.getJtfID().setText(String.valueOf(vista.getTablaListaDeUnidad().getValueAt(seleccion, 1)));

        for (Unidad unidad : unidades) {
            if (unidad.getId().toString().equals(vista.getJtfID().getText())) {
                //Setea Descripcion de Unidad
                vista.getJtfDescripcion().setText(unidad.getNombre());
            }
        }

        //Posiciona la seleccion en el Panel datos articulos. 
        vista.getjTabbedPaneContenedor().setSelectedIndex(0);
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

        //Inhabilita Boton Volver
        vista.habilitarBoton(estado, vista.getJbtn_Volver());

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

    }

    public void limpiarTodosLosCampos() {
        vista.limpiarCampo(vista.getJtfID());
        vista.limpiarCampo(vista.getJtfDescripcion());

    }

    public int sizeTabla() {
        int posicion = 0;
        for (Unidad unidad : modelo.findUnidadEntities()) {
            posicion++;
        }
        return posicion - 1;
    }
}
