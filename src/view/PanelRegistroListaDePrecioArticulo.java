/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;



/**
 *
 * @author Ariel
 */
public final class PanelRegistroListaDePrecioArticulo extends javax.swing.JPanel  implements InterfacePanel{

    /**
     * Creates new form PersonalPanel
     */
    private ValidadorDeCampos validador;
    
    public PanelRegistroListaDePrecioArticulo() {
        initComponents();      
        this.validador = new ValidadorDeCampos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jbtn_Volver = new javax.swing.JButton();
        jTabbedPaneContenedor = new javax.swing.JTabbedPane();
        jPanelEmpleado = new javax.swing.JPanel();
        jlbl_descripcion = new javax.swing.JLabel();
        jlbl_ID = new javax.swing.JLabel();
        jtf_ID = new javax.swing.JTextField();
        jtf_Descripcion = new javax.swing.JTextField();
        jcb_Categoria = new javax.swing.JComboBox<>();
        jlbl_Categoria = new javax.swing.JLabel();
        jlbl_Proveedor = new javax.swing.JLabel();
        jcb_Proveedor = new javax.swing.JComboBox<>();
        jlbl_precio = new javax.swing.JLabel();
        jtf_Precio = new javax.swing.JTextField();
        jlbl_Proveedor1 = new javax.swing.JLabel();
        jcb_ListaPrecio = new javax.swing.JComboBox<>();
        jbtn_asignarPrecio = new javax.swing.JButton();
        jbtn_Aceptar = new javax.swing.JButton();
        jbtn_Cancelar = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(950, 800));
        setMinimumSize(new java.awt.Dimension(950, 800));
        setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Lista de Articulos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        jPanel2.setMinimumSize(new java.awt.Dimension(730, 290));
        jPanel2.setLayout(null);

        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaArticulos);
        if (tablaArticulos.getColumnModel().getColumnCount() > 0) {
            tablaArticulos.getColumnModel().getColumn(0).setMinWidth(120);
            tablaArticulos.getColumnModel().getColumn(0).setPreferredWidth(120);
            tablaArticulos.getColumnModel().getColumn(0).setMaxWidth(120);
        }

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(30, 30, 680, 150);

        add(jPanel2);
        jPanel2.setBounds(70, 50, 730, 200);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setText("GESTIÓN DE ARTICULOS DE LISTA DE PRECIO");
        add(jLabel4);
        jLabel4.setBounds(210, 10, 450, 30);

        jbtn_Volver.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jbtn_Volver.setText("VOLVER");
        add(jbtn_Volver);
        jbtn_Volver.setBounds(590, 660, 210, 50);

        jPanelEmpleado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelEmpleado.setName("Empleado"); // NOI18N
        jPanelEmpleado.setLayout(null);

        jlbl_descripcion.setText("Descripción:");
        jPanelEmpleado.add(jlbl_descripcion);
        jlbl_descripcion.setBounds(60, 80, 100, 15);

        jlbl_ID.setText("ID:");
        jPanelEmpleado.add(jlbl_ID);
        jlbl_ID.setBounds(60, 40, 90, 15);
        jPanelEmpleado.add(jtf_ID);
        jtf_ID.setBounds(160, 40, 270, 19);
        jPanelEmpleado.add(jtf_Descripcion);
        jtf_Descripcion.setBounds(160, 80, 270, 19);

        jPanelEmpleado.add(jcb_Categoria);
        jcb_Categoria.setBounds(160, 130, 270, 24);

        jlbl_Categoria.setText("CATEGORIA:");
        jPanelEmpleado.add(jlbl_Categoria);
        jlbl_Categoria.setBounds(60, 130, 80, 15);

        jlbl_Proveedor.setText("PROVEEDOR: ");
        jPanelEmpleado.add(jlbl_Proveedor);
        jlbl_Proveedor.setBounds(60, 180, 80, 15);

        jPanelEmpleado.add(jcb_Proveedor);
        jcb_Proveedor.setBounds(160, 180, 270, 24);

        jlbl_precio.setText("Precio:");
        jPanelEmpleado.add(jlbl_precio);
        jlbl_precio.setBounds(60, 270, 70, 15);
        jPanelEmpleado.add(jtf_Precio);
        jtf_Precio.setBounds(160, 270, 270, 19);

        jlbl_Proveedor1.setText("Lista de Precio:");
        jPanelEmpleado.add(jlbl_Proveedor1);
        jlbl_Proveedor1.setBounds(60, 230, 100, 15);

        jPanelEmpleado.add(jcb_ListaPrecio);
        jcb_ListaPrecio.setBounds(160, 230, 270, 24);

        jbtn_asignarPrecio.setText("Asignar Precio");
        jPanelEmpleado.add(jbtn_asignarPrecio);
        jbtn_asignarPrecio.setBounds(50, 310, 380, 30);

        jTabbedPaneContenedor.addTab("Articulo", jPanelEmpleado);

        add(jTabbedPaneContenedor);
        jTabbedPaneContenedor.setBounds(70, 260, 730, 380);

        jbtn_Aceptar.setText("ACEPTAR");
        add(jbtn_Aceptar);
        jbtn_Aceptar.setBounds(70, 660, 170, 25);

        jbtn_Cancelar.setText("CANCELAR");
        add(jbtn_Cancelar);
        jbtn_Cancelar.setBounds(330, 660, 170, 25);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelEmpleado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPaneContenedor;
    private javax.swing.JButton jbtn_Aceptar;
    private javax.swing.JButton jbtn_Cancelar;
    private javax.swing.JButton jbtn_Volver;
    private javax.swing.JButton jbtn_asignarPrecio;
    private javax.swing.JComboBox<String> jcb_Categoria;
    private javax.swing.JComboBox<String> jcb_ListaPrecio;
    private javax.swing.JComboBox<String> jcb_Proveedor;
    private javax.swing.JLabel jlbl_Categoria;
    private javax.swing.JLabel jlbl_ID;
    private javax.swing.JLabel jlbl_Proveedor;
    private javax.swing.JLabel jlbl_Proveedor1;
    private javax.swing.JLabel jlbl_descripcion;
    private javax.swing.JLabel jlbl_precio;
    private javax.swing.JTextField jtf_Descripcion;
    private javax.swing.JTextField jtf_ID;
    private javax.swing.JTextField jtf_Precio;
    private javax.swing.JTable tablaArticulos;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void setControlador(Controller c) {
        
        //Agrega Botones al Escuchador ActionListener para manejar los eventos realizados
        jbtn_asignarPrecio.addActionListener(c);
        jbtn_Aceptar.addActionListener(c);
        jbtn_Cancelar.addActionListener(c);      
        jbtn_Volver.addActionListener(c); 
        
        
        //Agrega JTextField del Panel Datos del Empleado al escuchador KeyListener para verificar campos ingresados
        jtf_Descripcion.addKeyListener(c);
        jtf_Precio.addKeyListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_Proveedor.addItemListener(c);
        jcb_Categoria.addItemListener(c);
        jcb_ListaPrecio.addItemListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_Proveedor.addFocusListener(c);
        jcb_Categoria.addFocusListener(c);
        jcb_ListaPrecio.addFocusListener(c);
        
        //Agrego la tabla al escuchador Mouse Listener para verificar elementos seleccionados
        tablaArticulos.addMouseListener(c);
        
        
        
    }

    /*
    CAMPOS del PANEL Getters y Setters
    */    
    public javax.swing.JTextField getJtfID() {
        return jtf_ID;
    }

    public void setJtfID(javax.swing.JTextField jtfID) {
        this.jtf_ID = jtfID;
    }

    public javax.swing.JTextField getJtfDescripcion() {
        return jtf_Descripcion;
    }

    public void setJtfDescripcion(javax.swing.JTextField jtfDescripcion) {
        this.jtf_Descripcion = jtfDescripcion;
    }

    
    /*
    BOTONES del PANEL
    */
        
    public javax.swing.JButton getJbtn_Aceptar() {
        return jbtn_Aceptar;
    }

    public javax.swing.JButton getJbtn_Cancelar() {
        return jbtn_Cancelar;
    }
    

    public javax.swing.JButton getJbtn_AsignarPrecio() {
        return jbtn_asignarPrecio;
    }
    

    public javax.swing.JButton getJbtn_Volver() {
        return jbtn_Volver;
    }
      
    
    
    @Override
    public void limpiarCampo(JTextField campo){
        campo.setText("");        
    }
    
    @Override
    public void habilitarCampo(boolean h, JTextField campo){
        campo.setEditable(h);
        
    }

    @Override
   public void habilitarBoton(boolean h, JButton btn){
       btn.setEnabled(h);
   }

    @Override
    public ValidadorDeCampos getValidador() {
        return validador;
    }

    public javax.swing.JTable getTablaArticulos() {
        return tablaArticulos;
    }

    @Override
    public void habilitarCombobox(boolean h, JComboBox campo) {
        campo.setEnabled(h);
    }

    @Override
    public void limpiarCombobox(JComboBox campo) {
        campo.removeAllItems();
    }
    

    public javax.swing.JPanel getjPanelEmpleado() {
        return jPanelEmpleado;
    }

    public javax.swing.JTabbedPane getjTabbedPaneContenedor() {
        return jTabbedPaneContenedor;
    }

    public javax.swing.JComboBox<String> getJcb_Categoria() {
        return jcb_Categoria;
    }

    public void setJcb_Categoria(javax.swing.JComboBox<String> jcb_Categoria) {
        this.jcb_Categoria = jcb_Categoria;
    }

    public javax.swing.JComboBox<String> getJcb_Proveedor() {
        return jcb_Proveedor;
    }

    public void setJcb_Proveedor(javax.swing.JComboBox<String> jcb_Proveedor) {
        this.jcb_Proveedor = jcb_Proveedor;
    }

    public javax.swing.JComboBox<String> getJcb_ListaPrecio() {
        return jcb_ListaPrecio;
    }

    public void setJcb_ListaPrecio(javax.swing.JComboBox<String> jcb_ListaPreio) {
        this.jcb_ListaPrecio = jcb_ListaPreio;
    }

    public javax.swing.JTextField getJtf_Precio() {
        return jtf_Precio;
    }

    public void setJtf_Precio(javax.swing.JTextField jtf_Precio) {
        this.jtf_Precio = jtf_Precio;
    }
    
    
}