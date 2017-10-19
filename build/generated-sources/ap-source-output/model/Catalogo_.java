package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.CategoriaDeCatalogo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:17:03")
@StaticMetamodel(Catalogo.class)
public class Catalogo_ { 

    public static volatile SingularAttribute<Catalogo, String> descripcion;
    public static volatile ListAttribute<Catalogo, CategoriaDeCatalogo> listaCategoriaCatalogo;
    public static volatile SingularAttribute<Catalogo, Long> id;

}