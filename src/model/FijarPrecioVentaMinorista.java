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
public class FijarPrecioVentaMinorista extends IEstrategiaFijarPreciosVenta{
    private ComprobanteJpaController modeloComprobante;
    public FijarPrecioVentaMinorista() {
        modeloComprobante = new ComprobanteJpaController(Conexion.getEmf());
        iva = 21f;
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
        
    }

    @Override
    public void crearComprobante(Venta venta, TalonarioComprobante talonario) {
        Ticket nuevoTicket = new Ticket();
        nuevoTicket.setNumeroComprobante(talonario.getNumeracion_Actual());        
        nuevoTicket.setFecha(venta.getFecha());
        nuevoTicket.setSubTotal(venta.getPago().getSubTotal());
        nuevoTicket.setIva(venta.getPago().getIva());
        nuevoTicket.setTotal(venta.getPago().getTotal());
        nuevoTicket.setDescuento(venta.getPago().getDescuento());        
        nuevoTicket.setVenta(venta);
        modeloComprobante.create(nuevoTicket);
               
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
