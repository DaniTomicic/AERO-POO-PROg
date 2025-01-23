package control;

import com.sun.source.tree.PatternCaseLabelTree;
import modelo.Pasajero;
import modelo.PasajeroDAO;
import modelo.VueloDAO;

import javax.swing.*;
import java.util.regex.Pattern;


public class PasajeroControl {
    private static PasajeroDAO pasajeroDAO = new PasajeroDAO();
    private static VueloDAO vueloDAO = new VueloDAO();
    private static Pasajero p = new Pasajero();

    public PasajeroControl() {
    }

    public static void createPasajero() {
        try {
            p = validarDatos();

            PasajeroDAO.create(p);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Pasajero validarDatos(){
        try {
            String DNI = validarDNI("DNI","Introduza su DNI", Pattern.compile("^[1-9][0-9]{8}[A-Z]$"));

            //return p.set...set...
        }catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos no acepados");
        }
        return null;
    }

    public static String datos(String dato, String mensaje, Pattern patron) {
        try {

        }catch (NullPointerException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Datos no acepados");
        }
        return dato;

    }

    public static String validarDNI(String DNI, String mensaje, Pattern pattern) {
        return DNI;
    }



}

