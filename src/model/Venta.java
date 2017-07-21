/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ariel
 */
@Entity
@Table(name = "VENTA")
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA")
    private Date fecha;
    
    @Temporal(TemporalType.TIME)
    @Column(name = "HORA")
    private Date hora;
    
    @Column(name = "ES_COMPLETA")
    private boolean esCompleta;

   
    private Unidad unidad;
    
    
    private Empleado empleado;
    
    
    private Cliente cliente;
    
   
    private Pago pago;
    
    
    
    //LineaDe Venta que posee la Venta
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venta")
    private List<LineaDeVenta> lineaDeVenta;
    
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
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Venta[ id=" + id + " ]";
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

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    

    public List<LineaDeVenta> getLineaDeVenta() {
        return lineaDeVenta;
    }

    public void setLineaDeVenta(List<LineaDeVenta> lineaDeVenta) {
        this.lineaDeVenta = lineaDeVenta;
    }

   
    
}
