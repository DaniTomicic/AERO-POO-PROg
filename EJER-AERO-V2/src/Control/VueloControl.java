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
    private Vuelo v=new Vuelo();
    public VueloControl(VueloDAO vueloDAO) {

        this.vueloDAO = vueloDAO;
    }

    public void insert(){
        try {
            Vuelo v = this.DataValidationApplication();
            this.vueloDAO.create(v);
            JOptionPane.showMessageDialog((Component)null, "Vuelo insertado correctamente");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void update(){
        try {
            ArrayList<Vuelo> disponibles = vueloDAO.read();
            String[] dispo = disponibles.stream()
                    .map(Vuelo::getCodVuelo)
                    .filter(cod -> cod.contains("A")).toArray(String[]::new);

            String opcion = (String) JOptionPane.showInputDialog(null,
                    "Vuelos Disponibles:",
                    "Seleccione",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    dispo,
                    dispo[0]
            );

            v.setCodVuelo(opcion);

            disponibles.clear(); //limpio para enviar luego solo un vuelo
            disponibles.add(v);

            disponibles = vueloDAO.read();

            vueloDAO.update(disponibles.getFirst());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void delete(){
        try {
            ArrayList<Vuelo> disponibles = vueloDAO.read();
            String[] dispo = disponibles.stream()
                    .map(Vuelo::getCodVuelo)
                    .filter(cod -> cod.contains("A")).toArray(String[]::new);

            String opcion = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona el vuelo que quieras borrar",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    dispo,
                    dispo[0]
            );
            v.setCodVuelo(opcion);

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
        ArrayList<Vuelo> disponibles = vueloDAO.read();
        String[] codVuelo = disponibles.stream().map(Vuelo::getCodVuelo).toArray(String[]::new);
        String opcion = (String) JOptionPane.showInputDialog(
                null,
                "Codigos de vuelo disponibles",
                "Elije uno",
                JOptionPane.PLAIN_MESSAGE,
                null,
                codVuelo,
                codVuelo[0]
        );

        Vuelo vuelo = vueloDAO.search(opcion);

        JOptionPane.showMessageDialog(null,vuelo.toString());


    }
    public void flightByDestinarion(){
        ArrayList<Vuelo> disponibles = vueloDAO.read();
        String[] dispo = disponibles.stream()
                .map(Vuelo::getDestino)
                .toArray(String[]::new);

        String opcion = (String) JOptionPane.showInputDialog(
                null,
                "Selecciona el destino",
                "Opciones",
                JOptionPane.PLAIN_MESSAGE,
                null,
                dispo,
                dispo[0]
        );
        ArrayList<Vuelo> vuelos = vueloDAO.searchByDestination(opcion);

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

    private Vuelo DataValidationApplication(){
        String generatedCodVuelo = generateCodVuelo();

        LocalDate date = this.dateConversion(this.dataValidationApplication("Fecha de salida", "Teclea la fecha de salida del vuelo dd/mm/aaaa", "^[0-9]{2}/[0-9]{2}/[0-9]{4}$"));

        String procedencia = this.dataValidationApplication("Procedencia","Teclea la procedenca del vuelo","^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");

        String origin = this.dataValidationApplication("Origen","Teclea el origen del vuelo","^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");

        return new Vuelo(generatedCodVuelo,date,procedencia,origin);

    }


    private String generateCodVuelo(){
        String codigoGenerado;
        String CABECERA = "AEA";
        int contadorCABECERA = 1; //
        int contadorCOLA = 1;
        ArrayList<Vuelo> vuelos = vueloDAO.read();


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
    private LocalDate dateConversion(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date,dtf);
    }
}
