/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Tote
 * @author Brodner
 * @author David
 * @author Armando Santos
 */
public class GuardarFichero {

    private final String Notacion;
    private final String Notacion1;
    private final String Notacion2;
    private final String Notacion3;

    /*  public GuardarFichero(String Notacion) {
        this.Notacion = Notacion;
    }*/
    GuardarFichero(String msg, String postfija, String infija, String prefija) {
        this.Notacion = msg;
        this.Notacion1 = postfija;
        this.Notacion2 = infija;
        this.Notacion3 = prefija;
    }

    public void save() {
        File path = new File("");
        String arbol = Notacion;
        String postfija = Notacion1;
        String infija = Notacion2;
        String prefijo = Notacion3;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            FileWriter archivo = new FileWriter(path.getPath() + "Historial" + dtf.format(LocalDateTime.now()) + ".txt", true);
            BufferedWriter bfwriter = new BufferedWriter(archivo);
            bfwriter.write("Infija -> " + infija + "\n"
                    + "Prefija -> " + prefijo + "\n"
                    + "Postfija -> " + postfija + "\n\n"
                    + "Arbol -> \n" + arbol
                    + "\n\n*****************************************************************************\n\n");
            bfwriter.close();
            System.out.println("Archivo creado satisfactoriamente..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
