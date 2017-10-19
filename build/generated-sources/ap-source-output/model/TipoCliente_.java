package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.ListaDePrecio;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(TipoCliente.class)
public class TipoCliente_ { 

    public static volatile SingularAttribute<TipoCliente, String> descripcion;
    public static volatile ListAttribute<TipoCliente, ListaDePrecio> listaDePercio;
    public static volatile SingularAttribute<TipoCliente, Long> id;

}