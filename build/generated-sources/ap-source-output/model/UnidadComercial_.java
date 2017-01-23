package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empleado;
import model.Empresa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-22T17:33:45")
@StaticMetamodel(UnidadComercial.class)
public class UnidadComercial_ { 

    public static volatile SingularAttribute<UnidadComercial, Integer> cantidadSeccion;
    public static volatile SetAttribute<UnidadComercial, Empleado> empleados;
    public static volatile SingularAttribute<UnidadComercial, Long> id;
    public static volatile SingularAttribute<UnidadComercial, Integer> cantidadDeposito;
    public static volatile SingularAttribute<UnidadComercial, Empresa> empresa;

}