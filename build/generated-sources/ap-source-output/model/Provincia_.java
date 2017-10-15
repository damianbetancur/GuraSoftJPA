package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Localidad;
import model.Zona;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(Provincia.class)
public class Provincia_ { 

    public static volatile SingularAttribute<Provincia, Zona> zona;
    public static volatile SingularAttribute<Provincia, Long> id;
    public static volatile SingularAttribute<Provincia, String> nombre;
    public static volatile SetAttribute<Provincia, Localidad> localidades;

}