package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Articulo;
import model.CuentaCorriente;
import model.Empresa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ extends Persona_ {

    public static volatile SingularAttribute<Proveedor, String> cuit;
    public static volatile ListAttribute<Proveedor, Articulo> listaDeArticulos;
    public static volatile SingularAttribute<Proveedor, String> RazonSocial;
    public static volatile SingularAttribute<Proveedor, Empresa> unaEmpresa;
    public static volatile SingularAttribute<Proveedor, CuentaCorriente> cuentaCorriente;

}