/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import Vista.Vista;
import Modelo.ModeloExcel;
import com.itextpdf.text.Document;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumn;
import Modelo.generacionPdf;
import com.itextpdf.text.DocumentException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.generacionPdf_1;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.bouncycastle.util.io.BufferingOutputStream;

public class ControladorExcel implements ActionListener {

    ModeloExcel modeloE = new ModeloExcel();
    Vista vistaE = new Vista();
    generacionPdf generar = new generacionPdf();
    generacionPdf_1 generar_1 = new generacionPdf_1();
    JFileChooser selecArchivo = new JFileChooser();
    File archivo;
    int contAccion = 0;

    public ControladorExcel(Vista vistaE, ModeloExcel modeloE) {
        this.vistaE = vistaE;
        this.modeloE = modeloE;
        this.vistaE.cargar.addActionListener(this);
        this.vistaE.exportar.addActionListener(this);
        this.vistaE.exportar1.addActionListener(this);
    }

    public void AgregarFiltro() {
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)", "xls"));
        selecArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
    }

    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
        for (int i = 0; i < vistaE.jTable1.getRowCount(); i++) {
            this.vistaE.jTable1.setValueAt(true, i, 3);
        }
    }

    public void accederDatosFilacopia5x5() throws DocumentException, FileNotFoundException, IOException, PrinterException {
        this.vistaE = vistaE;
        boolean columna;
        int i = 0;
        JFileChooser selecArchivo = new JFileChooser();
        File archivo = null;
        int width = 131;
        int height = 131;
        Rectangle rec = new Rectangle(width, height);
        Rectangle rec2 = new Rectangle(width, height);
        rec.setBorderColor(BaseColor.BLACK);
        rec.setBorderWidthBottom(2);
        rec.setBorderWidthLeft(2);
        rec.setBorderWidthRight(2);
        rec.setBorderWidthTop(2);

        String sku, descripcion, ups;

        columna = (boolean) this.vistaE.jTable1.getValueAt(0, 3);
        sku = String.valueOf(this.vistaE.jTable1.getValueAt(i, 0));
        ups = String.valueOf(this.vistaE.jTable1.getValueAt(i, 2));
        //  if(selecArchivo.showDialog(null, "Crear")==JFileChooser.APPROVE_OPTION){
        //    archivo=selecArchivo.getSelectedFile();
        // }
        Document doc = new Document(PageSize.LEGAL_LANDSCAPE);
        ByteArrayOutputStream archivotemp = new ByteArrayOutputStream();
        PdfWriter pdf = PdfWriter.getInstance(doc, archivotemp);

        doc.setPageSize(rec);
        doc.setMargins(2, 2, 2, 2);
        doc.setMarginMirroring(columna);
        doc.open();
        Barcode128 code = new Barcode128();
        code.setCode(ups);
        Image img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
        img.scaleToFit(40, 20);
        Paragraph parrafo = new Paragraph();
        Paragraph sku_ = new Paragraph();
        Paragraph header = new Paragraph();
        Paragraph line = new Paragraph();
        Paragraph salto = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        header.setAlignment(Paragraph.ALIGN_CENTER);
        line.setAlignment(Paragraph.ALIGN_CENTER);
        line.setSpacingBefore((float) .2);
        line.setSpacingAfter((float) .2);
        sku_.setAlignment(Paragraph.ALIGN_CENTER);
        sku_.setSpacingAfter((float) .2);
        sku_.setSpacingBefore((float) .2);
        parrafo.setFont(FontFactory.getFont("Tahoma", 3, Font.BOLD, BaseColor.DARK_GRAY));
        sku_.setFont(FontFactory.getFont("Tahoma", 5, Font.BOLD, BaseColor.DARK_GRAY));
        header.setFont(FontFactory.getFont("Tahoma", 7, Font.BOLD, BaseColor.DARK_GRAY));
        doc.open();
        header.add("Claroshop");
        salto.add("\n");
        line.add("_____________");
        while (i < this.vistaE.jTable1.getRowCount()) {
            columna = (boolean) this.vistaE.jTable1.getValueAt(i, 3);
            if (columna == true) {
                sku = String.valueOf(this.vistaE.jTable1.getValueAt(i, 0));
                ups = String.valueOf(this.vistaE.jTable1.getValueAt(i, 2));
                code.setCode(sku);
                img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img.scaleToFit(60, 30);
                img.setAlignment(img.ALIGN_CENTER);
                descripcion = String.valueOf(this.vistaE.jTable1.getValueAt(i, 1));
                i = i + 1;
                doc.add(header);
                doc.add(line);
                sku_.add("sku:" + " " + sku);
                parrafo.add(descripcion);
                doc.add(sku_);
                doc.add(line);
                doc.add(parrafo);
                doc.add(img);
                pdf.getPageSize();
                parrafo.removeAll(parrafo);
                sku_.removeAll(sku_);
                if (i + 1 > this.vistaE.jTable1.getRowCount()) {
                    doc.close();
                    ByteArrayInputStream input = new ByteArrayInputStream(archivotemp.toByteArray());
                    PDDocument documento12 = PDDocument.load(input);
                    PrinterJob job = PrinterJob.getPrinterJob();
                    if (job.printDialog() == true) {
                        job.setPageable(new PDFPageable(documento12));
                        job.print();
                    }
                }
            } else if (columna == false) {
                i = i + 1;
                columna = (boolean) this.vistaE.jTable1.getValueAt(i, 3);
                if (i + 1 > this.vistaE.jTable1.getRowCount()) {
                    doc.close();
                }
            }
        }
    }

    public void imprimirPdfMayor() throws DocumentException, FileNotFoundException, IOException, PrinterException {
        this.vistaE = vistaE;
        boolean columna;
        int i = 0;
        JFileChooser selecArchivo = new JFileChooser();
        File archivo = null;
        int width = 275;
        int height = 203;
        Rectangle rec = new Rectangle(width, height);
        rec.setBorderColor(BaseColor.BLACK);
        rec.setBorderWidthBottom(2);
        rec.setBorderWidthLeft(2);
        rec.setBorderWidthRight(2);
        rec.setBorderWidthTop(2);
        String sku, descripcion, ups;
        columna = (boolean) this.vistaE.jTable1.getValueAt(0, 3);
        sku = String.valueOf(this.vistaE.jTable1.getValueAt(i, 0));
        ups = String.valueOf(this.vistaE.jTable1.getValueAt(i, 2));
    /*    if (selecArchivo.showDialog(null, "Crear") == JFileChooser.APPROVE_OPTION) {
            archivo = selecArchivo.getSelectedFile();
            if (archivo.getName().endsWith("pdf")) {
                JOptionPane.showMessageDialog(null, "importación exitosa" + "\n Formato" + archivo.getName().substring(archivo.getName().lastIndexOf(".")));
            } else {
                JOptionPane.showMessageDialog(null, "agregue la extencion .pdf" + "\n  ejemplo nombre.pdf ");
            }
        }*/
        
        Document doc = new Document(PageSize.LEGAL_LANDSCAPE);
        ByteArrayOutputStream archivotemp = new ByteArrayOutputStream();
        PdfWriter pdf = PdfWriter.getInstance(doc, archivotemp);
        doc.setPageSize(rec);
        
        doc.setMargins(10, 10, 10, 10);
        doc.setMarginMirroring(columna);
        doc.open();
        Barcode128 code = new Barcode128();
        code.setCode(sku);
        Image img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
        img.scaleToFit(70, 50);
        Paragraph parrafo = new Paragraph();
        Paragraph sku_ = new Paragraph();
        Paragraph header = new Paragraph();
        Paragraph line = new Paragraph();
        Paragraph salto = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        header.setAlignment(Paragraph.ALIGN_CENTER);
        line.setAlignment(Paragraph.ALIGN_CENTER);
        sku_.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 12, Font.BOLD, BaseColor.DARK_GRAY));
        sku_.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));
        header.setFont(FontFactory.getFont("Tahoma", 20, Font.BOLD, BaseColor.DARK_GRAY));
        doc.open();
        header.add("Claroshop");
        salto.add("\n");
        line.add("_______________________");
        while (i < this.vistaE.jTable1.getRowCount()) {
            columna = (boolean) this.vistaE.jTable1.getValueAt(i, 3);
            if (columna == true) {
                sku = String.valueOf(this.vistaE.jTable1.getValueAt(i, 0));
                ups = String.valueOf(this.vistaE.jTable1.getValueAt(i, 2));
                code.setCode(sku);
                img = code.createImageWithBarcode(pdf.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                img.scaleToFit(90, 90);
                img.setAlignment(img.ALIGN_CENTER);
                descripcion = String.valueOf(this.vistaE.jTable1.getValueAt(i, 1));
                i = i + 1;
                doc.add(header);
                doc.add(line);
                sku_.add("sku:" + " " + sku);
                parrafo.add(descripcion);
                doc.add(sku_);
                doc.add(line);
                doc.add(parrafo);
                doc.add(line);
                doc.add(salto);
                doc.add(img);
                pdf.getPageSize();
           

                parrafo.removeAll(parrafo);
                sku_.removeAll(sku_);
                if (i + 1 > this.vistaE.jTable1.getRowCount()) {
                    doc.close();
                    ByteArrayInputStream input = new ByteArrayInputStream(archivotemp.toByteArray());
                    PDDocument documento12 = PDDocument.load(input);
                    
                    PrinterJob job = PrinterJob.getPrinterJob();
                    if (job.printDialog() == true) {
                        job.setPageable(new PDFPageable(documento12));
                        job.print();
                    }
                }
            } else if (columna == false) {
                i = i + 1;
                columna = (boolean) this.vistaE.jTable1.getValueAt(i, 3);
                if (i + 1 > this.vistaE.jTable1.getRowCount()) {
                    doc.close();
                }
            }

        }
    }

    public void imprimir() {
        Document documento = new Document();
        try {
        } catch (Exception e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        contAccion++;
        if (contAccion == 1) {
            AgregarFiltro();
        }
        if (e.getSource() == vistaE.cargar) {
            if (selecArchivo.showDialog(null, "Seleccionar archivo") == JFileChooser.APPROVE_OPTION) {
                archivo = selecArchivo.getSelectedFile();
                if (archivo.getName().endsWith("xls") || archivo.getName().endsWith("xlsx")) {
                    JOptionPane.showMessageDialog(null,
                            modeloE.Importar(archivo, vistaE.jTable1) + "\n Formato ." + archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1),
                            "IMPORTAR EXCEL", JOptionPane.INFORMATION_MESSAGE);
                    addCheckBox(3, this.vistaE.jTable1);
                } else {
                    JOptionPane.showMessageDialog(null, "Elija un formato valido.");
                }
            }
        }
        if (e.getSource() == vistaE.exportar1) {

            try {
                imprimirPdfMayor();
            } catch (DocumentException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PrinterException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vistaE.exportar) {
            try {
                accederDatosFilacopia5x5();
            } catch (DocumentException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PrinterException ex) {
                Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
