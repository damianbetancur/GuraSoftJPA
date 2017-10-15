package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empresa;
import model.TipoCliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(ListaDePrecio.class)
public class ListaDePrecio_ { 

    public static volatile SingularAttribute<ListaDePrecio, String> descripcion;
    public static volatile SingularAttribute<ListaDePrecio, TipoCliente> tipoCliente;
    public static volatile SingularAttribute<ListaDePrecio, Empresa> unaEmpresa;
    public static volatile SingularAttribute<ListaDePrecio, Long> id;

}