/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Ariel
 */
@Embeddable
public class PrecioArticuloPK implements Serializable {

    public PrecioArticuloPK() {
    }

    private Long id_articulo;
    private Long id_listaDePrecio;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_articulo != null ? id_articulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        PrecioArticuloPK myId = (PrecioArticuloPK) o;

        if ((o instanceof PrecioArticuloPK)
                && (this.getId_listaDePrecio().equals(myId.getId_listaDePrecio()))
                && (this.id_articulo == myId.getId_articulo())) {
            flag = true;
        }
        return flag;
    }
// rest of the code with getters only

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

}
