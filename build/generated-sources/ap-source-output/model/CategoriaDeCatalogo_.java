package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Articulo;
import model.Catalogo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-07-20T23:36:07")
@StaticMetamodel(CategoriaDeCatalogo.class)
public class CategoriaDeCatalogo_ { 

    public static volatile SingularAttribute<CategoriaDeCatalogo, String> descripcion;
    public static volatile SingularAttribute<CategoriaDeCatalogo, Catalogo> unCatalogo;
    public static volatile ListAttribute<CategoriaDeCatalogo, Articulo> listaDeArticulos;
    public static volatile SingularAttribute<CategoriaDeCatalogo, Long> id;

}