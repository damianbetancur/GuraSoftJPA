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
public interface InterfacePanel {
    
    public void setControlador(Controller c);
    
    public void limpiarCampo(JTextField campo);
    
    public void limpiarCombobox(JComboBox campo);
    
    public void habilitarCampo(boolean h, JTextField campo);

    public void habilitarBoton(boolean h, JButton btn);

    public void habilitarCombobox(boolean h, JComboBox campo);
    
    public ValidadorDeCampos getValidador();
    
}
