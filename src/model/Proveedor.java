/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Ariel
 */
@Entity
@DiscriminatorValue(value = "PROVEEDOR")
public class Proveedor extends Persona {

    @Column(name = "cuit")
    private String cuit;

    @Column(name = "razon_Social")
    private String RazonSocial;

    @ManyToOne
    private Empresa unaEmpresa;

    ///Articulos que posee la Catalogoria de Articulos
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "unProveedor")
    private List<Articulo> listaDeArticulos;

    public String getCuit() {
        return cuit;
    }

    @ManyToOne
    private CuentaCorriente cuentaCorriente;

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Proveedor(String cuit) {
        super();
        this.cuit = cuit;
    }

    public Proveedor() {
        super();
    }

    public CuentaCorriente getCuentaCorriente() {
        return cuentaCorriente;
    }

    public void setCuentaCorriente(CuentaCorriente unaCuentaCorriente) {
        this.cuentaCorriente = unaCuentaCorriente;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String RazonSocial) {
        this.RazonSocial = RazonSocial;
    }

    public List<Articulo> getListaDeArticulos() {
        return listaDeArticulos;
    }

    public void setListaDeArticulos(List<Articulo> listaDeArticulos) {
        this.listaDeArticulos = listaDeArticulos;
    }

    public Empresa getUnaEmpresa() {
        return unaEmpresa;
    }

    public void setUnaEmpresa(Empresa unaEmpresa) {
        this.unaEmpresa = unaEmpresa;
    }

    @Override
    public String toString() {
        return getRazonSocial();
    }

}
