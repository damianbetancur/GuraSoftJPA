/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelTest;

import model.Cliente;
import model.CuentaCorriente;
import model.Empresa;
import model.TipoCliente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ariel
 */
public class ClienteJUnitTest {
    
    Cliente unCliente;
    CuentaCorriente unaCuentaCorriente;
    TipoCliente unTipocliente;
    Empresa unaEmpresa;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("before()");
        unCliente = new Cliente();
        unaCuentaCorriente = new CuentaCorriente();
        unTipocliente = new TipoCliente();
        unaEmpresa = new Empresa();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCliente(){
                 
        unCliente.setUnaEmpresa(unaEmpresa);
        //Verifica que la Empresa es la misma en cliente --> resultado esperado
        assertSame("Empresa agregada OK", unaEmpresa.getClass(), unCliente.getUnaEmpresa().getClass());
        
        unCliente.setCuentaCorriente(unaCuentaCorriente);
        //Verifica que la cuenta corriente es la misma en cliente 
        assertSame("Cuenta corriente agregada OK",unaCuentaCorriente.getClass(), unCliente.getCuentaCorriente().getClass());
        
        unCliente.setTipocliente(unTipocliente);
        //Verifica que Tipo de Cliente es el mismo en cliente 
        assertSame("Tipo de Cliente agregado OK",unTipocliente.getClass(), unCliente.getTipocliente().getClass());
        
    }
    
}
