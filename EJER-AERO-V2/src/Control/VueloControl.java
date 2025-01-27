package Control;

import Model.Vuelo;
import Model.VueloDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VueloControl {
    private final VueloDAO vueloDAO;

    public VueloControl(VueloDAO vueloDAO) {

        this.vueloDAO = vueloDAO;
    }

    public void insert(){
        try {
            Vuelo v = this.DataValidationApplication();
            this.vueloDAO.create(v);
            JOptionPane.showMessageDialog((Component)null, "Vuelo insertado correctamente");
        }catch (SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog((Component)null, "Error al insertar vuelo, no puede haber dos vuelos con el mismo código.");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private Vuelo DataValidationApplication(){
        String generatedCodVuelo = generateCodVuelo();

        LocalDate date = this.dateConversion(this.dataValidationApplication("Fecha de salida", "Teclea la fecha de salida del vuelo", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));

        String procedencia = this.dataValidationApplication("Procedencia","Teclea la procedenca del vuelo","^[A-Z][a-z]+([ -][A-Z][a-z]+)*$");

        String origin = this.dataValidationApplication("Origen","Teclea el origen del vuelo","^[A-Z][a-z]+([ -][A-Z][a-z]+)*$");

        return new Vuelo(generatedCodVuelo,date,procedencia,origin);

    }


    private String generateCodVuelo(){
        String codigoGenerado;
        String CABECERA = "AEA";
        int contadorCABECERA = 1; //
        int contadorCOLA = 1;

        vuelos = vueloDAO.read();


        boolean codigoExiste;
        do {
            codigoGenerado = String.format("%s%d%04d", CABECERA, contadorCABECERA, contadorCOLA);
            contadorCOLA++;

            if (contadorCOLA > 9999) {
                contadorCABECERA++;
                contadorCOLA = 1;
            }

            codigoExiste = false;
            for (Vuelo vuelo : vuelos) {
                if (vuelo.getCodVuelo().equals(codigoGenerado)) {
                    codigoExiste = true;
                    break;
                }
            }

        } while (codigoExiste);

        return codigoGenerado;
    }
    private String dataValidationApplication(String data,String msj,String pattern){
        String var="";
        boolean funcFinished=false;
        do {
            try {
                var = JOptionPane.showInputDialog(null,msj);
                if (var.isEmpty()){
                    JOptionPane.showMessageDialog(null,data + " no puede ser un valor vacio");
                }
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(var);
                if (m.matches()){
                    funcFinished=true;
                }else {
                    JOptionPane.showMessageDialog(null,data + " no tiene un formato valido");
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Error: " +e.getMessage());
            }
        }while (!funcFinished);
        return var;
    }
    public LocalDate dateConversion(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date,dtf);
    }
}
