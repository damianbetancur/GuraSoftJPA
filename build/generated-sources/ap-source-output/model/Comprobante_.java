package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Venta;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:17:03")
@StaticMetamodel(Comprobante.class)
public abstract class Comprobante_ { 

    public static volatile SingularAttribute<Comprobante, Date> fecha;
    public static volatile SingularAttribute<Comprobante, Float> total;
    public static volatile SingularAttribute<Comprobante, Venta> venta;
    public static volatile SingularAttribute<Comprobante, Float> iva;
    public static volatile SingularAttribute<Comprobante, Float> descuento;
    public static volatile SingularAttribute<Comprobante, Integer> numeroComprobante;
    public static volatile SingularAttribute<Comprobante, Long> id;
    public static volatile SingularAttribute<Comprobante, Float> subTotal;

}