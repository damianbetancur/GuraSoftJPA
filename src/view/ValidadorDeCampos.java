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
    
    public void validarNumeroDecimales(JTextField campo){
        campo.addKeyListener(new KeyAdapter() {            
            public void keyTyped(KeyEvent e){
                int k = (int)e.getKeyChar();
                if(k>=46 && k<=57){
                    if(k==46){
                        String dato = campo.getText();
                        int tamA= dato.length();
                        for(int i=0; i<=tamA; i++){
                            if(dato.contains(".")){
                                e.setKeyChar((char)KeyEvent.VK_CLEAR);
                            }
                        }
                    }
                    if(k==47){
                        e.setKeyChar((char)KeyEvent.VK_CLEAR);
                    }
                }else{
                    e.setKeyChar((char)KeyEvent.VK_CLEAR);
                    e.consume();
                }
                
                
            }
        });
    }
    public int verificarTamanioCampo(JTextField campo){
        int posicion=0;
        char caracter = (char) 0;
        for (int i = 0; i <= campo.getText().length()-1; i++) {
            caracter = campo.getText().charAt(i);
            if (caracter =='.') {
                posicion = i;
            }
        }
        return posicion;
    }
}
