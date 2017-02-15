/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table (name="PRECIO ARTICULOS")
public class PrecioArticulo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private ListaDePrecio unaListaPrecioArticulos;
    
    @ManyToOne
    private Articulo unArticulo;
    
    @Column(name="precio")
    private float precio;
    
    public Long getId() {
        return id;
    }

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
        if (!(object instanceof PrecioArticulo)) {
            return false;
        }
        PrecioArticulo other = (PrecioArticulo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PrecioArticulo[ id=" + id + " ]";
    }

    public ListaDePrecio getUnaListaPrecioArticulos() {
        return unaListaPrecioArticulos;
    }

    public void setUnaListaPrecioArticulos(ListaDePrecio unaListaPrecioArticulos) {
        this.unaListaPrecioArticulos = unaListaPrecioArticulos;
    }

    public Articulo getUnArticulo() {
        return unArticulo;
    }

    public void setUnArticulo(Articulo unArticulo) {
        this.unArticulo = unArticulo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
}
