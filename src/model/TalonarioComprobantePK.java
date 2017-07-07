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
public class TalonarioComprobantePK implements Serializable {
    public TalonarioComprobantePK() {
    }

    private Long id_Unidad;
    private Long id_TipoCliente;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_Unidad != null ? id_Unidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        boolean flag = false;
        TalonarioComprobantePK myId = (TalonarioComprobantePK) o;

        if ((o instanceof TalonarioComprobantePK)
                && (this.getId_TipoCliente().equals(myId.getId_TipoCliente()))
                && (this.id_Unidad == myId.getId_Unidad())) {
            flag = true;
        }
        return flag;
    }

    public Long getId_Unidad() {
        return id_Unidad;
    }

    public void setId_Unidad(Long id_Unidad) {
        this.id_Unidad = id_Unidad;
    }

    public Long getId_TipoCliente() {
        return id_TipoCliente;
    }

    public void setId_TipoCliente(Long id_TipoCliente) {
        this.id_TipoCliente = id_TipoCliente;
    }
    
    
}
