package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Catalogo;
import model.Cliente;
import model.Direccion;
import model.Proveedor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-19T04:17:03")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile SingularAttribute<Empresa, String> razonSocial;
    public static volatile SingularAttribute<Empresa, String> cuit;
    public static volatile SingularAttribute<Empresa, Direccion> direccion;
    public static volatile ListAttribute<Empresa, Cliente> listaClientes;
    public static volatile SingularAttribute<Empresa, Catalogo> catalogo;
    public static volatile SingularAttribute<Empresa, Long> id;
    public static volatile ListAttribute<Empresa, Proveedor> listaproveedores;

}