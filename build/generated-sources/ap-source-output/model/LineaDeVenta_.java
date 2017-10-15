package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Articulo;
import model.Venta;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(LineaDeVenta.class)
public class LineaDeVenta_ { 

    public static volatile SingularAttribute<LineaDeVenta, Venta> venta;
    public static volatile SingularAttribute<LineaDeVenta, Long> id;
    public static volatile SingularAttribute<LineaDeVenta, Articulo> articulo;
    public static volatile SingularAttribute<LineaDeVenta, Integer> cantidad;
    public static volatile SingularAttribute<LineaDeVenta, Float> subTotal;

}