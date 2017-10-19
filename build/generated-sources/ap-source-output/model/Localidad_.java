package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Provincia;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:17:03")
@StaticMetamodel(Localidad.class)
public class Localidad_ { 

    public static volatile SingularAttribute<Localidad, String> codigoPostal;
    public static volatile SingularAttribute<Localidad, Long> id;
    public static volatile SingularAttribute<Localidad, Provincia> provincia;
    public static volatile SingularAttribute<Localidad, String> nombre;

}