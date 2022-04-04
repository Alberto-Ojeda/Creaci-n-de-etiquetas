/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.*;
import Vista.Vista;
public class ModeloExcel {
    Workbook wb;
Vista vistaE= new Vista();
  
    public String Importar(File archivo, JTable tablaD){
        
        String respuesta="No se pudo realizar la importación.";
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        tablaD.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
       
        Object[] listaColumna = new Object[1000];
        try {
            
            wb = WorkbookFactory.create(new FileInputStream(archivo));
               Sheet hoja = wb.getSheetAt(0);
               Row row=hoja.createRow(0);
               row.createCell(0).setCellValue("SKU");
               row.createCell(1).setCellValue("DESCRIPCION");
               row.createCell(2).setCellValue("CODIGO");
               row.createCell(3).setCellValue("SELECTOR");
               Iterator filaIterator = hoja.rowIterator();
               
               
               
               
               
              
            int indiceFila=-1;
            while (filaIterator.hasNext() ) {    
            //    System.out.println(filaIterator.hasNext() );
            //    System.out.println( indiceFila);
                indiceFila++;
                Row fila = (Row) filaIterator.next();
                Iterator columnaIterator = fila.cellIterator();
//                Object[] listaColumna = new Object[1000];
             
                int indiceColumna=-1;
                while (columnaIterator.hasNext()) { 
                    indiceColumna++;
                 
                    Cell celda = (Cell) columnaIterator.next();
                    if(indiceFila==0){
                        modeloT.addColumn(celda.getStringCellValue());

                    }
                    
                    else{
                       
                        if( celda!=null){
                            switch(celda.getCellType()){
                                case Cell.CELL_TYPE_NUMERIC:
                                    listaColumna[indiceColumna]= (int)Math.round(celda.getNumericCellValue());
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    listaColumna[indiceColumna]= celda.getStringCellValue();
                                    break;
                                case Cell.CELL_TYPE_BOOLEAN:
                                    listaColumna[indiceColumna]= celda.getBooleanCellValue();
                                    break;
                                default:
                                    listaColumna[indiceColumna]=celda.getDateCellValue();
                                    break;
                            }
                     //       System.out.println("col"+indiceColumna+" valor: true - "+celda+".");                             
                        }

                
 
                    }
                  
                }
                
                if(indiceFila!=0)modeloT.addRow(listaColumna);
                 
            }
                    

            respuesta="Importación exitosa";
        } catch (IOException | InvalidFormatException | EncryptedDocumentException e) {

//            System.err.println(e.getMessage());
        }
                   
        return respuesta;
        
    }
   
}
