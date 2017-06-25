/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 *
 * @author Ariel
 */
@Entity
@Table (name="STOCK_ARTICULO")
@IdClass(StockArticuloPK.class)
public class StockArticulo implements Serializable {

    public StockArticulo(){}
    

    @Id
    @Column(name="ID_articulo")
    private Long id_articulo;

    @Id
    @Column(name="ID_deposito")
    private Long id_Deposito;

    @Column(name="STOCK_Actual",length=8)
    private int stockActual;       

    @Column(name="STOCK_Minimo",length=8)
    private int stockMinimo;
    
    @Column(name="STOCK_Maximo",length=8)
    private int stockMaximo;
    
    public Long getId_Deposito() {
        return id_Deposito;
    }

    public void setId_Deposito(Long id_Deposito) {
        this.id_Deposito = id_Deposito;
    }    

    public Long getId_articulo() {
        return id_articulo;
    }

    public void setId_articulo(Long id_articulo) {
        this.id_articulo = id_articulo;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getStockMaximo() {
        return stockMaximo;
    }

    public void setStockMaximo(int stockMaximo) {
        this.stockMaximo = stockMaximo;
    }
    
    
}
