package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Cliente;
import model.Empleado;
import model.LineaDeVenta;
import model.Pago;
import model.Unidad;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Date> fecha;
    public static volatile SingularAttribute<Venta, Unidad> unidad;
    public static volatile SingularAttribute<Venta, Cliente> cliente;
    public static volatile ListAttribute<Venta, LineaDeVenta> lineaDeVenta;
    public static volatile SingularAttribute<Venta, Boolean> esCompleta;
    public static volatile SingularAttribute<Venta, Empleado> empleado;
    public static volatile SingularAttribute<Venta, Date> hora;
    public static volatile SingularAttribute<Venta, Long> id;
    public static volatile SingularAttribute<Venta, Pago> pago;

}