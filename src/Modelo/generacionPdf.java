/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import Vista.Vista;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfWriter;
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
public class generacionPdf {
            JFileChooser selecArchivo = new JFileChooser();
            File archivo;
       public void generar(String serie) throws DocumentException{
           try {
          if(selecArchivo.showDialog(null, "Exportar")==JFileChooser.APPROVE_OPTION){
               archivo=selecArchivo.getSelectedFile();
               Document doc= new Document();
               PdfWriter pdf =PdfWriter.getInstance(doc, new FileOutputStream(archivo));
               doc.open();
               Barcode39 code= new Barcode39();
               code.setCode(serie);
               Image img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
               img.scaleToFit(50, 30);
               
               doc.add(img);
               doc.close();
          }
           } catch (FileNotFoundException ex) {
               Logger.getLogger(generacionPdf.class.getName()).log(Level.SEVERE, null, ex);
           }
            }
}
