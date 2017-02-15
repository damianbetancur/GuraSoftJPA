/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table (name="lISTA DE PRECIOS")
public class ListaDePrecio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="descripcion")
    private String descripcion;
    
    //Empresa a la que pertenece la lista de precio
    @ManyToOne
    private Empresa unaEmpresa;
    
    ///CategoriaProducto que posee el CatalogoArticulos
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unaListaPrecioArticulos")
    private List <PrecioArticulo> listaDePrecioArticulos;
    
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
        if (!(object instanceof ListaDePrecio)) {
            return false;
        }
        ListaDePrecio other = (ListaDePrecio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ListaDePrecio[ id=" + id + " ]";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empresa getUnaEmpresa() {
        return unaEmpresa;
    }

    public void setUnaEmpresa(Empresa unaEmpresa) {
        this.unaEmpresa = unaEmpresa;
    }

    public List <PrecioArticulo> getListaDePrecioArticulos() {
        return listaDePrecioArticulos;
    }

    public void setListaDePrecioArticulos(List <PrecioArticulo> listaDePrecioArticulos) {
        this.listaDePrecioArticulos = listaDePrecioArticulos;
    }
    
}
