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
public final class PanelRegistroDeDeposito extends javax.swing.JPanel  implements InterfacePanel{

    /**
     * Creates new form PersonalPanel
     */
    private ValidadorDeCampos validador;
    
    public PanelRegistroDeDeposito() {
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
        jbtn_Nuevo = new javax.swing.JButton();
        jbtn_Modificar = new javax.swing.JButton();
        jbtn_Listar = new javax.swing.JButton();
        jbtn_Eliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListaDeDeposito = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jbtn_Volver = new javax.swing.JButton();
        jTabbedPaneContenedor = new javax.swing.JTabbedPane();
        jPanelEmpleado = new javax.swing.JPanel();
        jlbl_descripcion = new javax.swing.JLabel();
        jlbl_ID = new javax.swing.JLabel();
        jtf_ID = new javax.swing.JTextField();
        jtf_descripcion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jcb_Unidad = new javax.swing.JComboBox<>();
        jlbl_unidad = new javax.swing.JLabel();
        jbtn_Aceptar = new javax.swing.JButton();
        jbtn_Cancelar = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(950, 800));
        setMinimumSize(new java.awt.Dimension(950, 800));
        setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Lista de Depositos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        jPanel2.setMinimumSize(new java.awt.Dimension(730, 290));
        jPanel2.setLayout(null);

        jbtn_Nuevo.setText("Agregar");
        jPanel2.add(jbtn_Nuevo);
        jbtn_Nuevo.setBounds(30, 190, 80, 30);

        jbtn_Modificar.setText("Modificar");
        jPanel2.add(jbtn_Modificar);
        jbtn_Modificar.setBounds(120, 190, 90, 30);

        jbtn_Listar.setText("Listar");
        jPanel2.add(jbtn_Listar);
        jbtn_Listar.setBounds(220, 190, 80, 30);

        jbtn_Eliminar.setText("Eliminar");
        jPanel2.add(jbtn_Eliminar);
        jbtn_Eliminar.setBounds(310, 190, 80, 30);

        tablaListaDeDeposito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaListaDeDeposito);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(30, 30, 680, 150);

        add(jPanel2);
        jPanel2.setBounds(70, 50, 730, 230);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setText("GESTIÓN DE DEPOSITOS");
        add(jLabel4);
        jLabel4.setBounds(280, 10, 250, 30);

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
        jtf_ID.setBounds(160, 40, 272, 19);
        jPanelEmpleado.add(jtf_descripcion);
        jtf_descripcion.setBounds(160, 80, 272, 19);
        jPanelEmpleado.add(jLabel1);
        jLabel1.setBounds(450, 40, 210, 0);

        jPanelEmpleado.add(jcb_Unidad);
        jcb_Unidad.setBounds(160, 120, 270, 24);

        jlbl_unidad.setText("Unidad:");
        jPanelEmpleado.add(jlbl_unidad);
        jlbl_unidad.setBounds(60, 120, 60, 15);

        jTabbedPaneContenedor.addTab("Deposito", jPanelEmpleado);

        add(jTabbedPaneContenedor);
        jTabbedPaneContenedor.setBounds(70, 310, 730, 320);

        jbtn_Aceptar.setText("ACEPTAR");
        add(jbtn_Aceptar);
        jbtn_Aceptar.setBounds(70, 660, 170, 25);

        jbtn_Cancelar.setText("CANCELAR");
        add(jbtn_Cancelar);
        jbtn_Cancelar.setBounds(270, 660, 170, 25);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelEmpleado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPaneContenedor;
    private javax.swing.JButton jbtn_Aceptar;
    private javax.swing.JButton jbtn_Cancelar;
    private javax.swing.JButton jbtn_Eliminar;
    private javax.swing.JButton jbtn_Listar;
    private javax.swing.JButton jbtn_Modificar;
    private javax.swing.JButton jbtn_Nuevo;
    private javax.swing.JButton jbtn_Volver;
    private javax.swing.JComboBox<String> jcb_Unidad;
    private javax.swing.JLabel jlbl_ID;
    private javax.swing.JLabel jlbl_descripcion;
    private javax.swing.JLabel jlbl_unidad;
    private javax.swing.JTextField jtf_ID;
    private javax.swing.JTextField jtf_descripcion;
    private javax.swing.JTable tablaListaDeDeposito;
    // End of variables declaration//GEN-END:variables

    
    @Override
    public void setControlador(Controller c) {
        
        //Agrega Botones al Escuchador ActionListener para manejar los eventos realizados
        jbtn_Nuevo.addActionListener(c);
        jbtn_Listar.addActionListener(c);
        jbtn_Modificar.addActionListener(c);
        jbtn_Eliminar.addActionListener(c);       
        jbtn_Aceptar.addActionListener(c);
        jbtn_Cancelar.addActionListener(c);      
        jbtn_Volver.addActionListener(c); 
        
        
        //Agrega JTextField del Panel Datos del Empleado al escuchador KeyListener para verificar campos ingresados
        jtf_descripcion.addKeyListener(c);
        
        
        //Agrego la tabla al escuchador Mouse Listener para verificar elementos seleccionados
        tablaListaDeDeposito.addMouseListener(c);
        
        
        jcb_Unidad.addItemListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_Unidad.addFocusListener(c);
        
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
        return jtf_descripcion;
    }

    public void setJtfDescripcion(javax.swing.JTextField jtfNombre) {
        this.jtf_descripcion = jtfNombre;
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

    public javax.swing.JButton getJbtn_Eliminar() {
        return jbtn_Eliminar;
    }

    public javax.swing.JButton getJbtn_Listar() {
        return jbtn_Listar;
    }

    public javax.swing.JButton getJbtn_Modificar() {
        return jbtn_Modificar;
    }

    public javax.swing.JButton getJbtn_Agregar() {
        return jbtn_Nuevo;
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

    public javax.swing.JTable getTablaListaDeDeposito() {
        return tablaListaDeDeposito;
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

    public javax.swing.JComboBox<String> getJcb_Unidad() {
        return jcb_Unidad;
    }

    public void setJcb_Unidad(javax.swing.JComboBox<String> jcb_Unidad) {
        this.jcb_Unidad = jcb_Unidad;
    }
    
    
}
