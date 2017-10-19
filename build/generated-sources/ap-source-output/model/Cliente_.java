package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CuentaCorriente;
import model.Empresa;
import model.TipoCliente;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(Cliente.class)
public class Cliente_ extends Persona_ {

    public static volatile SingularAttribute<Cliente, TipoCliente> tipocliente;
    public static volatile SingularAttribute<Cliente, Empresa> unaEmpresa;
    public static volatile SingularAttribute<Cliente, CuentaCorriente> cuentaCorriente;
    public static volatile SingularAttribute<Cliente, String> cuitCuil;

}