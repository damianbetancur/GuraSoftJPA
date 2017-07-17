package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-16T12:01:27")
@StaticMetamodel(Pago.class)
public class Pago_ { 

    public static volatile SingularAttribute<Pago, Date> fecha;
    public static volatile SingularAttribute<Pago, Float> precio;
    public static volatile SingularAttribute<Pago, Boolean> esCompleta;
    public static volatile SingularAttribute<Pago, Date> hora;
    public static volatile SingularAttribute<Pago, Long> id;
    public static volatile SingularAttribute<Pago, Float> cantidad;
    public static volatile SingularAttribute<Pago, Float> saldo;

}