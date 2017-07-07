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
public class JDialogBuscarUsuario extends javax.swing.JDialog implements InterfacePanel {

    private ValidadorDeCampos validador;
    /**
     * Creates new form JDialogBuscarUsuario
     */
    public JDialogBuscarUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.validador = new ValidadorDeCampos();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jbtn_Agregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jtf_BuscarCliente = new javax.swing.JTextField();
        jlbl_TituloBusqueda = new javax.swing.JLabel();
        jTabbedPaneContenedor = new javax.swing.JTabbedPane();
        jPanelEmpleado = new javax.swing.JPanel();
        jlbl_Nombre = new javax.swing.JLabel();
        jlbl_Apellido = new javax.swing.JLabel();
        jlbl_cuitCuil = new javax.swing.JLabel();
        jlbl_ID = new javax.swing.JLabel();
        jtf_ID = new javax.swing.JTextField();
        jtf_Nombre = new javax.swing.JTextField();
        jtf_cuitCuil = new javax.swing.JTextField();
        jtf_Apellido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jcb_tipoCliente = new javax.swing.JComboBox<>();
        jlbl_tipoCliente = new javax.swing.JLabel();
        jlbl_ctaCte = new javax.swing.JLabel();
        jtf_ctaCte = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(null);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Lista de Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 14))); // NOI18N
        jPanel2.setMinimumSize(new java.awt.Dimension(730, 290));
        jPanel2.setLayout(null);

        jbtn_Agregar.setText("Agregar");
        jPanel2.add(jbtn_Agregar);
        jbtn_Agregar.setBounds(10, 140, 80, 30);

        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaClientes);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 560, 100);

        jPanel1.add(jPanel2);
        jPanel2.setBounds(10, 80, 580, 180);

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlbl_TituloBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtf_BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(176, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtf_BuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlbl_TituloBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(10, 20, 580, 50);

        jPanelEmpleado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanelEmpleado.setName("Empleado"); // NOI18N
        jPanelEmpleado.setLayout(null);

        jlbl_Nombre.setText("Nombre:");
        jPanelEmpleado.add(jlbl_Nombre);
        jlbl_Nombre.setBounds(60, 80, 100, 14);

        jlbl_Apellido.setText("Apellido:");
        jPanelEmpleado.add(jlbl_Apellido);
        jlbl_Apellido.setBounds(60, 120, 90, 14);

        jlbl_cuitCuil.setText("CUIT-CUIL:");
        jPanelEmpleado.add(jlbl_cuitCuil);
        jlbl_cuitCuil.setBounds(60, 160, 80, 14);

        jlbl_ID.setText("ID:");
        jPanelEmpleado.add(jlbl_ID);
        jlbl_ID.setBounds(60, 40, 90, 14);
        jPanelEmpleado.add(jtf_ID);
        jtf_ID.setBounds(160, 40, 272, 20);
        jPanelEmpleado.add(jtf_Nombre);
        jtf_Nombre.setBounds(160, 80, 272, 20);
        jPanelEmpleado.add(jtf_cuitCuil);
        jtf_cuitCuil.setBounds(160, 160, 272, 20);
        jPanelEmpleado.add(jtf_Apellido);
        jtf_Apellido.setBounds(160, 120, 272, 20);
        jPanelEmpleado.add(jLabel1);
        jLabel1.setBounds(450, 40, 210, 0);

        jPanelEmpleado.add(jcb_tipoCliente);
        jcb_tipoCliente.setBounds(160, 200, 270, 22);

        jlbl_tipoCliente.setText("Tipo Cliente:");
        jPanelEmpleado.add(jlbl_tipoCliente);
        jlbl_tipoCliente.setBounds(60, 200, 80, 14);

        jlbl_ctaCte.setText("CTA / CTE:");
        jPanelEmpleado.add(jlbl_ctaCte);
        jlbl_ctaCte.setBounds(60, 250, 80, 14);
        jPanelEmpleado.add(jtf_ctaCte);
        jtf_ctaCte.setBounds(160, 250, 272, 20);

        jTabbedPaneContenedor.addTab("Cliente", jPanelEmpleado);

        jPanelDireccion.setLayout(null);

        jlbl_zona_direccion.setText("Zona:");
        jPanelDireccion.add(jlbl_zona_direccion);
        jlbl_zona_direccion.setBounds(40, 30, 60, 14);

        jPanelDireccion.add(jcb_zona_direccion);
        jcb_zona_direccion.setBounds(150, 30, 180, 22);

        jlbl_provincia_direccion.setText("Provincia:");
        jPanelDireccion.add(jlbl_provincia_direccion);
        jlbl_provincia_direccion.setBounds(40, 70, 60, 14);

        jPanelDireccion.add(jcb_provincia_direccion);
        jcb_provincia_direccion.setBounds(150, 70, 180, 22);

        jPanelDireccion.add(jcb_localidad_direccion);
        jcb_localidad_direccion.setBounds(150, 110, 180, 22);

        jlbl_localidad_direccion.setText("Localidad:");
        jPanelDireccion.add(jlbl_localidad_direccion);
        jlbl_localidad_direccion.setBounds(40, 110, 60, 14);

        jlbl_calle_direccion.setText("Calle:");
        jPanelDireccion.add(jlbl_calle_direccion);
        jlbl_calle_direccion.setBounds(40, 150, 60, 14);
        jPanelDireccion.add(jtf_calle_direccion);
        jtf_calle_direccion.setBounds(150, 150, 180, 20);
        jPanelDireccion.add(jtf_numero_direccion);
        jtf_numero_direccion.setBounds(150, 180, 180, 20);

        jlbl_numero_direccion.setText("Número:");
        jPanelDireccion.add(jlbl_numero_direccion);
        jlbl_numero_direccion.setBounds(40, 180, 60, 14);
        jPanelDireccion.add(jtf_piso_direccion);
        jtf_piso_direccion.setBounds(150, 210, 180, 20);

        jlbl_piso_direccion.setText("Piso:");
        jPanelDireccion.add(jlbl_piso_direccion);
        jlbl_piso_direccion.setBounds(40, 210, 60, 14);
        jPanelDireccion.add(jtf_departamento_direccion);
        jtf_departamento_direccion.setBounds(150, 240, 180, 20);

        jlbl_departamento_direccion.setText("Departamento:");
        jPanelDireccion.add(jlbl_departamento_direccion);
        jlbl_departamento_direccion.setBounds(40, 240, 90, 14);

        jTabbedPaneContenedor.addTab("Dirección", jPanelDireccion);

        jPanel1.add(jTabbedPaneContenedor);
        jTabbedPaneContenedor.setBounds(10, 260, 580, 310);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelDireccion;
    private javax.swing.JPanel jPanelEmpleado;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPaneContenedor;
    private javax.swing.JButton jbtn_Agregar;
    private javax.swing.JComboBox<String> jcb_localidad_direccion;
    private javax.swing.JComboBox<String> jcb_provincia_direccion;
    private javax.swing.JComboBox<String> jcb_tipoCliente;
    private javax.swing.JComboBox<String> jcb_zona_direccion;
    private javax.swing.JLabel jlbl_Apellido;
    private javax.swing.JLabel jlbl_ID;
    private javax.swing.JLabel jlbl_Nombre;
    private javax.swing.JLabel jlbl_TituloBusqueda;
    private javax.swing.JLabel jlbl_calle_direccion;
    private javax.swing.JLabel jlbl_ctaCte;
    private javax.swing.JLabel jlbl_cuitCuil;
    private javax.swing.JLabel jlbl_departamento_direccion;
    private javax.swing.JLabel jlbl_localidad_direccion;
    private javax.swing.JLabel jlbl_numero_direccion;
    private javax.swing.JLabel jlbl_piso_direccion;
    private javax.swing.JLabel jlbl_provincia_direccion;
    private javax.swing.JLabel jlbl_tipoCliente;
    private javax.swing.JLabel jlbl_zona_direccion;
    private javax.swing.JTextField jtf_Apellido;
    private javax.swing.JTextField jtf_BuscarCliente;
    private javax.swing.JTextField jtf_ID;
    private javax.swing.JTextField jtf_Nombre;
    private javax.swing.JTextField jtf_calle_direccion;
    private javax.swing.JTextField jtf_ctaCte;
    private javax.swing.JTextField jtf_cuitCuil;
    private javax.swing.JTextField jtf_departamento_direccion;
    private javax.swing.JTextField jtf_numero_direccion;
    private javax.swing.JTextField jtf_piso_direccion;
    private javax.swing.JTable tablaClientes;
    // End of variables declaration//GEN-END:variables


public void setControlador(Controller c) {
    try {
        //Agrega Botones al Escuchador ActionListener para manejar los eventos realizados
        jbtn_Agregar.addActionListener(c);
        
        
        //Agrega JTextField del Panel Datos del Empleado al escuchador KeyListener para verificar campos ingresados
        jtf_Nombre.addKeyListener(c);
        jtf_Apellido.addKeyListener(c);
        jtf_cuitCuil.addKeyListener(c);
        
        //Agrega JTextField del Panel Direccion al escuchador KeyListener para verificar campos ingresados
        jtf_calle_direccion.addKeyListener(c);
        jtf_numero_direccion.addKeyListener(c);
        jtf_piso_direccion.addKeyListener(c);
        jtf_departamento_direccion.addKeyListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_zona_direccion.addItemListener(c);
        jcb_provincia_direccion.addItemListener(c);
        jcb_localidad_direccion.addItemListener(c);        
        jcb_tipoCliente.addItemListener(c);
        
        //Agrega JcomboBox del Panel Direccion al escuchador ItemListener para verificar items seleccionados
        jcb_zona_direccion.addFocusListener(c);
        jcb_provincia_direccion.addFocusListener(c);
        jcb_localidad_direccion.addFocusListener(c);
        jcb_tipoCliente.addFocusListener(c);
        
        //Agrego la tabla al escuchador Mouse Listener para verificar elementos seleccionados
        tablaClientes.addMouseListener(c);
        
        jtf_BuscarCliente.addKeyListener(c);
        jtf_BuscarCliente.addFocusListener(c);
    } catch (Exception e) {
    }
        
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

    public javax.swing.JTable getTablaClientes() {
        return tablaClientes;
    }

    @Override
    public void habilitarCombobox(boolean h, JComboBox campo) {
        campo.setEnabled(h);
    }

    @Override
    public void limpiarCombobox(JComboBox campo) {
        campo.removeAllItems();
    }

    public javax.swing.JButton getJbtn_Agregar() {
        return jbtn_Agregar;
    }
    

    public javax.swing.JComboBox<String> getJcb_localidad_direccion() {
        return jcb_localidad_direccion;
    }

    public javax.swing.JComboBox<String> getJcb_provincia_direccion() {
        return jcb_provincia_direccion;
    }

    public javax.swing.JComboBox<String> getJcb_tipoCliente() {
        return jcb_tipoCliente;
    }

    public javax.swing.JComboBox<String> getJcb_zona_direccion() {
        return jcb_zona_direccion;
    }

    public javax.swing.JTextField getJtf_Apellido() {
        return jtf_Apellido;
    }

    public javax.swing.JTextField getJtf_BuscarCliente() {
        return jtf_BuscarCliente;
    }

    public javax.swing.JTextField getJtf_cuitCuil() {
        return jtf_cuitCuil;
    }

    public javax.swing.JTextField getJtf_ID() {
        return jtf_ID;
    }

    public javax.swing.JTextField getJtf_Nombre() {
        return jtf_Nombre;
    }

    public javax.swing.JTextField getJtf_calle_direccion() {
        return jtf_calle_direccion;
    }

    public javax.swing.JTextField getJtf_ctaCte() {
        return jtf_ctaCte;
    }

    public javax.swing.JTextField getJtf_departamento_direccion() {
        return jtf_departamento_direccion;
    }

    public javax.swing.JTextField getJtf_numero_direccion() {
        return jtf_numero_direccion;
    }

    public javax.swing.JTextField getJtf_piso_direccion() {
        return jtf_piso_direccion;
    }

    public javax.swing.JLabel getJlbl_TituloBusqueda() {
        return jlbl_TituloBusqueda;
    }

    public void setJlbl_TituloBusqueda(javax.swing.JLabel jlbl_TituloBusqueda) {
        this.jlbl_TituloBusqueda = jlbl_TituloBusqueda;
    }
    
    
}
