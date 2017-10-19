package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:35:28")
@StaticMetamodel(StockArticulo.class)
public class StockArticulo_ { 

    public static volatile SingularAttribute<StockArticulo, Integer> stockMinimo;
    public static volatile SingularAttribute<StockArticulo, Integer> stockActual;
    public static volatile SingularAttribute<StockArticulo, Long> id_articulo;
    public static volatile SingularAttribute<StockArticulo, Long> id_Deposito;
    public static volatile SingularAttribute<StockArticulo, Integer> stockMaximo;

}