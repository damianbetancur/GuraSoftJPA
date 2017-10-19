package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TipoPago;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:35:28")
@StaticMetamodel(Pago.class)
public class Pago_ { 

    public static volatile SingularAttribute<Pago, Date> fecha;
    public static volatile SingularAttribute<Pago, Float> total;
    public static volatile SingularAttribute<Pago, Boolean> esCompleta;
    public static volatile SingularAttribute<Pago, Float> iva;
    public static volatile SingularAttribute<Pago, Date> hora;
    public static volatile SingularAttribute<Pago, Float> descuento;
    public static volatile SingularAttribute<Pago, TipoPago> tipoPago;
    public static volatile SingularAttribute<Pago, Long> id;
    public static volatile SingularAttribute<Pago, Float> subTotal;

}