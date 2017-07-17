package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Empleado;
import model.Empresa;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-16T12:01:27")
@StaticMetamodel(Unidad.class)
public class Unidad_ { 

    public static volatile SingularAttribute<Unidad, Empresa> unaEmpresa;
    public static volatile SetAttribute<Unidad, Empleado> empleados;
    public static volatile SingularAttribute<Unidad, Long> id;
    public static volatile SingularAttribute<Unidad, String> nombre;

}