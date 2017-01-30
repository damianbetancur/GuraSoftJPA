/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Ariel
 */
@Entity
@DiscriminatorValue( value="EMPLEADO" )
public class Empleado extends Persona{
   
    @ManyToOne
    private TipoEmpleado tipoEmpleado;
    
}
