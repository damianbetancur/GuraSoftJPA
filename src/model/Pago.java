/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ariel
 */
@Entity
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    @Column(name = "PRECIO", length = 50)
    private float precio;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA")
    private Date fecha;
    
    @Temporal(TemporalType.TIME)
    @Column(name = "HORA")
    private Date hora;
    
    @Column(name = "ES_COMPLETA")
    private boolean esCompleta;
    
    @Column(name = "cantidad")
    private float cantidad;
    
    @Column(name = "saldo")
    private float saldo;
    
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)) {
            return false;
        }
        Pago other = (Pago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Pago[ id=" + id + " ]";
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public boolean isEsCompleta() {
        return esCompleta;
    }

    public void setEsCompleta(boolean esCompleta) {
        this.esCompleta = esCompleta;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
    
    public void actualizarCuentaCorriente(Cliente unCliente, boolean enCuentaCorriente){
        if(enCuentaCorriente){
            //Lo que debe el Cliente + el pago actual
            unCliente.getCuentaCorriente().setSaldo(unCliente.getCuentaCorriente().getSaldo()+this.getPrecio());
            this.setSaldo(this.getPrecio());
            this.setEsCompleta(false);
        }else{
            this.setSaldo(0);
            this.setEsCompleta(false);
        }
    
    }
    
}
