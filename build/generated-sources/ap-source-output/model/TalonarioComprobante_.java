package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-16T12:01:27")
@StaticMetamodel(TalonarioComprobante.class)
public class TalonarioComprobante_ { 

    public static volatile SingularAttribute<TalonarioComprobante, String> descripcion;
    public static volatile SingularAttribute<TalonarioComprobante, Long> id_TipoCliente;
    public static volatile SingularAttribute<TalonarioComprobante, Integer> numeracion_Actual;
    public static volatile SingularAttribute<TalonarioComprobante, Long> id_Unidad;
    public static volatile SingularAttribute<TalonarioComprobante, Integer> numeracion_Hasta;
    public static volatile SingularAttribute<TalonarioComprobante, Integer> numeracion_Desde;

}