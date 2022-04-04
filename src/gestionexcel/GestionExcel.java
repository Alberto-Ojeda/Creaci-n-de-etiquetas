/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionexcel;
import Modelo.ModeloExcel;
import Vista.Vista;
import Controlador.ControladorExcel;


public class GestionExcel {

   
    public static void main(String[] args) {
        ModeloExcel modeloE = new ModeloExcel();
        Vista vistaE = new Vista();
        ControladorExcel contraControladorExcel = new ControladorExcel(vistaE, modeloE);
        vistaE.setVisible(true);
        vistaE.setLocationRelativeTo(null);
         
    }
    
}
