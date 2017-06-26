/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Genera un archivo Excel
 *
 * @author Ariel
 */
public class GenerarExcel {

    /**
     * Genera un archivo excel
     *
     * @param entrada
     * @param ruta
     */
    public void generarExcel(String[][] entrada, String ruta) {

        try {
            //configuracion inicial
            WorkbookSettings conf = new WorkbookSettings();
            //iso para la generación del EXCEl
            conf.setEncoding("ISO-8859-1");
            //Se crea el Excel
            WritableWorkbook woorBook = Workbook.createWorkbook(new File(ruta), conf);
            //Se crea la Hoja del excel en la posicion 0 (cero)
            WritableSheet sheet = woorBook.createSheet("informe", 0);
            //Fuente para el contenido
            WritableFont h = new WritableFont(WritableFont.COURIER, 16, WritableFont.BOLD);
            //Mandar el formato a la celda
            WritableCellFormat hFormat = new WritableCellFormat(h);

            //Insertar en entrada
            for (int i = 0; i < entrada.length; i++) {//Fila
                for (int j = 0; j < entrada[i].length; j++) {
                    try {//Columna                    
                        sheet.addCell(new jxl.write.Label(j, j, entrada[i][j], hFormat));
                    } catch (WriteException ex) {
                        java.util.logging.Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            woorBook.write();
            woorBook.close();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            java.util.logging.Logger.getLogger(GenerarExcel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
