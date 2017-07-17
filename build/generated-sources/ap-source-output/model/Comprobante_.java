package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Venta;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-16T12:01:27")
@StaticMetamodel(Comprobante.class)
public abstract class Comprobante_ { 

    public static volatile SingularAttribute<Comprobante, Date> fecha;
    public static volatile SingularAttribute<Comprobante, Venta> venta;
    public static volatile SingularAttribute<Comprobante, Long> id;

}