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
public class StockArticuloPK implements Serializable {

    public StockArticuloPK() {
    }

    private Long id_articulo;
    private Long id_Deposito;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_articulo != null ? id_articulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        StockArticuloPK myId = (StockArticuloPK) o;

        if ((o instanceof PrecioArticuloPK)
                && (this.getId_Deposito().equals(myId.getId_Deposito()))
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

    public Long getId_Deposito() {
        return id_Deposito;
    }

    public void setId_Deposito(Long id_Deposito) {
        this.id_Deposito = id_Deposito;
    }

}
