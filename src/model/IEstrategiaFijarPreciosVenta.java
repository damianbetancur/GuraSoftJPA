/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import model.JPAController.PrecioArticuloJpaController;

/**
 *
 * @author Ariel
 */
public abstract class IEstrategiaFijarPreciosVenta {
    protected float iva;
    
    public abstract void getSubTotal(ArrayList<LineaDeVenta> lineasDeVenta, PrecioArticuloJpaController modeloPrecioArticulo, ListaDePrecio listaDePrecio);
    
    public abstract float getTotal(ArrayList<LineaDeVenta> lineasDeVentas);
    
    public abstract void aplicarDescuento(ArrayList<LineaDeVenta> lineasDeVentas, int descuento);
    
    public abstract void crearComprobante(Venta venta, TalonarioComprobante talonario);
    
    public abstract float aplicarIVA(float total);
    
    public abstract float getIVA();
}
