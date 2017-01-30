/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.EmpleadoController;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;



/**
 *
 * @author Ariel
 */
public final class PanelRegistroEmpleado extends javax.swing.JPanel  implements InterfacePanel{

    /**
     * Creates new form PersonalPanel
     */
    private ValidadorDeCampos validador;
    
    public PanelRegistroEmpleado() {
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
        tablaEmpleados = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jbtn_Volver = new javax.swing.JButton();
        jTabbedPaneContenedor = new javax.swing.JTabbedPane();
        jPanelEmpleado = new javax.swing.JPanel();
        jlbl_Nombre = new javax.swing.JLabel();
        jlbl_Apellido = new javax.swing.JLabel();
        jlbl_DNI = new javax.swing.JLabel();
        jlbl_ID = new javax.swing.JLabel();
        jtf_ID = new javax.swing.JTextField();
        jtf_Nombre = new javax.swing.JTextField();
        jtf_DNI = new javax.swing.JTextField();
        jtf_Apellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        msj_Direccion = new javax.swing.JLabel();
        msj_ID = new javax.swing.JLabel();
        msj_Nombre = new javax.swing.JLabel();
        msj_Apellido = new javax.swing.JLabel();
        msj_DNI = new javax.swing.JLabel();
        jcb_unidad = new javax.swing.JComboBox<>();
        jlbl_unidad = new javax.swing.JLabel();
        jlbl_TipoEmpleado = new javax.swing.JLabel();
        jcb_empleado = new javax.swing.JComboBox<>();
        jPanelDireccion = new javax.swing.JPanel();
        jlbl_zona_direccion = new javax.swing.JLabel();
        jcb_zona_direccion = new javax.swing.JComboBox<>();
        jlbl_provincia_direccion = new javax.swing.JLabel();
        jcb_provincia_direccion = new javax.swing.JComboBox<>();
        jcb_localidad_direccion = new javax.swing.JComboBox<>();
        jlbl_localidad_direccion = new javax.swing.JLabel();
        jlbl_calle_direccion = new javax.swing.JLabel();
        jtf_calle_direccion = new javax.swing.JTextField();
        jtf_numero_direccion = new javax.swing.JTextField();
        jlbl_numero_direccion = new javax.swing.JLabel();
        jtf_piso_direccion = new javax.swing.JTextField();
        jlbl_piso_direccion = new javax.swing.JLabel();
        jtf_departamento_direccion = new javax.swing.JTextField();
        jlbl_departamento_direccion = new javax.swing.JLabel();
        jbtn_Aceptar = new javax.swing.JButton();
        jbtn_Cancelar = new javax.swing.JButton();

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(950, 800));
        setMinimumSize(new java.awt.Dimension(950, 800));
        setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Lista de Empleados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
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

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaEmpleados);
        if (tablaEmpleados.getColumnModel().getColumnCount() > 0) {
            tablaEmpleados.getColumnModel().getColumn(0).setMinWidth(120);
            tablaEmpleados.getColumnModel().getColumn(0).setPreferredWidth(120);
            tablaEmpleados.getColumnModel().getColumn(0).setMaxWidth(120);
        }

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(30, 30, 680, 150);

        add(jPanel2);
        jPanel2.setBounds(70, 50, 730, 230);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setText("GESTIÓN DE EMPLEADOS");
        add(jLabel4);
        jLabel4.setBounds(300, 10, 250, 30);

        jbtn_Volver.setFont(new java.awt.Font("Dialog", 0, 36)); // NOI18N
        jbtn_Volver.setText("VOLVER");
        add(jbtn_Volver);
        jbtn_Volver.setBounds(590, 660, 210, 50);

        jPanelEmpleado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelEmpleado.setName("Empleado"); // NOI18N
        jPanelEmpleado.setLayout(null);

        jlbl_Nombre.setText("Nombre:");
        jPanelEmpleado.add(jlbl_Nombre);
        jlbl_Nombre.setBounds(60, 80, 100, 15);

        jlbl_Apellido.setText("Apellido:");
        jPanelEmpleado.add(jlbl_Apellido);
        jlbl_Apellido.setBounds(60, 120, 90, 15);

        jlbl_DNI.setText("DNI:");
        jPanelEmpleado.add(jlbl_DNI);
        jlbl_DNI.setBounds(60, 160, 80, 15);

        jlbl_ID.setText("ID:");
        jPanelEmpleado.add(jlbl_ID);
        jlbl_ID.setBounds(60, 40, 90, 15);
        jPanelEmpleado.add(jtf_ID);
        jtf_ID.setBounds(160, 40, 272, 19);
        jPanelEmpleado.add(jtf_Nombre);
        jtf_Nombre.setBounds(160, 80, 272, 19);
        jPanelEmpleado.add(jtf_DNI);
        jtf_DNI.setBounds(160, 160, 272, 19);
        jPanelEmpleado.add(jtf_Apellido);
        jtf_Apellido.setBounds(160, 120, 272, 19);
        jPanelEmpleado.add(jLabel1);
        jLabel1.setBounds(450, 40, 210, 0);
        jPanelEmpleado.add(msj_Direccion);
        msj_Direccion.setBounds(480, 190, 34, 15);
        jPanelEmpleado.add(msj_ID);
        msj_ID.setBounds(480, 40, 0, 0);
        jPanelEmpleado.add(msj_Nombre);
        msj_Nombre.setBounds(480, 80, 0, 0);
        jPanelEmpleado.add(msj_Apellido);
        msj_Apellido.setBounds(480, 110, 0, 0);
        jPanelEmpleado.add(msj_DNI);
        msj_DNI.setBounds(480, 150, 0, 0);

        jPanelEmpleado.add(jcb_unidad);
        jcb_unidad.setBounds(160, 200, 270, 24);

        jlbl_unidad.setText("UNIDAD:");
        jPanelEmpleado.add(jlbl_unidad);
        jlbl_unidad.setBounds(60, 200, 60, 15);

        jlbl_TipoEmpleado.setText("EMPLEADO:");
        jPanelEmpleado.add(jlbl_TipoEmpleado);
        jlbl_TipoEmpleado.setBounds(60, 250, 70, 15);

        jPanelEmpleado.add(jcb_empleado);
        jcb_empleado.setBounds(160, 250, 270, 24);

        jTabbedPaneContenedor.addTab("Empleado", jPanelEmpleado);

        jPanelDireccion.setLayout(null);

        jlbl_zona_direccion.setText("Zona:");
        jPanelDireccion.add(jlbl_zona_direccion);
        jlbl_zona_direccion.setBounds(40, 30, 60, 15);

        jPanelDireccion.add(jcb_zona_direccion);
        jcb_zona_direccion.setBounds(150, 30, 180, 24);

        jlbl_provincia_direccion.setText("Provincia:");
        jPanelDireccion.add(jlbl_provincia_direccion);
        jlbl_provincia_direccion.setBounds(40, 70, 60, 15);

        jPanelDireccion.add(jcb_provincia_direccion);
        jcb_provincia_direccion.setBounds(150, 70, 180, 24);

        jPanelDireccion.add(jcb_localidad_direccion);
        jcb_localidad_direccion.setBounds(150, 110, 180, 24);

        jlbl_localidad_direccion.setText("Localidad:");
        jPanelDireccion.add(jlbl_localidad_direccion);
        jlbl_localidad_direccion.setBounds(40, 110, 60, 15);

        jlbl_calle_direccion.setText("Calle:");
        jPanelDireccion.add(jlbl_calle_direccion);
        jlbl_calle_direccion.setBounds(40, 150, 60, 15);
        jPanelDireccion.add(jtf_calle_direccion);
        jtf_calle_direccion.setBounds(150, 150, 180, 19);
        jPanelDireccion.add(jtf_numero_direccion);
        jtf_numero_direccion.setBounds(150, 180, 180, 19);

        jlbl_numero_direccion.setText("Número:");
        jPanelDireccion.add(jlbl_numero_direccion);
        jlbl_numero_direccion.setBounds(40, 180, 60, 15);
        jPanelDireccion.add(jtf_piso_direccion);
        jtf_piso_direccion.setBounds(150, 210, 180, 19);

        jlbl_piso_direccion.setText("Piso:");
        jPanelDireccion.add(jlbl_piso_direccion);
        jlbl_piso_direccion.setBounds(40, 210, 60, 15);
        jPanelDireccion.add(jtf_departamento_direccion);
        jtf_departamento_direccion.setBounds(150, 240, 180, 19);

        jlbl_departamento_direccion.setText("Departamento:");
        jPanelDireccion.add(jlbl_departamento_direccion);
        jlbl_departamento_direccion.setBounds(40, 240, 90, 15);

        jTabbedPaneContenedor.addTab("Dirección", jPanelDireccion);

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
    private javax.swing.JPanel jPanelDireccion;
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
    private javax.swing.JComboBox<String> jcb_empleado;
    private javax.swing.JComboBox<String> jcb_localidad_direccion;
    private javax.swing.JComboBox<String> jcb_provincia_direccion;
    private javax.swing.JComboBox<String> jcb_unidad;
    private javax.swing.JComboBox<String> jcb_zona_direccion;
    private javax.swing.JLabel jlbl_Apellido;
    private javax.swing.JLabel jlbl_DNI;
    private javax.swing.JLabel jlbl_ID;
    private javax.swing.JLabel jlbl_Nombre;
    private javax.swing.JLabel jlbl_TipoEmpleado;
    private javax.swing.JLabel jlbl_calle_direccion;
    private javax.swing.JLabel jlbl_departamento_direccion;
    private javax.swing.JLabel jlbl_localidad_direccion;
    private javax.swing.JLabel jlbl_numero_direccion;
    private javax.swing.JLabel jlbl_piso_direccion;
    private javax.swing.JLabel jlbl_provincia_direccion;
    private javax.swing.JLabel jlbl_unidad;
    private javax.swing.JLabel jlbl_zona_direccion;
    private javax.swing.JTextField jtf_Apellido;
    private javax.swing.JTextField jtf_DNI;
    private javax.swing.JTextField jtf_ID;
    private javax.swing.JTextField jtf_Nombre;
    private javax.swing.JTextField jtf_calle_direccion;
    private javax.swing.JTextField jtf_departamento_direccion;
    private javax.swing.JTextField jtf_numero_direccion;
    private javax.swing.JTextField jtf_piso_direccion;
    private javax.swing.JLabel msj_Apellido;
    private javax.swing.JLabel msj_DNI;
    private javax.swing.JLabel msj_Direccion;
    private javax.swing.JLabel msj_ID;
    private javax.swing.JLabel msj_Nombre;
    private javax.swing.JTable tablaEmpleados;
    // End of variables declaration//GEN-END:variables

    
    public void setControlador(EmpleadoController c) {
        
        //Agrega Botones al Escuchador ActionListener para manejar los eventos realizados
        jbtn_Nuevo.addActionListener(c);
        jbtn_Listar.addActionListener(c);
        jbtn_Modificar.addActionListener(c);
        jbtn_Eliminar.addActionListener(c);        
        jbtn_Aceptar.addActionListener(c);
        jbtn_Cancelar.addActionListener(c);        
        jbtn_Volver.addActionListener(c);  
        
        //Agrega JTextField del Panel Datos del Empleado al escuchador KeyListener para verificar campos ingresados
        jtf_Nombre.addKeyListener(c);
        jtf_Apellido.addKeyListener(c);
        jtf_DNI.addKeyListener(c);
        
        //Agrega JTextField del Panel Direccion al escuchador KeyListener para verificar campos ingresados
        jtf_calle_direccion.addKeyListener(c);
        jtf_numero_direccion.addKeyListener(c);
        jtf_piso_direccion.addKeyListener(c);
        jtf_departamento_direccion.addKeyListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_zona_direccion.addItemListener(c);
        jcb_provincia_direccion.addItemListener(c);
        jcb_localidad_direccion.addItemListener(c);
        jcb_empleado.addItemListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_zona_direccion.addFocusListener(c);
        jcb_provincia_direccion.addFocusListener(c);
        jcb_localidad_direccion.addFocusListener(c);
        jtf_DNI.addFocusListener(c);
        jcb_empleado.addFocusListener(c);
        
        //Agrego la tabla al escuchador Mouse Listener para verificar elementos seleccionados
        tablaEmpleados.addMouseListener(c);
        
        
        
    }

    /*
    CAMPOS del PANEL Getters y Setters
    */
    public javax.swing.JTextField getJtfApellido() {
        return jtf_Apellido;
    }

    public void setJtfApellido(javax.swing.JTextField jtfApellido) {
        this.jtf_Apellido = jtfApellido;
    }

    public javax.swing.JTextField getJtfDNI() {
        return jtf_DNI;
    }

    public void setJtfDNI(javax.swing.JTextField jtfDNI) {
        this.jtf_DNI = jtfDNI;
    }
   
    public javax.swing.JTextField getJtfID() {
        return jtf_ID;
    }

    public void setJtfID(javax.swing.JTextField jtfID) {
        this.jtf_ID = jtfID;
    }

    public javax.swing.JTextField getJtfNombre() {
        return jtf_Nombre;
    }

    public void setJtfNombre(javax.swing.JTextField jtfNombre) {
        this.jtf_Nombre = jtfNombre;
    }

    public javax.swing.JComboBox<String> getJcb_localidad_direccion() {
        return jcb_localidad_direccion;
    }

    public void setJcb_localidad_direccion(javax.swing.JComboBox<String> jcb_localidad_direccion) {
        this.jcb_localidad_direccion = jcb_localidad_direccion;
    }

    public javax.swing.JComboBox<String> getJcb_provincia_direccion() {
        return jcb_provincia_direccion;
    }

    public void setJcb_provincia_direccion(javax.swing.JComboBox<String> jcb_provincia_direccion) {
        this.jcb_provincia_direccion = jcb_provincia_direccion;
    }

    public javax.swing.JComboBox<String> getJcb_zona_direccion() {
        return jcb_zona_direccion;
    }

    public void setJcb_zona_direccion(javax.swing.JComboBox<String> jcb_zona_direccion) {
        this.jcb_zona_direccion = jcb_zona_direccion;
    }

    public javax.swing.JTextField getJtf_calle_direccion() {
        return jtf_calle_direccion;
    }

    public void setJtf_calle_direccion(javax.swing.JTextField jtf_calle_direccion) {
        this.jtf_calle_direccion = jtf_calle_direccion;
    }

    public javax.swing.JTextField getJtf_departamento_direccion() {
        return jtf_departamento_direccion;
    }

    public void setJtf_departamento_direccion(javax.swing.JTextField jtf_departamento_direccion) {
        this.jtf_departamento_direccion = jtf_departamento_direccion;
    }

    public javax.swing.JTextField getJtf_numero_direccion() {
        return jtf_numero_direccion;
    }

    public void setJtf_numero_direccion(javax.swing.JTextField jtf_numero_direccion) {
        this.jtf_numero_direccion = jtf_numero_direccion;
    }

    public javax.swing.JTextField getJtf_piso_direccion() {
        return jtf_piso_direccion;
    }

    public void setJtf_piso_direccion(javax.swing.JTextField jtf_piso_direccion) {
        this.jtf_piso_direccion = jtf_piso_direccion;
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

    public javax.swing.JTable getTablaEmpleados() {
        return tablaEmpleados;
    }

    @Override
    public void habilitarCombobox(boolean h, JComboBox campo) {
        campo.setEnabled(h);
    }

    @Override
    public void limpiarCombobox(JComboBox campo) {
        campo.removeAllItems();
    }

    public javax.swing.JPanel getjPanelDireccion() {
        return jPanelDireccion;
    }

    public javax.swing.JPanel getjPanelEmpleado() {
        return jPanelEmpleado;
    }

    public javax.swing.JTabbedPane getjTabbedPaneContenedor() {
        return jTabbedPaneContenedor;
    }

    public javax.swing.JComboBox<String> getJcb_unidad() {
        return jcb_unidad;
    }

    public void setJcb_unidad(javax.swing.JComboBox<String> jcb_unidad) {
        this.jcb_unidad = jcb_unidad;
    }

    public javax.swing.JComboBox<String> getJcb_empleado() {
        return jcb_empleado;
    }

    public void setJcb_empleado(javax.swing.JComboBox<String> jcb_empleado) {
        this.jcb_empleado = jcb_empleado;
    }
    
    
}
