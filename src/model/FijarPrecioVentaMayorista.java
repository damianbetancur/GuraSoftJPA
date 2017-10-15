/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import model.JPAController.ComprobanteJpaController;
import model.JPAController.Conexion;
import model.JPAController.PrecioArticuloJpaController;

/**
 *
 * @author Ariel
 */
public class FijarPrecioVentaMayorista extends IEstrategiaFijarPreciosVenta{
    private ComprobanteJpaController modeloComprobante;
    public FijarPrecioVentaMayorista() {
        modeloComprobante = new ComprobanteJpaController(Conexion.getEmf());
        iva = 10.5f;
    }

    @Override
    public void getSubTotal(ArrayList<LineaDeVenta> lineasDeVenta, PrecioArticuloJpaController modeloPrecioArticulo, ListaDePrecio listaDePrecio) {        
        float subTotalArticulo = 0;
        for (LineaDeVenta ldv : lineasDeVenta) {
            for (PrecioArticulo pa : modeloPrecioArticulo.findPrecioArticuloEntities()) {
                if (ldv.getArticulo().getId().equals(pa.getId_articulo()) && pa.getId_listaDePrecio().equals(listaDePrecio.getId())) {
                    subTotalArticulo = ldv.getCantidad() * pa.getPrecio();
                    ldv.setSubTotal(subTotalArticulo);
                    subTotalArticulo = 0;
                }
            }
        }
    }

    @Override
    public float getTotal(ArrayList<LineaDeVenta> lineasDeVentas) {
        float total = 0;
        for (LineaDeVenta ldvtas : lineasDeVentas) {
            total = total + ldvtas.getSubTotal();
        }
        return total;
    }

    

    @Override
    public void aplicarDescuento(ArrayList<LineaDeVenta> lineasDeVentas, int descuento) {
        float dto = 0;
        for (LineaDeVenta ldv : lineasDeVentas) {
            dto = 0;
            dto = (ldv.getSubTotal()*descuento)/100;
            ldv.setSubTotal(ldv.getSubTotal()-dto);
        }
    }

    @Override
    public void crearComprobante(Venta venta, TalonarioComprobante talonario) {        
        Factura nuevaFactura = new Factura();
        nuevaFactura.setNumeroComprobante(talonario.getNumeracion_Actual());        
        nuevaFactura.setFecha(venta.getFecha());
        nuevaFactura.setSubTotal(venta.getPago().getSubTotal());
        nuevaFactura.setIva(venta.getPago().getIva());
        nuevaFactura.setTotal(venta.getPago().getTotal());
        nuevaFactura.setDescuento(venta.getPago().getDescuento());        
        nuevaFactura.setVenta(venta);
        modeloComprobante.create(nuevaFactura);
    }

    @Override
    public float aplicarIVA(float total) {
        float totalIVA = 0f;
        totalIVA = (total * iva )/100f;
        return totalIVA;
    }

    @Override
    public float getIVA() {
        return iva;
    }

    
    
    
}
