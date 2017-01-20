/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table (name="UNIDADES_COMERCIALES")
public class UnidadComercial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="cantidadSeccion")
    private int cantidadSeccion;
    
    @Column(name="cantidadDeposito")
    private int cantidadDeposito;
    
    //Empresa a la que pertenece la Unidad Comercial
    @ManyToOne
    private Empresa empresa;
    
    //Empleados contenidas en Unidad Comercial
    @ManyToMany(targetEntity=Empleado.class)
    private Set empleados;
    
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
        if (!(object instanceof UnidadComercial)) {
            return false;
        }
        UnidadComercial other = (UnidadComercial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UnidadComercial[ id=" + id + " ]";
    }

    public int getCantidadSeccion() {
        return cantidadSeccion;
    }

    public void setCantidadSeccion(int cantidadSeccion) {
        this.cantidadSeccion = cantidadSeccion;
    }

    public int getCantidadDeposito() {
        return cantidadDeposito;
    }

    public void setCantidadDeposito(int cantidadDeposito) {
        this.cantidadDeposito = cantidadDeposito;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Set getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set empleados) {
        this.empleados = empleados;
    }
    
    
}
