package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Direccion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-01-22T17:33:45")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile SingularAttribute<Empresa, String> razonSocial;
    public static volatile SingularAttribute<Empresa, String> cuit;
    public static volatile SingularAttribute<Empresa, Direccion> direccion;
    public static volatile SingularAttribute<Empresa, Long> id;

}