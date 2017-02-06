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
@Table (name="EMPRESAS")
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="razonSocial")
    private String razonSocial;
    
    @Column(name="cuit")
    private String cuit;
    
    
    //Direccion a la que pertenece la Empresa
    @ManyToOne
    private Direccion direccion;
    
    //Catalogo a la que pertenece la Empresa
    @ManyToOne
    private CatalogoArticulos catalogo;
    
    //Clientes que posee la empresa
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unaEmpresa")
    private List <Cliente> listaClientes;

    //Proveedores que posee la empresa
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unaEmpresa")
    private List <Proveedor> listaproveedores;
    
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
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Empresa[ id=" + id + " ]";
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List <Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List <Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public CatalogoArticulos getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalogoArticulos catalogo) {
        this.catalogo = catalogo;
    }

    public List <Proveedor> getListaproveedores() {
        return listaproveedores;
    }

    public void setListaproveedores(List <Proveedor> listaproveedores) {
        this.listaproveedores = listaproveedores;
    }
   

    
    
}
