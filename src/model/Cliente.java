/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ariel
 */
@Entity
@DiscriminatorValue( value="CLIENTE" )
public class Cliente extends Persona{
    
   @Column(name="cuit")
    private String cuit;

   @ManyToOne
    private CuentaCorriente cuentaCorriente;
   
    public String getCuit() {
        return cuit;
    }    

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Cliente(String cuit) {
        super( );
        this.cuit = cuit;
    }
   
    public Cliente() {
        super( );       
    }

    public CuentaCorriente getCuentaCorriente() {
        return cuentaCorriente;
    }

    public void setCuentaCorriente(CuentaCorriente unaCuentaCorriente) {
        this.cuentaCorriente = unaCuentaCorriente;
    }
    
    
}