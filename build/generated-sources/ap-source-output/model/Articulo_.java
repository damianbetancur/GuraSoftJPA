package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CategoriaDeCatalogo;
import model.Proveedor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-16T12:01:27")
@StaticMetamodel(Articulo.class)
public class Articulo_ { 

    public static volatile SingularAttribute<Articulo, String> descripcion;
    public static volatile SingularAttribute<Articulo, Long> id;
    public static volatile SingularAttribute<Articulo, Proveedor> unProveedor;
    public static volatile SingularAttribute<Articulo, CategoriaDeCatalogo> unCategoriaDeCatalogo;

}