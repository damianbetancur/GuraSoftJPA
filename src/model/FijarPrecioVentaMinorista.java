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
public class FijarPrecioVentaMinorista extends IEstrategiaFijarPreciosVenta{

    public FijarPrecioVentaMinorista() {
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
    public void crearComprobante(Venta venta, Comprobante comp) {
        Pago nuevoPago = new Pago();
        nuevoPago.actualizarCuentaCorriente(venta.getCliente(), true);
        
        Ticket nuevoTicket = new Ticket();
        venta.getPago();
        nuevoTicket.setVenta(venta);
        nuevoTicket.setFecha(venta.getFecha());
        comp = nuevoTicket;
               
    }

    

    
}
