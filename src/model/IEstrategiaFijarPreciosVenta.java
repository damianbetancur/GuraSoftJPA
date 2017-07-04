/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;

/**
 *
 * @author Ariel
 */
public abstract class IEstrategiaFijarPreciosVenta {
    protected List<PrecioArticulo> listaPrecioArticulo;
    protected Venta venta;
    
    public abstract PrecioArticulo getSubTotal(LineaDeVenta ldv);
    public abstract float getTotal();
    
    public abstract void crearComprobante(Venta venta);
}
