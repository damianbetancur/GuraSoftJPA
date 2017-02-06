package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Localidad;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-02-03T01:27:47")
@StaticMetamodel(Direccion.class)
public class Direccion_ { 

    public static volatile SingularAttribute<Direccion, String> piso;
    public static volatile SingularAttribute<Direccion, String> numero;
    public static volatile SingularAttribute<Direccion, String> calle;
    public static volatile SingularAttribute<Direccion, String> departamento;
    public static volatile SingularAttribute<Direccion, Localidad> localidad;
    public static volatile SingularAttribute<Direccion, Long> id;

}