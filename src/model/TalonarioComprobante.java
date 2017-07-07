/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "TALONARIO_COMPROBANTE")
@IdClass(TalonarioComprobantePK.class)
public class TalonarioComprobante implements Serializable {

    public TalonarioComprobante() {
    }

    @Id
    @Column(name = "ID_Unidad")
    private Long id_Unidad;

    @Id
    @Column(name = "ID_TipoCliente")
    private Long id_TipoCliente;

    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "numeracion_Desde", length = 8)
    private int numeracion_Desde;

    @Column(name = "numeracion_Hasta", length = 8)
    private int numeracion_Hasta;

    @Column(name = "numeracion_Actual", length = 8)
    private int numeracion_Actual;

    public Long getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(Long id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public Long getId_TipoCliente() {
        return id_TipoCliente;
    }

    public void setId_TipoCliente(Long id_TipoCliente) {
        this.id_TipoCliente = id_TipoCliente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumeracion_Desde() {
        return numeracion_Desde;
    }

    public void setNumeracion_Desde(int numeracion_Desde) {
        this.numeracion_Desde = numeracion_Desde;
    }

    public int getNumeracion_Hasta() {
        return numeracion_Hasta;
    }

    public void setNumeracion_Hasta(int numeracion_Hasta) {
        this.numeracion_Hasta = numeracion_Hasta;
    }

    public int getNumeracion_Actual() {
        return numeracion_Actual;
    }

    public void setNumeracion_Actual(int numeracion_Actual) {
        this.numeracion_Actual = numeracion_Actual;
    }
    
    
    
}
