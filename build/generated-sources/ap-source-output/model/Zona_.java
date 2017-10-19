package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Provincia;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:17:03")
@StaticMetamodel(Zona.class)
public class Zona_ { 

    public static volatile SetAttribute<Zona, Provincia> provincias;
    public static volatile SingularAttribute<Zona, Long> id;
    public static volatile SingularAttribute<Zona, String> nombre;

}