/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Ariel
 */
public class ValidadorDeCampos {
    public void validarSoloLetras(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if (Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    
    }
    
    public void validarSoloNumero(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {            
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    
    }
    
    public void LimitarCaracteres(JTextField campo, int cantidad){
        campo.addKeyListener(new KeyAdapter() {            
            public void keyTyped(KeyEvent e){
                char c = e.getKeyChar();
                int tam = campo.getText().length();
                if (tam>=cantidad) {
                    e.consume();
                }
            }
        });
    
    }
}
