package Control;

import Model.Vuelo;
import Model.VueloDAO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VueloControl {
    private final VueloDAO vueloDAO;
    private Vuelo v =new Vuelo();
    public VueloControl(VueloDAO vueloDAO) {

        this.vueloDAO = vueloDAO;
    }

    public void insert(){
        try {
            v = this.DataValidationApplication(v.getCodVuelo());
            this.vueloDAO.create(v);
            JOptionPane.showMessageDialog((Component)null, "Vuelo insertado correctamente");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(){
        try {
                String codVuelo = this.dataValidationApplication("Codigo de vuelo","Teclea el codigo de vuelo 'AEA...' hasta 5 numeros","^AEA[0-9][0-9]{4}$");

                v = vueloDAO.search(codVuelo);

                if (v == null){
                    JOptionPane.showMessageDialog((Component)null, "Vuelo no encontrado");
                }else {
                    v = this.DataValidationApplication(codVuelo);
                    vueloDAO.update(v);
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
    }

    public void delete(){
        try {
            String codVuelo = this.dataValidationApplication("Codigo de vuelo","Teclea el codigo del vuelo","^AEA[0-9][0-9]{4}$");

            v.setCodVuelo(codVuelo);

            vueloDAO.delete(v);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void search(){
        try {
            ArrayList<Vuelo> disponibles = vueloDAO.read();

            for (Vuelo v : disponibles) {
                JOptionPane.showMessageDialog(null,v.toString());
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void searchByCodVuelo(){
        String codVuelo = this.dataValidationApplication("Codigo de vuelo","Teclea el codigo del vuelo","^AEA[0-9][0-9]{4}$");

        v = vueloDAO.search(codVuelo);

        JOptionPane.showMessageDialog(null,v.toString());


    }
    public void flightByDestinarion(){
        String destino = this.dataValidationApplication("Destino","Teclea el destino del vuelo","^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");

        ArrayList<Vuelo> vuelos = vueloDAO.searchByDestination(destino);

        for(Vuelo v : vuelos){
            JOptionPane.showMessageDialog(null,v.toString());
        }
    }
    public void searchByDate(){
        ArrayList<Vuelo> disponibles = vueloDAO.read();
        String[] dispo = disponibles.stream()
                .map(Vuelo::getFechaSalida)
                .toArray(String[]::new);
        String opcion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione la fecha de salida",
                "Opciones",
                JOptionPane.PLAIN_MESSAGE,
                null,
                dispo,
                dispo[0]
        );
        LocalDate fechaSalida = LocalDate.parse(opcion);

        ArrayList<Vuelo> porFechas = vueloDAO.searchByDate(fechaSalida);
    }

    private Vuelo DataValidationApplication(String codVuelo){


        LocalDate date = this.dateConversion(this.dataValidationApplication("Fecha de salida", "Teclea la fecha de salida del vuelo dd/mm/aaaa", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));

        String procedencia = this.dataValidationApplication("Procedencia","Teclea la procedenca del vuelo","^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");

        String origin = this.dataValidationApplication("Origen","Teclea el origen del vuelo","^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");

        return new Vuelo(codVuelo,date,procedencia,origin);

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
    private LocalDate dateConversion(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date,dtf);
    }
}
