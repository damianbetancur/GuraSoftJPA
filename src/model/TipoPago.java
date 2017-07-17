/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ariel
 */
public enum TipoPago {
    CONTADO ("Contado"), //Separamos con comas
    CTACTE ("Cuenta Corriente");
    
 
    //Campos tipo constante   
    private final String tipo; 
 
    /**
     * Constructor. Al asignarle uno de los valores posibles a una variable del tipo enumerado el constructor asigna 
        automáticamente valores de los campos
     */ 
    TipoPago (String tipo) { 
        this.tipo = tipo;
    } //Cierre del constructor
 
    //Métodos de la clase tipo Enum
    public String getTipo() { return tipo; }
}
