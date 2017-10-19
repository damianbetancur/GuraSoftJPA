package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.TipoEmpleado;
import model.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:17:03")
@StaticMetamodel(Empleado.class)
public class Empleado_ extends Persona_ {

    public static volatile SingularAttribute<Empleado, Usuario> unUsuario;
    public static volatile SingularAttribute<Empleado, Date> fechaIngreso;
    public static volatile SingularAttribute<Empleado, TipoEmpleado> tipoEmpleado;
    public static volatile SingularAttribute<Empleado, String> cuitCuil;

}