/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import Vista.Vista;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.print.Pageable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.*;


/**
 *
 * @author alberto
 */
public class generacionPdf_1 {
            JFileChooser selecArchivo = new JFileChooser();
            File archivo;
            int width=189;
            int height=189;
            Rectangle rec = new Rectangle(width,height );
      public File exportarArchivo(){
           if(selecArchivo.showDialog(null, "Crear")==JFileChooser.APPROVE_OPTION){
               archivo=selecArchivo.getSelectedFile();
           }
           return archivo;
      }
       public void generar(String serie, String descripcion) throws DocumentException{
        
           try {
          if(selecArchivo.showDialog(null, "Crear")==JFileChooser.APPROVE_OPTION){
               archivo=selecArchivo.getSelectedFile();
               Document doc= new Document();
      /*       PrinterJob job = PrinterJob.getPrinterJob();       
               if (job.printDialog() == true) {            
               job.setPageable(new pdf doc);
              }*/ 
               
               PdfWriter pdf =PdfWriter.getInstance(doc, new FileOutputStream(archivo));
               doc.setPageSize(rec);
               doc.setMargins(10, 10, 10,10);
               doc.open();
 
               Barcode39 code= new Barcode39();
               code.setCode(serie);
               Image img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
               img.scaleToFit(50, 30);
               Paragraph parrafo = new Paragraph();
               Paragraph salto = new Paragraph();
               parrafo.setAlignment(Paragraph.ALIGN_CENTER);
               //parrafo.add("information");
               parrafo.setFont(FontFactory.getFont("Tahoma",5,Font.BOLD, BaseColor.DARK_GRAY));
               
               doc.open();
               
               
               parrafo.add(descripcion);
               salto.add("\n \n");
               doc.add( parrafo  );
               doc.add(salto);
               doc.add(img);
              pdf.getPageSize();
              
               System.out.println(               pdf.getPageSize());
               
               doc.close();
          }
               
           } catch (FileNotFoundException ex) {
               Logger.getLogger(generacionPdf.class.getName()).log(Level.SEVERE, null, ex);
           }
            }
 /* public void imprimir() throws PrinterException, IOException {
        // Indicamos el nombre del archivo Pdf que deseamos imprimir
        PDDocument document = PDDocument.load(new File("./documento.pdf"));
 
        PrinterJob job = PrinterJob.getPrinterJob();
 
        LOGGER.log(Level.INFO, "Mostrando el dialogo de impresion");
        if (job.printDialog() == true) {            
            job.setPageable(new PDFPageable(document));
 
            LOGGER.log(Level.INFO, "Imprimiendo documento");
            job.print();
        }
    }*/
}

