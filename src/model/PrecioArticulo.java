
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
@Table (name="PRECIO_ARTICULO")
@IdClass(PrecioArticuloPK.class)
public class PrecioArticulo implements Serializable {

    public PrecioArticulo(){}
    

    @Id
    @Column(name="ID_articulo")
    private Long id_articulo;

    @Id
    @Column(name="ID_listaDePrecios")
    private Long id_listaDePrecio;

    @Column(name="PRECIO",length=50)
    private float precio;

    public Long getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(Long id_articulo) {
        this.id_articulo = id_articulo;
    }

    public Long getId_listaDePrecio() {
        return id_listaDePrecio;
    }

    public void setId_listaDePrecio(Long id_listaDePrecio) {
        this.id_listaDePrecio = id_listaDePrecio;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    
    
    
}
