/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author Ariel
 */
public interface InterfacePanel {
    
    public void limpiarCampo(JTextField campo);
    
    public void habilitarCampo(boolean h, JTextField campo);

    public void habilitarBoton(boolean h, JButton btn);

    public ValidadorDeCampos getValidador();
    
}
