package Control;

import Model.Pasajero;
import Model.PasajeroDAO;
import Model.Vuelo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.Main.vueloDAO;

public class PasajeroControl {
    private final PasajeroDAO pasajeroDAO;
    private  Pasajero p =new Pasajero();

    public PasajeroControl(PasajeroDAO pasajeroDAO) {
        this.pasajeroDAO = pasajeroDAO;
    }

    public void insert(){
        try {
            p = pasajeroDAO.search(this.dataValidation("DNI","teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$"));

            if (p == null){
                Pasajero pas = this.dataValidationApplication();
                this.pasajeroDAO.create(pas);
            }
            update();

            JOptionPane.showMessageDialog((Component) null,"Pasajero insertado correctamente");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void update(){
        try{
            p = pasajeroDAO.search(this.dataValidation("DNI","teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$"));

            JOptionPane.showMessageDialog(null,p.toString());

            String[] modificaciones ={"Nombre","Telefono","Codigo de vuelo"};

            String eleccion = (String) JOptionPane.showInputDialog(null,"Elija que quiere modificar",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    modificaciones,
                    modificaciones[0]
            );

            p = this.dataValidationApplication();

            pasajeroDAO.update(p);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void delete(){
        try {
            p = pasajeroDAO.search(this.dataValidation("DNI","teclea el DNI del pasajero", "^[1-9][0-9]{7}[A-Z]$"));

            pasajeroDAO.delete(p);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void search(){
        try {
            ArrayList<Pasajero> disponibles = pasajeroDAO.read();

            for (Pasajero p : disponibles) {
                JOptionPane.showMessageDialog(null,p.toString());
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private Pasajero dataValidationApplication(){

        String name =this.dataValidation("Name","teclea el nombre del pasajero","^[A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+([ -][A-Za-zñÑáéíóúÁÉÍÓÚüÜ]+)*$");

        String cellphone = this.dataValidation("Cellphone","teclea el numero de telefono del pasajero","^[678][0-9]{8}$");

        String codVuelo = this.searchAndAssignCodVuelo();

        return new Pasajero(p.getDni(),name,cellphone,codVuelo);
    }

    private String searchAndAssignCodVuelo(){
        boolean codVueloFinished = false;

        String option;
        String[] codVuelos = vueloDAO.read().stream()
                .map(Vuelo::getCodVuelo)
                .filter(codVuelo ->codVuelo.contains("A"))
                .toArray(String[]::new);
        do {
            option = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el codigo del vuelo",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    codVuelos,
                    codVuelos[0]
            );

            if (option.isEmpty()){
                JOptionPane.showMessageDialog(null,"El codigo de vuelo no puede estar vacio");
            }else {
                codVueloFinished = true;
            }

        }while (!codVueloFinished);
        return option;
    }

    public void searchFlightOfPassanger(){
        String[] pasajerosCod = pasajeroDAO.read().stream()
                .map(Pasajero::getDni)
                .toArray(String[]::new);
        try {
            String option = (String) JOptionPane.showInputDialog(null,
                    "Seleccione el DNI del pasajero",
                    "Opciones",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    pasajerosCod,
                    pasajerosCod[0]
            );

            ArrayList<Vuelo> vuelos = pasajeroDAO.searchFlights(option);

            for (Vuelo v : vuelos) {
                JOptionPane.showMessageDialog(null,v.toString());
            }


        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private String dataValidation(String data, String msj, String pattern){
        boolean funcfinished=false;
        String var="";
        do {
            try {
                var = JOptionPane.showInputDialog(null,msj);
                if (var.isEmpty()){
                    JOptionPane.showMessageDialog(null,"el dato " + data + " no puede estar vacio");
                }
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(var);

                if (m.matches()){
                    funcfinished=true;
                }

            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"No se aceptan numeros aqui");
            }

        }while (!funcfinished);
        return var;
    }
}
